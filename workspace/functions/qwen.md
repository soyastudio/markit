Here is the FreeMarker template for a thread-safe Java Singleton class, followed by the YAML-formatted JSON Schema that defines and validates the variables expected by the template.

### 1. FreeMarker Template (`singleton.ftl`)

This template uses the **Double-Checked Locking** pattern, which is the standard, highly-performant way to implement a thread-safe class-based Singleton in Java.

```freemarker
<#-- Only output the package declaration if packageName is provided and not empty -->
<#if packageName?? && packageName?has_content>
package ${packageName};

</#if>
/**
 * ${className}
<#if description?? && description?has_content>
 * <p>${description}</p>
</#if>
 *
 * <p>Thread-safe Singleton implementation using Double-Checked Locking.</p>
 *
 * @author ${author!"Anonymous"}
 * @since ${creationDate!"2026-10-06"}
 */
public final class ${className} {

    // The volatile keyword ensures multiple threads handle the instance variable correctly
    private static volatile ${className} instance;

    // Private constructor prevents instantiation from other classes
    // Also protects against instantiation via reflection
    private ${className}() {
        if (instance != null) {
            throw new IllegalStateException("Singleton instance already created");
        }
    }

    /**
     * Returns the singleton instance of this class.
     *
     * @return the singleton instance
     */
    public static ${className} getInstance() {
        ${className} result = instance;
        if (result == null) {
            synchronized (${className}.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ${className}();
                }
            }
        }
        return result;
    }
}
```

---

### 2. YAML-Formatted JSON Schema (`singleton-schema.yaml`)

This JSON Schema (written in YAML syntax) strictly defines the data model required to render the FreeMarker template. It includes Java-specific regex patterns to ensure the generated code is syntactically valid.

```yaml
$schema: "http://json-schema.org/draft-07/schema#"
title: "Java Singleton FreeMarker Data Model"
description: "Defines the variables expected by the Java Singleton FreeMarker template."
type: "object"
properties:
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

required:
  - "className"

additionalProperties: false
```

### How to use them together in a pipeline

When building a code generator, you can use the JSON Schema to **validate** the incoming data before passing it to FreeMarker. This prevents runtime errors (like `InvalidReferenceException`) if a user forgets the `className` or passes an invalid package name.

**Example Java Validation & Generation Flow:**

```java
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

public class CodeGenerator {

    public static void generateSingleton(Map<String, Object> dataModel) throws Exception {
        // 1. Load and parse the YAML JSON Schema
        String schemaContent = Files.readString(Paths.get("singleton-schema.yaml"));
        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonSchema schema = factory.getSchema(schemaContent);

        // 2. Validate the data model against the schema
        Set<ValidationMessage> errors = schema.validate(dataModel);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Invalid data model for Singleton generation: " + errors);
        }

        // 3. Process the FreeMarker template
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassLoaderForTemplateLoading(CodeGenerator.class.getClassLoader(), "/templates");
        Template template = cfg.getTemplate("singleton.ftl");

        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);

        // 4. Output the generated code
        System.out.println(writer.toString());
    }
}
```

*(Note: For the Java validation example above, you would need the `com.networknt:json-schema-validator` library in your `pom.xml` or `build.gradle` to parse YAML-based JSON schemas).*