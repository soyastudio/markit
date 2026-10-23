package sorya.framework.markita.support;

import sorya.framework.markita.TemplateAnnotator;
import sorya.framework.markita.TemplateContext;

import java.util.Map;

public class DefaultTemplateAnnotator extends AbstractTemplateComponent implements TemplateAnnotator {

    private String inputTemplateFormat;
    private String inputTemplate;

    private String inputFormat;
    private String inputProcessor;

    private String outputSchema;

    public DefaultTemplateAnnotator(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public String getFormat() {
        return "";
    }
}
