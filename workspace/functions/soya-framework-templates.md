---
name: soya.framework.templates
owner: wenqun
tags:
  - "[add tag for index and search]"
functions:
  - "Hello"
---
# Template Function Package: soya.framework.templates

## [Hello](#./Hello)
Template
```freemarker
Hello ${name}!
```

Input Schema
```yaml
$schema: "http://json-schema.org/draft-07/schema#"
type: "object"
properties: 
  name:
    type: "string"
```

Sample Input (Optional)
~~~json input
{
  "name": "World"
}
~~~
Sample Output (Optional)
~~~ output

~~~
