---
name: openapi-designer-template
owner: wenqun
version: 0.0.1
description:
  - xyz
tags:
  - soya
parameters:
  - name
functions:
  - openapi
annotators:
  - operation
renderers:
  - openapi-renderer

---

# [PIPELINE TEMPLATE: openapi-designer-template](.)

## [Pipeline Parameters](./PARAMETERS)

### [Parameter: name](./PARAMETERS/name)
```yaml PARAMETER

```


## [Template Functions](./FUNCTIONS)

### [Function: openapi](./FUNCTIONS/openapi)
**Template**
```python TEMPLATE

```

**Input Schema**
```yaml SCHEMA
$schema: "http://json-schema.org/draft-07/schema#"
type: "object"
properties: 
  name:
    type: "string"

```


## [AnnotatorTemplates](./ANNOTATORS)

### [Annotator Template: operation](./ANNOTATORS/operation)
<pre><code>
</code></pre>

Properties
<pre><code>
</code></pre>

Output Schema
``` SCHEMA

```

TEMPLATE
``` TEMPLATE

```


## [Template Renderers](./RENDERERS)

### [openapi-renderer](./RENDERERS/openapi-renderer)
Function:
<pre><code>
</code></pre>

Function Assignment:
```jsonpath ASSIGNMENT


```


## [Pipeline Initialization](./CONFIGURATION)

### [Pipeline Initializer Template](./CONFIGURATION/INITIALIZER)
**Pipeline Initializer Script**
```python SCRIPT
print('Hello')

```

**Pipeline Initializer Schema**
```json SCHEMA

```

