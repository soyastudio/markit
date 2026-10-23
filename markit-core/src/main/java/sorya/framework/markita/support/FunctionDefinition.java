package sorya.framework.markita.support;

import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class FunctionDefinition extends TemplateComponentDefinition<TemplateFunction> {

    public static final String DEFAULT_TEMPLATE_FORMAT = "freemarker";
    public static final String DEFAULT_TEMPLATE_CONTENTS = TextBuilder.builder("Hello ${name}!").toString();
    public static final String DEFAULT_INPUT_SCHEMA_FORMAT = SchemaFormat.YAML.name().toLowerCase();
    public static final String DEFAULT_INPUT_SCHEMA = TextBuilder.builder("$schema: \"http://json-schema.org/draft-07/schema#\"").setIndent(2)
            .newLineWithCurrentIndents("type: \"object\"")
            .newLineWithCurrentIndents("properties: ")
            .indentRight()
            .newLineWithCurrentIndents("name:")
            .indentRight()
            .newLineWithCurrentIndents("type: \"string\"")
            .indentLeft()
            .indentLeft().toString();

    private TemplateElement templateElement = new TemplateElement(DEFAULT_TEMPLATE_FORMAT, DEFAULT_TEMPLATE_CONTENTS);
    private TemplateElement schemaElement = new TemplateElement(DEFAULT_INPUT_SCHEMA_FORMAT, DEFAULT_INPUT_SCHEMA);

    public FunctionDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public TemplateElement getTemplateElement() {
        return templateElement;
    }

    public void setTemplateElement(TemplateElement templateElement) {
        this.templateElement = templateElement;
    }

    public TemplateElement getSchemaElement() {
        return schemaElement;
    }

    public void setSchemaElement(TemplateElement schemaElement) {
        this.schemaElement = schemaElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents("**Template**")
                .newLineWithCurrentIndents(templateStartFence(templateElement.getFormat()))
                .newLineWithCurrentIndents(templateElement.getBody())
                .newLine()
                .newLineWithCurrentIndents(COMMON_FENCE_TOKEN)
                .newLine();

        builder.newLineWithCurrentIndents("**Input Schema**")
                .newLineWithCurrentIndents(schemaStartFence(schemaElement.getFormat()))
                .newLineWithCurrentIndents(schemaElement.getBody())
                .newLine()
                .newLineWithCurrentIndents(COMMON_FENCE_TOKEN)
                .newLine();

    }

    @Override
    TemplateFunction create() {
        DefaultTemplateFunction function = new DefaultTemplateFunction(getName(), getProperties());
        function.setTemplateFormat(templateElement.getFormat());
        function.setTemplate(templateElement.getBody());
        function.setSchema(DefaultTemplateSchema.create(schemaElement.getFormat(), schemaElement.getBody()));
        return function;
    }
}
