---
name: soya.framework
owner: wenqun
version: 0.0.1
description:
tags:
  - java
functions: 
  - Singleton
metadata:
---

# [Template Function Package: soya.framework](.)
<!--
hidden information
-->

<pre>
Hello World!
</pre>


## [FUNCTIONS](./FUNCTIONS)

### [Singleton](./FUNCTIONS/Singleton)
**Template**
```freemarker TEMPLATE
<#if packageName?has_content>
package ${packageName};
</#if>

/**
 * ${className} - Singleton class.
<#if description?has_content>
 * <p>${description}</p>
</#if>
 * 
 * <p>This implementation uses Double-Checked Locking to ensure thread safety 
 * while minimizing the performance cost of synchronization.</p>
 *
 * @author ${author!"Anonymous"}
 * @since ${date!"2026-10-03"}
 */
public class ${className} {

    // The volatile keyword ensures that multiple threads handle the instance variable correctly
    private static volatile ${className} instance;

    // Private constructor prevents instantiation from other classes
    private ${className}() {
    }

    /**
     * Public static method for getting the singleton instance.
     *
     * @return the singleton instance of ${className}
     */
    public static ${className} getInstance() {
        if (instance == null) {
            synchronized (${className}.class) {
                if (instance == null) {
                    instance = new ${className}();
                }
            }
        }
        return instance;
    }
}

```

**Input Schema**
```yaml SCHEMA
packageName:
  type: "string"
  description: "The Java package name for the generated class."
  pattern: "^[a-z][a-z0-9_]*(\\.[a-z][a-z0-9_]*)*$"
  examples:
    - "com.example.service"
    - "org.myapp.core.utils"

className:
  type: "string"
  description: "The name of the Singleton class. Must follow Java PascalCase naming conventions."
  pattern: "^[A-Z][a-zA-Z0-9]*$"
  examples:
    - "DatabaseConnectionManager"
    - "AppConfig"

description:
  type: "string"
  description: "A brief description of the Singleton's purpose, injected into the Javadoc."
  default: "A thread-safe singleton class."

author:
  type: "string"
  description: "The author of the generated class."
  default: "Anonymous"

creationDate:
  type: "string"
  description: "The creation date of the class, formatted as YYYY-MM-DD."
  format: "date"
  default: "2026-10-06"

```

