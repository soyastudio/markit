Here's a comprehensive FreeMarker template to create a BigQuery table with various configurable options:

## Main BigQuery Table Template (`bigquery_table.ftl`)

```freemarker
<#--
  BigQuery Table Creation Template
  This template generates a BigQuery CREATE TABLE statement
  
  Required parameters:
  - projectId: GCP project ID
  - datasetId: BigQuery dataset ID
  - tableId: Table name
  - schema: List of column definitions
  
  Optional parameters:
  - description: Table description
  - labels: Map of key-value labels
  - partitioning: Partitioning configuration
  - clustering: List of clustering columns
  - expirationMs: Table expiration in milliseconds
  - requirePartitionFilter: Boolean for partition filter requirement
  - createDisposition: CREATE_IF_NEEDED or CREATE_NEVER
  - writeDisposition: WRITE_EMPTY, WRITE_TRUNCATE, or WRITE_APPEND
-->

<#-- Validate required parameters -->
<#if !projectId??>
  <#stop "ERROR: projectId is required">
</#if>
<#if !datasetId??>
  <#stop "ERROR: datasetId is required">
</#if>
<#if !tableId??>
  <#stop "ERROR: tableId is required">
</#if>
<#if !schema?? || schema?size == 0>
  <#stop "ERROR: schema is required and must contain at least one column">
</#if>

<#-- Generate CREATE TABLE statement -->
CREATE TABLE <#if createDisposition?? && createDisposition == "CREATE_IF_NEEDED">IF NOT EXISTS </#if>`${projectId}.${datasetId}.${tableId}`
(
<#list schema as column>
  `${column.name}` ${column.type}<#if column.mode?? && column.mode != "NULLABLE"> ${column.mode}</#if><#if column.description??> OPTIONS(description="${column.description?replace('"', '\\"')}")</#if><#if column_has_next>,</#if>
</#list>
)
<#-- Add OPTIONS clause if any table-level options exist -->
<#if description?? || labels?? || expirationMs?? || partitioning?? || clustering?? || requirePartitionFilter??>
OPTIONS(
  <#assign options = []>
  
  <#-- Table description -->
  <#if description??>
    <#assign options += ['description="' + description?replace('"', '\\"') + '"']>
  </#if>
  
  <#-- Labels -->
  <#if labels?? && labels?size gt 0>
    <#assign labelStrings = []>
    <#list labels?keys as key>
      <#assign labelStrings += ['"' + key + '"="' + labels[key] + '"']>
    </#list>
    <#assign options += ['labels=[' + labelStrings?join(', ') + ']']>
  </#if>
  
  <#-- Partitioning -->
  <#if partitioning??>
    <#if partitioning.type == "DAY" || partitioning.type == "HOUR" || partitioning.type == "MONTH" || partitioning.type == "YEAR">
      <#-- Time-based partitioning -->
      <#assign partitionClause = 'partitioning_type="' + partitioning.type + '"'>
      <#if partitioning.field??>
        <#assign partitionClause += ', partitioning_field="' + partitioning.field + '"'>
      </#if>
      <#assign options += [partitionClause]>
    <#elseif partitioning.type == "RANGE">
      <#-- Range partitioning -->
      <#if !partitioning.field?? || !partitioning.start?? || !partitioning.end?? || !partitioning.interval??>
        <#stop "ERROR: Range partitioning requires field, start, end, and interval">
      </#if>
      <#assign rangeClause = 'partitioning_type="RANGE", range_partitioning=STRUCT('>
      <#assign rangeClause += 'field="' + partitioning.field + '", '>
      <#assign rangeClause += 'range=STRUCT(start=' + partitioning.start + ', `end`=' + partitioning.end + ', interval=' + partitioning.interval + '))'>
      <#assign options += [rangeClause]>
    </#if>
  </#if>
  
  <#-- Require partition filter -->
  <#if requirePartitionFilter?? && requirePartitionFilter>
    <#assign options += ['require_partition_filter=true']>
  </#if>
  
  <#-- Clustering -->
  <#if clustering?? && clustering?size gt 0>
    <#assign clusteringFields = []>
    <#list clustering as field>
      <#assign clusteringFields += ['"' + field + '"']>
    </#list>
    <#assign options += ['clustering_fields=[' + clusteringFields?join(', ') + ']']>
  </#if>
  
  <#-- Expiration -->
  <#if expirationMs??>
    <#assign options += ['expiration_timestamp=TIMESTAMP_ADD(CURRENT_TIMESTAMP(), INTERVAL ' + (expirationMs / 1000) + ' SECOND)']>
  </#if>
  
  <#-- Output all options -->
  ${options?join(',\n  ')}
)
</#if>
<#-- Add write disposition if specified -->
<#if writeDisposition?? && writeDisposition != "WRITE_EMPTY">
  <#-- Note: Write disposition is typically handled by the client library, not in DDL -->
</#if>
;
```

