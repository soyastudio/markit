package sorya.framework.markita.support;

import com.google.gson.*;
import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.*;
import sorya.framework.markita.util.ZipEncryptUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PipelineMarkdown extends ExecutableMarkdown<PipelineMetadata> implements Pipeline {

    public static final String PARAMETERS = "PARAMETERS";
    public static final String ANNOTATIONS = "ANNOTATIONS";
    public static final String RENDERERS = "RENDERERS";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final List<TemplateParameter> schema = new ArrayList<>();
    private Map<String, TemplateFunction> functions = new LinkedHashMap<>();
    private Map<String, AnnotatorTemplate> annotators = new LinkedHashMap<>();

    private final Map<String, Object> parameters = new LinkedHashMap<>();
    private final Map<String, Annotator> annotations = new LinkedHashMap<>();
    private final Map<String, TemplateRenderer> renderers = new LinkedHashMap<>();

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
        parse(metadata.getTemplate());

        root.setTitle("PIPELINE: " + metadata.getName());
        TemplateMarkdownNode parameters = new TemplateMarkdownNode(PARAMETERS, "Pipeline Parameters", null);
        root.addChild(parameters);

        TemplateMarkdownNode annotators = new TemplateMarkdownNode(ANNOTATIONS, "Annotations", null);
        root.addChild(annotators);

        TemplateMarkdownNode renderers = new TemplateMarkdownNode(RENDERERS, "Renderers", null);
        root.addChild(renderers);
    }

    private void parse(String template) {
        try {
            String json = ZipEncryptUtils.base64DecodeAndUnzip(template);



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
