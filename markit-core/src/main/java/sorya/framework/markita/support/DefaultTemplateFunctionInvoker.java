package sorya.framework.markita.support;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sorya.framework.markita.*;
import sorya.framework.markita.templates.FreemarkerExecutor;
import sorya.framework.markita.templates.HandlebarsExecutor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTemplateFunctionInvoker implements TemplateFunctionInvoker {
    protected static Map<String, TemplateExecutor> executors = new HashMap<>();

    static {
        // Freemarker
        TemplateExecutor freeMarkerExecutor = new FreemarkerExecutor();
        executors.put("FREEMARKER", freeMarkerExecutor);
        executors.put("FTL", freeMarkerExecutor);

        // Mustache/Handlebars
        TemplateExecutor mustacheExecutor = new HandlebarsExecutor();
        executors.put("MUSTACHE", mustacheExecutor);
        executors.put("HANDLEBARS", mustacheExecutor);
        executors.put("HBS", mustacheExecutor);

    }

    @Override
    public final Object invoke(TemplateFunction function, String input) {
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

    protected Map<String, Object> parseInput(String input, TemplateSchema schema) {
        /*Map<String, Object> result = new LinkedHashMap<>();
        if(input != null) {
            Gson gson = new Gson();
            Map<String, Object> map = gson.fromJson(input,
                    new TypeToken<Map<String, Object>>() {
                    }.getType());
            Arrays.stream(schema.parameters()).forEach(p -> {
                if (map.containsKey(p.getName())) {
                    result.put(p.getName(), map.get(p.getName()));
                } else if (p.getDefaultValue() != null) {
                    result.put(p.getName(), p.getDefaultValue());

                } else if (p.isRequired()) {
                    throw new IllegalArgumentException("Property is required: " + p.getName());
                }
            });
        }*/

        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(input,
                new TypeToken<Map<String, Object>>() {
                }.getType());

        return map;

        //return result;
    }
}
