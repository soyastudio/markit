package sorya.framework.markita.support;

import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultPipelineTemplate implements PipelineTemplate {

    private String name;
    private final TemplateSchema schema = new DefaultTemplateSchema();
    private final List<TemplateFunction> functions= new ArrayList<>();
    private final List<TemplateAnnotator> annotators = new ArrayList<>();
    private final List<TemplateRenderer> renderers = new ArrayList<>();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TemplateSchema getSchema() {
        return schema;
    }

    public void setName(String name) {
        this.name = name;
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
