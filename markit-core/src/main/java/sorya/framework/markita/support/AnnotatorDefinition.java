package sorya.framework.markita.support;

import sorya.framework.markita.TemplateAnnotator;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class AnnotatorDefinition extends TemplateComponentDefinition<TemplateAnnotator> {

    private String scriptType;
    private final TemplateElement schemaElement = new TemplateElement();
    private final TemplateElement templateElement = new TemplateElement();

    public AnnotatorDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public String getScriptType() {
        return scriptType;
    }

    public TemplateElement getSchemaElement() {
        return schemaElement;
    }

    public TemplateElement getTemplateElement() {
        return templateElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {

        if(getProperties() != null) {
            builder.newLineWithCurrentIndents("Properties");
            builder.newLineWithCurrentIndents("<pre><code>");
            getProperties().forEach((k,v) -> {
                builder.newLineWithCurrentIndents(k).append(": ").append(v);
            });
            builder.newLineWithCurrentIndents("</code></pre>");
            builder.newLine();
        }

        builder.newLineWithCurrentIndents("Output Schema");
        builder.newLineWithCurrentIndents(schemaStartFence(schemaElement.getFormat()));
        builder.newLineWithCurrentIndents(schemaElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

        builder.newLineWithCurrentIndents("TEMPLATE");
        builder.newLineWithCurrentIndents(templateStartFence(templateElement.getFormat()));
        builder.newLineWithCurrentIndents(templateElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

    }

    @Override
    TemplateAnnotator create() {
        DefaultTemplateAnnotator annotator = new DefaultTemplateAnnotator(getName(), getProperties());


        return annotator;
    }
}