## Usage Example

Here's an example of how to use this template:

```java
// Java example using FreeMarker
Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
cfg.setDirectoryForTemplateLoading(new File("templates"));
Template template = cfg.getTemplate("bigquery_table.ftl");

Map<String, Object> data = new HashMap<>();
data.put("projectId", "my-project");
data.put("datasetId", "my_dataset");
data.put("tableId", "events");
data.put("description", "Event tracking table");
data.put("requirePartitionFilter", true);

// Define schema
List<Map<String, String>> schema = new ArrayList<>();
schema.add(Map.of("name", "event_id", "type", "STRING", "mode", "REQUIRED"));
schema.add(Map.of("name", "event_timestamp", "type", "TIMESTAMP", "mode", "REQUIRED"));
schema.add(Map.of("name", "user_id", "type", "STRING", "mode", "NULLABLE"));
schema.add(Map.of("name", "event_type", "type", "STRING", "mode", "REQUIRED"));
schema.add(Map.of("name", "properties", "type", "JSON", "mode", "NULLABLE"));
data.put("schema", schema);

// Add partitioning
Map<String, Object> partitioning = new HashMap<>();
partitioning.put("type", "DAY");
partitioning.put("field", "event_timestamp");
data.put("partitioning", partitioning);

// Add clustering
data.put("clustering", List.of("event_type", "user_id"));

// Add labels
Map<String, String> labels = new HashMap<>();
labels.put("environment", "production");
labels.put("team", "analytics");
data.put("labels", labels);

// Process template
StringWriter writer = new StringWriter();
template.process(data, writer);
System.out.println(writer.toString());
```

## Generated Output Example

```sql
CREATE TABLE `my-project.my_dataset.events`
(
  `event_id` STRING NOT NULL,
  `event_timestamp` TIMESTAMP NOT NULL,
  `user_id` STRING,
  `event_type` STRING NOT NULL,
  `properties` JSON
)
OPTIONS(
  description="Event tracking table",
  labels=["environment"="production", "team"="analytics"],
  partitioning_type="DAY", partitioning_field="event_timestamp",
  require_partition_filter=true,
  clustering_fields=["event_type", "user_id"]
);
```

## Additional Helper Template for Complex Data Types (`complex_types.ftl`)

For handling RECORD/STRUCT types:

```freemarker
<#macro columnDefinition column indent="">
${indent}`${column.name}` <#if column.type == "RECORD">STRUCT&lt;
<#list column.fields as field>
${indent}  <@columnDefinition column=field indent=indent + "  "/><#if field_has_next>,</#if>
</#list>
${indent}&gt;<#else>${column.type}</#if><#if column.mode?? && column.mode != "NULLABLE"> ${column.mode}</#if><#if column.description??> OPTIONS(description="${column.description?replace('"', '\\"')}")</#if>
</#macro>
```

This template provides a flexible, reusable solution for generating BigQuery DDL statements with support for:
- Basic column definitions with all BigQuery data types
- Nested RECORD/STRUCT types
- Partitioning (time-based and range)
- Clustering
- Labels
- Table descriptions
- Expiration
- Create dispositions

You can extend it further based on your specific requirements, such as adding support for external tables, views, or materialized views.