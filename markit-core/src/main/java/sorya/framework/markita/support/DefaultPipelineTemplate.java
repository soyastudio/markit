package sorya.framework.markita.support;

import com.google.gson.*;
import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultPipelineTemplate implements PipelineTemplate {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private String name;
    private final DefaultTemplateSchema schema = new DefaultTemplateSchema();
    private final List<TemplateFunction> functions= new ArrayList<>();
    private final List<AnnotatorTemplate> annotators = new ArrayList<>();
    private final List<TemplateRenderer> renderers = new ArrayList<>();

    private transient final PipelineTemplateMarkdown markdown;

    public DefaultPipelineTemplate() {
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
    public List<AnnotatorTemplate> getAnnotators() {
        return annotators;
    }

    @Override
    public List<TemplateRenderer> getRenderers() {
        return renderers;
    }

    @Override
    public Pipeline create(String name, Object context) {
        return markdown.create(name, context);
    }

    public static String toJson(PipelineTemplate pipelineTemplate) {
        return gson.toJson(pipelineTemplate);
    }

    public static PipelineTemplate fromJson(String json) {
        DefaultPipelineTemplate template = new DefaultPipelineTemplate();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        JsonElement schema = jsonObject.get("schema");
        if(schema != null) {
            JsonElement params = schema.getAsJsonObject().get("parameters");
            if(params != null) {
                params.getAsJsonArray().forEach(p -> {
                    DefaultTemplateParameter parameter = gson.fromJson(p, DefaultTemplateParameter.class);

                });
            }
        }

        JsonElement funcs = jsonObject.get("functions");
        if(funcs != null) {
            funcs.getAsJsonArray().forEach(f -> {
                DefaultTemplateFunction function = gson.fromJson(f, DefaultTemplateFunction.class);
            });
        }



        return template;
    }
}
