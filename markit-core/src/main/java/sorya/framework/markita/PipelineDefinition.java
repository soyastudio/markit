package sorya.framework.markita;


public interface PipelineDefinition extends TemplateComponent {

    AnnotatorDefinition[] getAnnotatorDefinitions();

    RendererDefinition[] getRendererDefinitions();

    interface AnnotatorDefinition {
        String getName();

        String getTemplate();
    }

    interface RendererDefinition {
        String getName();

        String getTemplateFunction();
    }
}
