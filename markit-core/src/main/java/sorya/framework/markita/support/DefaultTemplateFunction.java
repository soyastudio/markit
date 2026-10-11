package sorya.framework.markita.support;

import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateResolver;
import sorya.framework.markita.TemplateSchema;

public class DefaultTemplateFunction implements TemplateFunction {

    private final String name;
    private final String templateFormat;
    private final String template;
    private final TemplateSchema schema;

    public DefaultTemplateFunction(String name, String templateFormat, String template, TemplateSchema schema) {
        this.name = name;
        this.templateFormat = templateFormat;
        this.template = template;
        this.schema = schema;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTemplateFormat() {
        return templateFormat;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public TemplateSchema getSchema() {
        return schema;
    }
}
