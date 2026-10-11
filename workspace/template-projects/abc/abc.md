---
name: abc
owner: wenqun
tags:
  - "Zoya"
functions:
  - "bigquery-table-ddl"
renderers:
  - "[define a renderer in following format: renderer_name?format=rendered_format&resolver=optional_resolver]"
evaluators:
  - "[define an evaluator in following format: renderer_name?format=yaml&resolver=optional_resolver]"
annotators:
  - "[define an annotator in following format: annotator_name?format=annotator_format&resolver=optional_resolver]"
schemas:
  - "[define an annotation schema in following format: annotator_name?format=schema_format]"
---
# Complex Template Design Worksheet
<pre>
What is ...
</pre>

<pre>
What is function
</pre>


## [TEMPLATE FUNCTIONS](#./FUNCTIONS)
<pre>
What is function
</pre>


### [bigquery-table-ddl](#./FUNCTIONS/bigquery-table-ddl)
<pre>
Function desc
</pre>

Template
```ftl
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

Input Schema
```yaml
$schema: "http://json-schema.org"
type: object
properties:
  name:
    type: string
  age:
    type: integer
    minimum: 0
  email:
    type: string
    format: email
required:
  - name
  - email
```

Output Schema
~~~yaml
$schema: "http://json-schema.org"
type: object
properties:
  name:
    type: string
  age:
    type: integer
    minimum: 0
  email:
    type: string
    format: email
required:
  - name
  - email
~~~

## [TEMPLATE RENDERERS](#./RENDERERS)

## [RENDERER EVALUATORS](#./EVALUATORS)

## [TEMPLATE ANNOTATORS](#./ANNOTATORS)

## [ANNOTATION SCHEMAS](#./SCHEMAS)
