package sorya.framework.markita.support;

import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.Pipeline;
import sorya.framework.markita.TemplateAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PipelineMarkdown extends ExecutableMarkdown<PipelineMetadata> implements Pipeline {

    public static final String INIT_PARAMS = "INIT_PARAMS";
    public static final String ANNOTATORS = "ANNOTATORS";

    private Object initResource;

    private final Map<String, Object> parameters = new LinkedHashMap<>();
    private final Map<String, TemplateAnnotation> annotations = new LinkedHashMap<>();

    public PipelineMarkdown(String markdown) {
        super(markdown);
    }

    public PipelineMetadata getMetadata() {
        return metadata;
    }

    public Object getInitResource() {
        return initResource;
    }

    @Override
    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public Map<String, TemplateAnnotation> getAnnotations() {
        return annotations;
    }

    @Override
    protected void init() {
        root.setTitle("PIPELINE: " + metadata.getName());

        TemplateMarkdownNode initParams = new TemplateMarkdownNode(INIT_PARAMS);
        initParams.setTitle("PIPELINE PARAMETER INITIALIZATION");
        root.addChild(initParams);

        TemplateMarkdownNode init = new TemplateMarkdownNode(PipelineInitParamsDefinition.INIT_PARAMS, new PipelineInitParamsDefinition());
        init.setTitle("Pipeline Parameters Initialization");
        initParams.addChild(init);


        TemplateMarkdownNode annotators = new TemplateMarkdownNode(ANNOTATORS);
        root.addChild(annotators);

        try {
            PipelineResourceExtractor<?> initializer = (PipelineResourceExtractor<?>) Class.forName(metadata.getInitializer()).getDeclaredConstructor()
                    .newInstance();

            initializer.init(this);
            this.initResource = initializer.getResource();

            // TODO: initialize the pipeline markdown

        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
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
