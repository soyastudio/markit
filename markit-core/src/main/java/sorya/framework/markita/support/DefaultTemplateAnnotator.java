package sorya.framework.markita.support;

import sorya.framework.markita.TemplateAnnotator;
import sorya.framework.markita.TemplateContext;

public class DefaultTemplateAnnotator implements TemplateAnnotator {

    private String name;

    private String inputTemplateFormat;
    private String inputTemplate;

    private String inputFormat;
    private String inputProcessor;

    private String outputSchema;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFormat() {
        return "";
    }
}
