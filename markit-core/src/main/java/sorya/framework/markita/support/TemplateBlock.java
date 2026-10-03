package sorya.framework.markita.support;

public class TemplateBlock {

    private String templateFormat;
    private String template;

    private String inputSchemaFormat;
    private String inputSchema;

    private String outputSchemaFormat;
    private String outputSchema;

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
}
