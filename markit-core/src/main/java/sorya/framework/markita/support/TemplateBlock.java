package sorya.framework.markita.support;

import sorya.framework.markita.util.TextBuilder;

public class TemplateBlock {

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
    public static final String DEFAULT_INPUT_FORMAT = "json";

    private String templateFormat = DEFAULT_TEMPLATE_FORMAT;
    private String template = DEFAULT_TEMPLATE_CONTENTS;

    private String inputSchemaFormat = DEFAULT_INPUT_SCHEMA_FORMAT;
    private String inputSchema = DEFAULT_INPUT_SCHEMA;

    private String sampleInputFormat = DEFAULT_INPUT_FORMAT;
    private String sampleInput;

    private String sampleOutputFormat;
    private String sampleOutput;

    public String getTemplateFormat() {
        return templateFormat;
    }

    public void setTemplateFormat(String templateFormat) {
        this.templateFormat = templateFormat;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getInputSchemaFormat() {
        return inputSchemaFormat;
    }

    public void setInputSchemaFormat(String inputSchemaFormat) {
        this.inputSchemaFormat = inputSchemaFormat;
    }

    public String getInputSchema() {
        return inputSchema;
    }

    public void setInputSchema(String inputSchema) {
        this.inputSchema = inputSchema;
    }

    public String getSampleInputFormat() {
        if(sampleInputFormat != null && !sampleInputFormat.trim().isEmpty()) {
            return sampleInputFormat;
        } else {
            return DEFAULT_INPUT_FORMAT;
        }
    }

    public void setSampleInputFormat(String sampleInputFormat) {
        this.sampleInputFormat = sampleInputFormat;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutputFormat() {
        return sampleOutputFormat;
    }

    public void setSampleOutputFormat(String sampleOutputFormat) {
        this.sampleOutputFormat = sampleOutputFormat;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }
}
