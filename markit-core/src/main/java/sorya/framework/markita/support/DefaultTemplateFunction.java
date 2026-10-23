package sorya.framework.markita.support;

import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateSchema;

import java.util.Map;

public class DefaultTemplateFunction extends AbstractTemplateComponent implements TemplateFunction {

    private String templateFormat;
    private String template;
    private TemplateSchema schema;

    public DefaultTemplateFunction(String name, Map<String, String> properties) {
        super(name, properties);
    }


    @Override
    public String getTemplateFormat() {
        return templateFormat;
    }

    public void setTemplateFormat(String templateFormat) {
        this.templateFormat = templateFormat;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public TemplateSchema getSchema() {
        return schema;
    }

    public void setSchema(TemplateSchema schema) {
        this.schema = schema;
    }
}
