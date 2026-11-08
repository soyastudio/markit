package sorya.framework.markita;

import java.util.List;

public interface PipelineTemplate extends TemplateExecutable {

    List<TemplateFunction> getFunctions();

    List<AnnotatorTemplate> getAnnotators();

    List<TemplateRenderer> getRenderers();

    Pipeline create(String name, Object context);
}
