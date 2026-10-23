package sorya.framework.markita.support;

import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultTemplatePipeline implements TemplatePipeline {
    private String name;
    private TemplateSchema schema;
    private final List<TemplateFunction> functions= new ArrayList<>();
    private final List<TemplateAnnotator> annotators = new ArrayList<>();
    private final List<TemplateRenderer> renderers = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TemplateSchema getSchema() {
        return schema;
    }

    public void setSchema(TemplateSchema schema) {
        this.schema = schema;
    }

    @Override
    public List<TemplateFunction> getFunctions() {
        return functions;
    }

    @Override
    public List<TemplateAnnotator> getAnnotators() {
        return annotators;
    }

    @Override
    public List<TemplateRenderer> getRenderers() {
        return renderers;
    }
}
