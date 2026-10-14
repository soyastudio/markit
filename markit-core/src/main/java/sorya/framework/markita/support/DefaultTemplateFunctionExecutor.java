package sorya.framework.markita.support;

import sorya.framework.markita.TemplateExecutor;
import sorya.framework.markita.TemplateExecutorException;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionAssignment;
import sorya.framework.markita.templates.FreemarkerExecutor;
import sorya.framework.markita.templates.HandlebarsExecutor;

import java.util.HashMap;
import java.util.Map;

public class DefaultTemplateFunctionExecutor {

    private static DefaultTemplateFunctionExecutor INSTANCE;
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

    protected DefaultTemplateFunctionExecutor() {
        if(INSTANCE != null) {
            throw new  IllegalStateException("DefaultTemplateFunctionExecutor instance already exist");
        }
        INSTANCE = this;
    }

    public static DefaultTemplateFunctionExecutor getInstance() {
        if(INSTANCE == null) {
            new DefaultTemplateFunctionExecutor();
        }

        return INSTANCE;
    }

    public Object execute(TemplateFunction function, Map<String, Object> parameters) throws TemplateExecutorException {
        return executors.get(function.getTemplateFormat().toUpperCase()).execute(function.getTemplate(), parameters);
    }
}
