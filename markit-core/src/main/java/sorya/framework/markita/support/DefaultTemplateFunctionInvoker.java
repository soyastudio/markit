package sorya.framework.markita.support;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sorya.framework.markita.*;
import sorya.framework.markita.templates.FreemarkerExecutor;
import sorya.framework.markita.templates.MustacheExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTemplateFunctionInvoker implements TemplateFunctionInvoker {
    protected static Map<String, TemplateExecutor> executors = new HashMap<>();

    static {
        executors.put("MUSTACHE", new MustacheExecutor());
        executors.put("FREEMARKER", new FreemarkerExecutor());
        executors.put("FTL", executors.get("FREEMARKER"));
    }

    @Override
    public final Object invoke(TemplateFunction function, Object input) {

        Map<String, Object> params = parseInput(input, function.getSchema());

        TemplateExecutor executor = executors.get(function.getTemplateFormat().toUpperCase());
        if (executor == null) {
            throw new NullPointerException("Cannot find template executor for: " + function.getTemplateFormat());
        }

        Object result = executor.execute(function.getTemplate(), params);
        if (function.getResolver() != null) {
            result = function.getResolver().resolve(result);
        }

        return result;
    }

    protected Map<String, Object> parseInput(Object input, TemplateSchema schema) {
        Gson gson = new Gson();
        String json = gson.toJson(input);
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
        Map<String, Object> result = new LinkedHashMap<>();
        Arrays.stream(schema.parameters()).forEach(p -> {
            if (map.containsKey(p.getName())) {
                result.put(p.getName(), map.get(p.getName()));
            } else if (p.getDefaultValue() != null) {
                result.put(p.getName(), evaluate(p, p.getDefaultValue()));

            } else if (p.isRequired()) {
                throw new IllegalArgumentException("Property is required: " + p.getName());
            }
        });

        return result;
    }

    protected Object evaluate(TemplateParameter parameter, Object value) {
        return value;
    }
}
