package sorya.framework.markita.support;

import sorya.framework.markita.TemplateSchema;
import sorya.framework.markita.util.TextBuilder;

public class SchemaDefinition extends TemplateComponentDefinition<TemplateSchema> {

    private TemplateElement schemaElement = new TemplateElement(SchemaFormat.YAML.name().toLowerCase(), "");

    public SchemaDefinition() {
        super(null, null);
    }

    public TemplateElement getSchemaElement() {
        return schemaElement;
    }

    public void setSchemaElement(TemplateElement schemaElement) {
        this.schemaElement = schemaElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents("**Input Schema**")
                .newLineWithCurrentIndents(schemaStartFence(schemaElement.getFormat()))
                .newLineWithCurrentIndents(schemaElement.getBody())
                .newLine()
                .newLineWithCurrentIndents(COMMON_FENCE_TOKEN)
                .newLine();
    }

    @Override
    TemplateSchema create() {
        return DefaultTemplateSchema.create(schemaElement.getFormat(), schemaElement.getBody());
    }
}
