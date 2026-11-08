package sorya.framework.markita;

public interface Annotator extends TemplateComponent {

    String getScriptType();

    String getScript();

    Object getValue();

    String getMessage();
}
