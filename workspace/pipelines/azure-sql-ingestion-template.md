---
name: azure-sql-ingestion-template
owner: wenqun
version: 0.0.1
description:
  - xyz
tags:
  - ABC
  - xyz
parameters: 
  - bod_name
  - application
functions: 
  - create-bq-table
annotators: 
  - project-properties?format=yaml
  - table-definition-query?format=sql
  - table-definition?format=csv
renderers: 
  - data-catalog-generator
metadata:
---

# [Azure SQL Ingestion Template](.)

## [PARAMETERS](./PARAMETERS)

### [SCHEMA](./PARAMETERS/schema)
**Input Schema**
```yaml SCHEMA
bod_name:
  type: "string"
  description: "bod_name_desc"
  required: true
  defaultValue: "default_value_of_bod_name"
  examples:
    - "example_of_bod_name"

application:
  type: "string"
  description: "description_of_application"
  required: true
  defaultValue: "default_value_of_application"
  examples:
    - "example_of_application"

```


## [FUNCTIONS](./FUNCTIONS)

### [create-bq-table](./FUNCTIONS/create-bq-table)
**Template**
```freemarker TEMPLATE
Hello ${name}!

```

**Input Schema**
```yaml SCHEMA
$schema: "http://json-schema.org/draft-07/schema#"
type: "object"
properties: 
  name:
    type: "string"

```


## [ANNOTATORS](./ANNOTATORS)

### [project-properties](./ANNOTATORS/project-properties)
Output Schema
``` SCHEMA

```

TEMPLATE
``` TEMPLATE

```


### [table-definition-query](./ANNOTATORS/table-definition-query)
Output Schema
``` SCHEMA

```

TEMPLATE
``` TEMPLATE

```


### [table-definition](./ANNOTATORS/table-definition)
Output Schema
``` SCHEMA

```

TEMPLATE
``` TEMPLATE

```


## [RENDERERS](./RENDERERS)

### [data-catalog-generator](./RENDERERS/data-catalog-generator)
