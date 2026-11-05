package sorya.framework.markita;

public interface TemplateAnnotator extends TemplateComponent {

    TemplateSchema getSchema();

    String getScriptType();

    String getScriptTemplateFormat();

    String getScriptTemplate();
}
