package sorya.framework.markita.support;

import sorya.framework.markita.TemplateAnnotator;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class AnnotatorDefinition extends TemplateComponentDefinition<TemplateAnnotator> {

    private String outputSchemaFormat;
    private String outputSchema;

    private String templateFormat;
    private String template;

    public AnnotatorDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public String getOutputSchemaFormat() {
        return outputSchemaFormat;
    }

    public void setOutputSchemaFormat(String outputSchemaFormat) {
        this.outputSchemaFormat = outputSchemaFormat;
    }

    public String getOutputSchema() {
        return outputSchema;
    }

    public void setOutputSchema(String outputSchema) {
        this.outputSchema = outputSchema;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents("Output Schema");
        builder.newLineWithCurrentIndents(schemaStartFence(getOutputSchemaFormat()));
        builder.newLineWithCurrentIndents(outputSchema);
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

        builder.newLineWithCurrentIndents("TEMPLATE");
        builder.newLineWithCurrentIndents(templateStartFence(templateFormat));
        builder.newLineWithCurrentIndents(template);
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

    }

    @Override
    TemplateAnnotator create() {
        DefaultTemplateAnnotator annotator = new DefaultTemplateAnnotator(getName(), getProperties());


        return annotator;
    }
}
