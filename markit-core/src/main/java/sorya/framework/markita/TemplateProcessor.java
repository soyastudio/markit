package sorya.framework.markita;

import sorya.framework.markita.support.TemplateComponentResolver;

public interface TemplateProcessor {

    String getName();

    String getTemplateFormat();

    String getTemplate();

    TemplateComponentResolver<?> getResolver();

    void process(TemplateContext context);
}
