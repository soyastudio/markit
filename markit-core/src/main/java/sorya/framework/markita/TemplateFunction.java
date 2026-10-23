package sorya.framework.markita;

public interface TemplateFunction extends TemplateExecutable, TemplateComponent {
    String getTemplateFormat();

    String getTemplate();

}
