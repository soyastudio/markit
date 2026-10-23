package sorya.framework.markita.support;

import sorya.framework.markita.TemplateRenderer;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class RendererDefinition extends TemplateComponentDefinition<TemplateRenderer> {


    public RendererDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    @Override
    public void toMarkdown(TextBuilder builder) {

    }

    @Override
    TemplateRenderer create() {
        return new DefaultTemplateRenderer(getName(), getProperties());
    }
}
