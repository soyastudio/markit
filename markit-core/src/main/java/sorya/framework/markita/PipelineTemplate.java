package sorya.framework.markita;

import java.util.List;

public interface PipelineTemplate extends TemplateExecutable {

    TemplateSchema getSchema();

    List<TemplateFunction> getFunctions();

    List<TemplateAnnotator> getAnnotators();

    TemplateAnnotator getAnnotator(String name);

    List<TemplateRenderer> getRenderers();
}
