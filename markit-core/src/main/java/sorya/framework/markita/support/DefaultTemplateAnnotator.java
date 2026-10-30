package sorya.framework.markita.support;

import sorya.framework.markita.TemplateAnnotator;
import sorya.framework.markita.TemplateContext;
import sorya.framework.markita.TemplateSchema;

import java.util.Map;

public class DefaultTemplateAnnotator extends AbstractTemplateComponent implements TemplateAnnotator {

    private TemplateSchema schema;
    private String scriptType;
    private String scriptTemplateFormat;
    private String scriptTemplate;

    public DefaultTemplateAnnotator(String name, Map<String, String> properties) {
        super(name, properties);
    }

    @Override
    public TemplateSchema getSchema() {
        return schema;
    }

    public void setSchema(TemplateSchema schema) {
        this.schema = schema;
    }

    @Override
    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    @Override
    public String getScriptTemplateFormat() {
        return scriptTemplateFormat;
    }

    public void setScriptTemplateFormat(String scriptTemplateFormat) {
        this.scriptTemplateFormat = scriptTemplateFormat;
    }

    @Override
    public String getScriptTemplate() {
        return scriptTemplate;
    }

    public void setScriptTemplate(String scriptTemplate) {
        this.scriptTemplate = scriptTemplate;
    }
}
