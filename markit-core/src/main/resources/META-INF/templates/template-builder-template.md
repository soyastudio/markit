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