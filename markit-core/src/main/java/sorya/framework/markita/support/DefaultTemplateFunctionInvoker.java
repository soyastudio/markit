package sorya.framework.markita.support;

import sorya.framework.markita.TemplateExecutor;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionInvoker;
import sorya.framework.markita.TemplateSchema;

import java.util.HashMap;
import java.util.Map;

public class DefaultTemplateFunctionInvoker implements TemplateFunctionInvoker {
    protected static Map<String, TemplateExecutor> executors = new HashMap<>();

    static {

    }

    @Override
    public final Object invoke(Object input, TemplateFunction function) {

        Map<String, Object> params = parseInput(input, function.getSchema());

        TemplateExecutor executor = executors.get(function.getTemplateFormat().toUpperCase());
        if(executor == null) {
            throw new NullPointerException("Cannot find template executor for: " + function.getTemplateFormat());
        }
        Object result = executor.execute(function.getTemplate(), params);
        if(function.getResolver() != null) {
            result = function.getResolver().resolve(result);
        }

        return result;
    }

    protected Map<String, Object> parseInput(Object input, TemplateSchema schema) {
        return new HashMap<>();
    }
}
