package sorya.framework.markita.support;

import com.google.gson.*;
import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.*;
import sorya.framework.markita.util.ZipEncryptUtils;

import java.util.*;

public class PipelineMarkdown extends ExecutableMarkdown<PipelineMetadata> implements Pipeline {

    public static final String PARAMETERS = "PARAMETERS";
    public static final String ANNOTATIONS = "ANNOTATIONS";
    public static final String RENDERERS = "RENDERERS";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private TemplateSchema schema;
    private Map<String, TemplateFunction> functions;
    private Map<String, AnnotatorTemplate> annotatorTemplates;

    private Map<String, Object> parameters;
    private Map<String, Annotator> annotations;
    private Map<String, TemplateRenderer> renderers;

    public PipelineMarkdown(String markdown) {
        super(markdown);
    }

    public PipelineMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public Map<String, Annotator> getAnnotations() {
        return annotations;
    }

    @Override
    protected void init() {
        this.functions = new LinkedHashMap<>();
        this.annotatorTemplates = new LinkedHashMap<>();

        this.parameters = new LinkedHashMap<>();
        this.annotations = new LinkedHashMap<>();
        this.renderers = new LinkedHashMap<>();

        initTemplate(metadata.getTemplate());

        root.setTitle("PIPELINE: " + metadata.getName());
        TemplateMarkdownNode parameters = new TemplateMarkdownNode(PARAMETERS, "Pipeline Parameters", null);
        root.addChild(parameters);

        TemplateMarkdownNode annotators = new TemplateMarkdownNode(ANNOTATIONS, "Annotations", null);
        root.addChild(annotators);

        TemplateMarkdownNode renderers = new TemplateMarkdownNode(RENDERERS, "Renderers", null);
        root.addChild(renderers);
    }

    private void initTemplate(String template) {
        try {
            String json = ZipEncryptUtils.base64DecodeAndUnzip(template);
            PipelineTemplate pipelineTemplate = DefaultPipelineTemplate.fromJson(json);

            this.schema = pipelineTemplate.getSchema();
            Arrays.stream(pipelineTemplate.getFunctions()).forEach(f -> {
                functions.put(f.getName(), f);
            });

            Arrays.stream(pipelineTemplate.getAnnotators()).forEach(a -> {
                annotatorTemplates.put(a.getName(), a);
            });

            Arrays.stream(pipelineTemplate.getRenderers()).forEach(r -> {
                renderers.put(r.getName(), r);
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void configure() {

    }

    @Override
    protected void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock) {

    }

}
