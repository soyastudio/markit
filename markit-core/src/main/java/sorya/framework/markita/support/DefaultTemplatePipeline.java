package sorya.framework.markita.support;

import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultTemplatePipeline extends AbstractTemplateProcessor implements TemplatePipeline {

    private final DefaultTemplate template;
    private final List<TemplateParameter> parameters = new ArrayList<>();
    private final List<TemplateAnnotator> annotators = new ArrayList<>();
    private final List<TemplateEvaluator> evaluators = new ArrayList<>();
    private final List<TemplateRenderer> renderers = new ArrayList<>();

    public DefaultTemplatePipeline(DefaultTemplate template) {
        this.template = template;

        this.parameters.addAll(template.parameters());

        template.annotatorBuilders().forEach((k, v) -> {
            annotators.add(v.create());
        });

        template.evaluatorBuilders().forEach((k, v) -> {
            evaluators.add(v.create());
        });

        template.rendererBuilders().forEach((k, v) -> {
            renderers.add(v.create());
        });
    }

    @Override
    public List<TemplateParameter> parameters() {
        return parameters;
    }

    @Override
    public List<TemplateAnnotator> annotators() {
        return annotators;
    }

    @Override
    public List<TemplateEvaluator> evaluators() {
        return evaluators;
    }

    @Override
    public List<TemplateRenderer> renderers() {
        return renderers;
    }

    @Override
    public void process(TemplateContext context) {
        DefaultTemplateContext dtc = (DefaultTemplateContext) context;
        // 1. authenticate user:
        if (authenticate(dtc)) {
            // 2. reset context states:
            reset(dtc);

            // 3. annotate:
            annotate(dtc);

            // 4. evaluate:
            evaluate(dtc);

            // 5 render:
            render(dtc);

        }


    }

    protected boolean authenticate(DefaultTemplateContext context) {
        return true;
    }

    protected void reset(DefaultTemplateContext context) {

    }

    protected void annotate(DefaultTemplateContext context) {

    }

    protected void evaluate(DefaultTemplateContext context) {

    }

    protected void render(DefaultTemplateContext context) {

    }
}
