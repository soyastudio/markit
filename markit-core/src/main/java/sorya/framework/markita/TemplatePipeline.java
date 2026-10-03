package sorya.framework.markita;

import java.util.List;

public interface TemplatePipeline extends TemplateProcessor {
    List<TemplateParameter> parameters();

    List<TemplateAnnotator> annotators();

    List<TemplateEvaluator> evaluators();

    List<TemplateRenderer> renderers();
}
