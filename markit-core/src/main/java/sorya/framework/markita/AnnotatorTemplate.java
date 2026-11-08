package sorya.framework.markita;

public interface AnnotatorTemplate extends TemplateComponent {

    TemplateSchema getSchema();

    String getScriptType();

    String getScriptTemplateFormat();

    String getScriptTemplate();
}
