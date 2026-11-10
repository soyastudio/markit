package sorya.framework.markita.support;

import com.google.gson.*;
import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultPipelineTemplate implements PipelineTemplate {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String name;
    private final DefaultTemplateSchema schema = new DefaultTemplateSchema();
    private final List<DefaultTemplateFunction> functions= new ArrayList<>();
    private final List<DefaultAnnotatorTemplate> annotators = new ArrayList<>();
    private final List<DefaultTemplateRenderer> renderers = new ArrayList<>();

    private transient final PipelineTemplateMarkdown markdown;

    protected DefaultPipelineTemplate() {
        this.markdown = null;
    }

    DefaultPipelineTemplate(PipelineTemplateMarkdown markdown) {
        this.markdown = markdown;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DefaultTemplateSchema getSchema() {
        return schema;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public TemplateFunction[] getFunctions() {
        return functions.toArray(new TemplateFunction[0]);
    }

    @Override
    public AnnotatorTemplate[] getAnnotators() {
        return annotators.toArray(new AnnotatorTemplate[0]);
    }

    @Override
    public TemplateRenderer[] getRenderers() {
        return renderers.toArray(new TemplateRenderer[0]);
    }

    public void addFunction(DefaultTemplateFunction function) {
        functions.add(function);
    }

    public void addAnnotator(DefaultAnnotatorTemplate annotatorTemplate) {
        annotators.add(annotatorTemplate);
    }

    public void addRenderer(DefaultTemplateRenderer renderer) {
        renderers.add(renderer);
    }

    @Override
    public Pipeline create(String name, Object context) {
        return markdown.create(name, context);
    }

    public static String toJson(PipelineTemplate pipelineTemplate) {
        return gson.toJson(pipelineTemplate);
    }

    public static PipelineTemplate fromJson(String json) {
        return gson.fromJson(json, DefaultPipelineTemplate.class);
    }
}
