package sorya.framework.markita.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.List;

public class PipelineTemplateMarkdown extends ExecutableMarkdown<PipelineTemplateMarkdown.PipelineTemplateMetadata> implements PipelineTemplate {

    public static final String PARAMETERS = "PARAMETERS";
    public static final String FUNCTIONS = "FUNCTIONS";
    public static final String ANNOTATORS = "ANNOTATORS";
    public static final String RENDERERS = "RENDERERS";
    public static final String CONFIGURATION = "CONFIGURATION";

    private static final String EMPTY_PIPELINE_TEMPLATE;

    private DefaultPipelineTemplate pipeline;

    static {
        try {
            EMPTY_PIPELINE_TEMPLATE = loadFromClasspath("META-INF/templates/empty-pipeline-template.md");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String newInstance(String name) {
        return EMPTY_PIPELINE_TEMPLATE.replace("${name}", name);
    }

    public PipelineTemplateMarkdown(String markdown) {
        super(markdown);
    }

    private void test() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(pipeline));
    }

    @Override
    protected void init() {
        root.setTitle("Template Pipeline: " + metadata.name);

        // Parameters:
        TemplateMarkdownNode parameters = new TemplateMarkdownNode(PARAMETERS, "Pipeline Parameters", null);
        root.addChild(parameters);
        metadata.parameters.forEach(e -> {
            if (!e.trim().isEmpty() && !e.trim().startsWith("# ")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                parameters.addChild(new TemplateMarkdownNode(descriptor.getName(),
                        "Parameter: " + descriptor.getName(),
                        new PipelineParameterDefinition(descriptor.getName(), descriptor.getProperties())));
            }
        });

        // Functions:
        TemplateMarkdownNode functions = new TemplateMarkdownNode(FUNCTIONS, "Template Functions", null);
        root.addChild(functions);

        metadata.functions.forEach(e -> {
            if (!e.trim().isEmpty() && !e.trim().startsWith("# ")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                functions.addChild(new TemplateMarkdownNode(descriptor.getName(),
                        "Function: " + descriptor.getName(),
                        new FunctionDefinition(descriptor.getName(), descriptor.getProperties())));
            }
        });

        // Annotators:
        TemplateMarkdownNode annotators = new TemplateMarkdownNode(ANNOTATORS, "Annotator Templates", null);
        root.addChild(annotators);

        metadata.annotators.forEach(e -> {
            if (!e.trim().isEmpty() && !e.trim().startsWith("# ")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                annotators.addChild(new TemplateMarkdownNode(descriptor.getName(),
                        "Annotator Template: " + descriptor.getName(),
                        new AnnotatorDefinition(descriptor.getName(), descriptor.getProperties())));
            }
        });

        // Renderers:
        TemplateMarkdownNode renderers = new TemplateMarkdownNode(RENDERERS, "Template Renderers", null);
        root.addChild(renderers);

        metadata.renderers.forEach(e -> {
            if (!e.trim().isEmpty() && !e.trim().startsWith("# ")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                RendererDefinition rendererDefinition = new RendererDefinition(descriptor.getName(), descriptor.getProperties());

                if (rendererDefinition.getProperties() != null && rendererDefinition.getProperties().get("function") != null) {
                    String function = rendererDefinition.getProperties().get("function");
                }
                renderers.addChild(new TemplateMarkdownNode(descriptor.getName(), "Renderer: " + descriptor.getName(),
                        rendererDefinition));
            }
        });

        // Configuration:
        TemplateMarkdownNode configuration = new TemplateMarkdownNode(CONFIGURATION, "Pipeline Configuration", null);
        root.addChild(configuration);
        // - Initializer Template
        PipelineInitializerDefinition initializerDefinition = new PipelineInitializerDefinition(PipelineInitializerDefinition.INITIALIZER, null);
        TemplateMarkdownNode init = new TemplateMarkdownNode(PipelineInitializerDefinition.INITIALIZER, "Pipeline Initializer Template", initializerDefinition);
        configuration.addChild(init);

    }

