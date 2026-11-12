package sorya.framework.markita.support;

import sorya.framework.markita.PipelineDefinition;

import java.util.ArrayList;
import java.util.List;

public class DefaultPipelineDefinition implements PipelineDefinition {

    private final String name;
    private final List<DefaultAnnotatorDefinition> annotatorDefinitions = new ArrayList<>();
    private final List<DefaultRendererDefinition> rendererDefinitions = new ArrayList<>();

    public DefaultPipelineDefinition(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DefaultAnnotatorDefinition[] getAnnotatorDefinitions() {
        return annotatorDefinitions.toArray(new DefaultAnnotatorDefinition[0]);
    }

    public void addAnnotatorDefinition(String name, String template) {
        annotatorDefinitions.add(new DefaultAnnotatorDefinition(name, template));
    }

    @Override
    public DefaultRendererDefinition[] getRendererDefinitions() {
        return rendererDefinitions.toArray(new DefaultRendererDefinition[0]);
    }

    public void addRendererDefinition(String name, String functionName) {
        rendererDefinitions.add(new DefaultRendererDefinition(name, functionName));
    }

    public static class DefaultAnnotatorDefinition implements PipelineDefinition.AnnotatorDefinition {
        private final String name;
        private final String template;

        public DefaultAnnotatorDefinition(String name, String template) {
            this.name = name;
            this.template = template;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getTemplate() {
            return template;
        }
    }

    public static class DefaultRendererDefinition implements RendererDefinition {
        private final String name;
        private final String templateFunction;

        public DefaultRendererDefinition(String name, String templateFunction) {
            this.name = name;
            this.templateFunction = templateFunction;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getTemplateFunction() {
            return templateFunction;
        }
    }

}
