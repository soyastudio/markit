package sorya.framework.markita.support;

import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionAssignment;
import sorya.framework.markita.TemplateRenderer;

import java.util.Map;

public class DefaultTemplateRenderer extends AbstractTemplateComponent implements TemplateRenderer {

    public DefaultTemplateRenderer(String name, Map<String, String> properties) {
        super(name, properties);
    }

    @Override
    public TemplateFunction getTemplate() {
        return null;
    }

    @Override
    public TemplateFunctionAssignment getAssignment() {
        return null;
    }
}
