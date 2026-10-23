package sorya.framework.markita;

import java.util.List;

public interface TemplatePipeline extends TemplateExecutable {
    TemplateSchema getSchema();

    List<TemplateFunction> getFunctions();

    List<TemplateAnnotator> getAnnotators();

    List<TemplateRenderer> getRenderers();
}
