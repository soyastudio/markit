package sorya.framework.markita;

public interface PipelineTemplate extends TemplateExecutable {

    TemplateFunction[] getFunctions();

    AnnotatorTemplate[] getAnnotators();

    TemplateRenderer[] getRenderers();

    PipelineDefinition createPipelineDefinition();

    Pipeline create(String name, PipelineDefinition definition);

}
