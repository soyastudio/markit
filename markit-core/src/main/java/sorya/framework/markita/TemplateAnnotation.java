package sorya.framework.markita;

public interface TemplateAnnotation extends TemplateComponent {
    String getPayload();

    Object getValue();

    String getMessage();
}
