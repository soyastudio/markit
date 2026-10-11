---
name: xyz
owner: wenqun
tags:
  - "[add tag for index and search]"
functions:
  - "function"
renderers:
  - "renderer"
evaluators:
  - "[define an evaluator in following format: renderer_name?format=yaml&resolver=optional_resolver]"
annotators:
  - "annotator"
---
# Complex Template Design Worksheet

## [TEMPLATE FUNCTIONS](#./FUNCTIONS)

### [function](#./FUNCTIONS/function)
Template
```


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

### [renderer](#./RENDERERS/renderer)
Template
```


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

## [RENDERER EVALUATORS](#./EVALUATORS)

### [renderer](#./EVALUATORS/renderer)
Template
```


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

## [TEMPLATE ANNOTATORS](#./ANNOTATORS)

### [annotator](#./ANNOTATORS/annotator)
Template
```


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
