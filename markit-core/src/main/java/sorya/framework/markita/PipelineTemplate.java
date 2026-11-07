package sorya.framework.markita;

import java.util.List;

public interface PipelineTemplate extends TemplateExecutable {

    List<TemplateFunction> getFunctions();

    List<TemplateAnnotator> getAnnotators();

    List<TemplateRenderer> getRenderers();
}
