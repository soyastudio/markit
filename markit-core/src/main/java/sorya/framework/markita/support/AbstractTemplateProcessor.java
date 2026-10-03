package sorya.framework.markita.support;

import sorya.framework.markita.TemplateComponent;
import sorya.framework.markita.TemplateProcessor;

public abstract class AbstractTemplateProcessor implements TemplateProcessor {

    protected String name;
    protected String templateFormat;
    protected String template;
    protected TemplateComponentResolver<? extends TemplateComponent> resolver;

    public AbstractTemplateProcessor() {
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
        return "";
    }

    @Override
    public TemplateComponentResolver<?> getResolver() {
        return resolver;
    }


}
