# Template Verification

### Template
```handlebars TEMPLATE
CREATE TABLE IF NOT EXISTS `{{dataset}}.{{table_name}}` (
  {{#each schema}}
  `{{name}}` {{type}}{{#if mode}} {{mode}}{{/if}}{{#if description}} OPTIONS(description="{{description}}"){{/if}}{{#unless @last}},{{/unless}}
  {{/each}}
)
{{#if partition_field}}
PARTITION BY {{partition_field}}
{{/if}}
{{#if clustering_fields}}
CLUSTER BY {{#each clustering_fields}}{{this}}{{#unless @last}}, {{/unless}}{{/each}}
{{/if}}
OPTIONS(
  description="{{description}}"
);
```

### Input Data
```yaml INPUT
table_name: user_events
dataset: analytics
description: "User behavior events"
partition_field: event_date
clustering_fields:
  - user_id
  - event_type
schema:
  - name: event_date
    type: DATE
    mode: NULLABLE
    description: "Event date"
  - name: user_id
    type: STRING
    mode: REQUIRED
    description: "User ID"
  - name: event_type
    type: STRING
    mode: NULLABLE
  - name: properties
    type: JSON
    mode: NULLABLE
```