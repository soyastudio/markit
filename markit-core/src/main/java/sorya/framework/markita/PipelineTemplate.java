package sorya.framework.markita;

public interface PipelineTemplate extends TemplateExecutable {

    TemplateFunction[] getFunctions();

    AnnotatorTemplate[] getAnnotators();

    TemplateRenderer[] getRenderers();

    Pipeline create(String name, Object context);
}
