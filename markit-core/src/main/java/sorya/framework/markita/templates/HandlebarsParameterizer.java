package sorya.framework.markita.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
//import com.github.jknack.handlebars.internal.Section;
//import com.github.jknack.handlebars.internal.Variable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import sorya.framework.markita.TemplateParameterizer;
import sorya.framework.markita.TemplateSchema;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class HandlebarsParameterizer implements TemplateParameterizer {
    @Override
    public TemplateSchema parameterize(String template) {
        return null;
    }


    /**
     * Parses a Handlebars template and returns a YAML schema string
     * describing every variable it references.
     */
    public static String extractToYaml(String templateSource) throws Exception {
        Handlebars hb = new Handlebars();
        Template template = hb.compileInline(templateSource);

        // Collect raw variable names (e.g. "user.name", "age")
        Set<String> rawVars = new LinkedHashSet<>();
        walk(template, rawVars);

        // Build a nested map representing the schema
        Map<String, Object> schema = buildSchema(rawVars);
        return toYaml(schema);
    }

    /** Recursive AST walker. */
    private static void walk(Template node, Set<String> vars) {
        /*if (node instanceof Variable) {
            vars.add(((Variable) node).getName());
        } else if (node instanceof Section) {
            // {{#if x}}, {{#each items}}, {{#with user}} all reference a variable
            vars.add(((Section) node).getName());
        }

        Template[] children = node.children();
        if (children != null) {
            for (Template child : children) {
                walk(child, vars);
            }
        }*/
    }

    /**
     * Expands dotted paths into a nested JSON-Schema-like structure.
     * e.g. "user.name" -> properties.user.properties.name
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> buildSchema(Set<String> rawVars) {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("type", "object");
        Map<String, Object> rootProps = new LinkedHashMap<>();
        root.put("properties", rootProps);

        for (String path : rawVars) {
            String[] parts = path.split("\\.");
            Map<String, Object> current = rootProps;

            for (int i = 0; i < parts.length; i++) {
                String part = parts[i];
                boolean isLeaf = (i == parts.length - 1);

                if (!current.containsKey(part)) {
                    Map<String, Object> node = new LinkedHashMap<>();
                    node.put("type", isLeaf ? "string" : "object");
                    if (!isLeaf) {
                        node.put("properties", new LinkedHashMap<>());
                    }
                    current.put(part, node);
                }

                if (!isLeaf) {
                    Object next = ((Map<String, Object>) current.get(part)).get("properties");
                    current = (Map<String, Object>) next;
                }
            }
        }
        return root;
    }

    private static String toYaml(Object schema) {
        DumperOptions opts = new DumperOptions();
        opts.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        opts.setPrettyFlow(true);
        return new Yaml(opts).dump(schema);
    }

    // ---- Demo ----
    public static void main(String[] args) throws Exception {
        String tpl =
                "Hello {{user.name}}, you are {{user.age}} years old.\n" +
                        "{{#if user.address.city}}City: {{user.address.city}}{{/if}}\n" +
                        "{{#each tags}}{{this}}{{/each}}\n" +
                        "Raw: {{{rawHtml}}}";

        System.out.println(extractToYaml(tpl));
    }
}
