package sorya.framework.markita;

public interface TemplateAnnotation extends TemplateComponent {
    String getScriptType();

    String getScript();

    Object getValue();

    String getMessage();
}
