package sorya.framework.markita;

public interface TemplateFunction {
    String getName();

    String getTemplateFormat();

    String getTemplate();

    TemplateSchema getSchema();

    TemplateResolver getResolver();

    String getOutputFormat();

    TemplateSchema getOutputSchema();
}
