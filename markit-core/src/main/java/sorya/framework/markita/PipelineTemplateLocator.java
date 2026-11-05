package sorya.framework.markita;

public interface PipelineTemplateLocator {

    PipelineTemplate getTemplatePipeline(String name) throws TemplatePipelineNotFoundException;

    class TemplatePipelineNotFoundException extends NullPointerException {
        public TemplatePipelineNotFoundException(String name) {
            super("Template not found: " + name);
        }
    }
}