    @Override
    protected void configure() {
        this.pipeline = new DefaultPipelineTemplate();
        pipeline.setName(metadata.name);
        root.getChild(PARAMETERS).getChildren().forEach((k, v) -> {
            PipelineParameterDefinition definition = (PipelineParameterDefinition) v.getComponentDefinition();
            pipeline.getSchema().getParameters().add(definition.create());
        });

        root.getChild(FUNCTIONS).getChildren().forEach((k, v) -> {
            FunctionDefinition definition = (FunctionDefinition) v.getComponentDefinition();
            pipeline.getFunctions().add(definition.create());
        });

        root.getChild(ANNOTATORS).getChildren().forEach((k, v) -> {
            AnnotatorDefinition definition = (AnnotatorDefinition) v.getComponentDefinition();
            pipeline.getAnnotators().add(definition.create());
        });

        root.getChild(RENDERERS).getChildren().forEach((k, v) -> {
            RendererDefinition definition = (RendererDefinition) v.getComponentDefinition();
            pipeline.getRenderers().add(definition.create());
        });

        TemplateMarkdownNode configNode = root.getChild(CONFIGURATION);
        PipelineInitializerDefinition initializerDefinition = (PipelineInitializerDefinition) configNode.getChild(PipelineInitializerDefinition.INITIALIZER).getComponentDefinition();


        test();
    }

    @Override
    protected void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock) {

        TemplateComponentDefinition<?> definition = tn.getComponentDefinition();
        TemplateElement templateElement = TemplateElement.fromMarkdown(fencedCodeBlock);

        if (definition instanceof PipelineInitializerDefinition initializerDefinition) {
            if (PipelineInitializerDefinition.INIT_SCRIPT.equalsIgnoreCase(templateElement.getName())) {
                initializerDefinition.getInitScriptElement().set(templateElement);
            } else if (PipelineInitializerDefinition.INIT_SCHEMA.equalsIgnoreCase(templateElement.getName())) {
                initializerDefinition.getInitSchemaElement().set(templateElement);
            }

        } else if (definition instanceof SchemaDefinition schemaDefinition) {
            if (templateElement.getBody() != null && !templateElement.getBody().isEmpty()) {
                schemaDefinition.getSchemaElement().set(templateElement);
            }

        } else if (definition instanceof FunctionDefinition functionDefinition) {
            if (FunctionDefinition.TEMPLATE_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                functionDefinition.getTemplateElement().set(templateElement);
            } else if (FunctionDefinition.SCHEMA_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                functionDefinition.getSchemaElement().set(templateElement);
            }

        } else if (definition instanceof AnnotatorDefinition annotatorDefinition) {
            if (AnnotatorDefinition.SCHEMA_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                annotatorDefinition.getSchemaElement().set(templateElement);
            } else if (AnnotatorDefinition.TEMPLATE_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                annotatorDefinition.getTemplateElement().set(templateElement);
            }

        } else if (definition instanceof RendererDefinition rendererDefinition) {
            if (RendererDefinition.ASSIGNMENT_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                rendererDefinition.getAssignmentElement().set(templateElement);
            }
        }

    }

    @Override
    public TemplateSchema getSchema() {
        return pipeline.getSchema();
    }

    @Override
    public List<TemplateFunction> getFunctions() {
        return pipeline.getFunctions();
    }

    @Override
    public List<TemplateAnnotator> getAnnotators() {
        return pipeline.getAnnotators();
    }

    @Override
    public List<TemplateRenderer> getRenderers() {
        return pipeline.getRenderers();
    }

    static class PipelineTemplateMetadata extends Metadata {
        List<String> parameters = new ArrayList<>();
        List<String> functions = new ArrayList<>();
        List<String> annotators = new ArrayList<>();
        List<String> renderers = new ArrayList<>();

        @Override
        protected String[] getDeclaredFieldNames() {
            return new String[]{"parameters", "functions", "annotators", "renderers"};
        }
    }
}
