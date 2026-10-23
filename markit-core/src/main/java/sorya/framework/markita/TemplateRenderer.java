package sorya.framework.markita;

public interface TemplateRenderer extends TemplateComponent {
    TemplateFunction getTemplate();

    TemplateFunctionAssignment getAssignment();
}
