package sorya.framework.markita.support;

import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.*;

import java.util.ArrayList;
import java.util.List;

public class PipelineTemplateMarkdown extends ExecutableMarkdown<PipelineTemplateMarkdown.PipelineTemplateMetadata> implements PipelineTemplate {

    public static final String SCHEMA = "schema";
    public static final String PARAMETERS = "PARAMETERS";
    public static final String FUNCTIONS = "FUNCTIONS";
    public static final String ANNOTATORS = "ANNOTATORS";
    public static final String RENDERERS = "RENDERERS";

    private DefaultPipelineTemplate pipeline;

    public PipelineTemplateMarkdown(String markdown) {
        super(markdown);
    }

    @Override
    protected void init() {
        root.setTitle("Template Pipeline: " + metadata.name);

        TemplateMarkdownNode parameters = new TemplateMarkdownNode(PARAMETERS);
        parameters.setTitle(PARAMETERS);
        root.addChild(parameters);
        SchemaDefinition schemaDefinition = new SchemaDefinition();
        StringBuilder builder = new StringBuilder();
        metadata.parameters.forEach(p -> {
            builder.append(p).append(":\n");
            builder.append("  type: \"string\"\n");
            builder.append("  description: \"description_of_").append(p).append("\"\n");
            builder.append("  required: true\n");
            builder.append("  defaultValue: \"default_value_of_").append(p).append("\"\n");
            builder.append("  examples:\n");
            builder.append("    - \"example_of_").append(p).append("\"\n");
            builder.append("\n");
        });

        schemaDefinition.getSchemaElement().setBody(builder.toString());
        TemplateMarkdownNode schema = new TemplateMarkdownNode(SCHEMA, schemaDefinition);
        schema.setTitle("SCHEMA");
        parameters.addChild(schema);

        TemplateMarkdownNode functions = new TemplateMarkdownNode(FUNCTIONS);
        functions.setTitle(FUNCTIONS);
        root.addChild(functions);

        TemplateMarkdownNode annotators = new TemplateMarkdownNode(ANNOTATORS);
        annotators.setTitle(ANNOTATORS);
        root.addChild(annotators);

        TemplateMarkdownNode renderers = new TemplateMarkdownNode(RENDERERS);
        renderers.setTitle(RENDERERS);
        root.addChild(renderers);

        metadata.functions.forEach(e -> {
            if (!e.startsWith("[")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                functions.addChild(new TemplateMarkdownNode(descriptor.getName(), new FunctionDefinition(descriptor.getName(), descriptor.getProperties())));
            }
        });

        metadata.annotators.forEach(e -> {
            TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
            annotators.addChild(new TemplateMarkdownNode(descriptor.getName(), new AnnotatorDefinition(descriptor.getName(), descriptor.getProperties())));
        });

        metadata.renderers.forEach(e -> {
            TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
            RendererDefinition rendererDefinition = new RendererDefinition(descriptor.getName(), descriptor.getProperties());

            if(rendererDefinition.getProperties() != null  && rendererDefinition.getProperties().get("function") != null) {
                String function = rendererDefinition.getProperties().get("function");
            }

            renderers.addChild(new TemplateMarkdownNode(descriptor.getName(), rendererDefinition));
        });
    }

    @Override
    protected void configure() {
        this.pipeline = new DefaultPipelineTemplate();
        pipeline.setName(metadata.name);
        SchemaDefinition schemaDefinition = (SchemaDefinition) root.getChild(PARAMETERS).getChild(SCHEMA).getComponentDefinition();
        pipeline.setSchema(schemaDefinition.create());

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
    }

    @Override
    protected void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock) {

        TemplateComponentDefinition<?> definition = tn.getComponentDefinition();
        TemplateElement templateElement = TemplateElement.fromMarkdown(fencedCodeBlock);

        if(definition instanceof SchemaDefinition schemaDefinition) {
            if(templateElement.getBody() != null && !templateElement.getBody().isEmpty()) {
                schemaDefinition.getSchemaElement().set(templateElement);
            }

        } else if (definition instanceof FunctionDefinition functionDefinition) {
            if(FunctionDefinition.TEMPLATE_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                functionDefinition.getTemplateElement().set(templateElement);
            } else if(FunctionDefinition.SCHEMA_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                functionDefinition.getSchemaElement().set(templateElement);
            }

        } else if(definition instanceof AnnotatorDefinition annotatorDefinition) {
            if(AnnotatorDefinition.SCHEMA_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                annotatorDefinition.getSchemaElement().set(templateElement);
            } else if(AnnotatorDefinition.TEMPLATE_TOKEN.equalsIgnoreCase(templateElement.getName())) {
                annotatorDefinition.getTemplateElement().set(templateElement);
            }

        } else if (definition instanceof RendererDefinition rendererDefinition) {
            if(RendererDefinition.ASSIGNMENT_TOKEN.equalsIgnoreCase(templateElement.getName())) {
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
    public TemplateAnnotator getAnnotator(String name) {
        return pipeline.getAnnotator(name);
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
