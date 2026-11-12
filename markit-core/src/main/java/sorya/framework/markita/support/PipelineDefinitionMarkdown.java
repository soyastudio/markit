package sorya.framework.markita.support;

import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.PipelineDefinition;

import java.util.Map;

public class PipelineDefinitionMarkdown extends ExecutableMarkdown<PipelineTemplateMarkdown.PipelineTemplateMetadata> implements PipelineDefinition {

    private  DefaultPipelineDefinition pipelineDefinition;

    protected PipelineDefinitionMarkdown(String markdown) {
        super(markdown);
    }

    @Override
    protected void init() {
        root.setTitle("Pipeline Definition: " + metadata.name);

        // Configuration:
        TemplateMarkdownNode configuration = new TemplateMarkdownNode("CONFIGURATION", "Pipeline Configuration", null);
        root.addChild(configuration);

        // - Initializer Template
        PipelineDefinitionDefinition definitionDefinition = new PipelineDefinitionDefinition(metadata.getName(), null);
        TemplateMarkdownNode init = new TemplateMarkdownNode(PipelineInitializerDefinition.INITIALIZER, "Pipeline Initializer Template", definitionDefinition);
        configuration.addChild(init);
    }

    @Override
    protected void configure() {
        this.pipelineDefinition = new DefaultPipelineDefinition(metadata.getName());

    }

    @Override
    protected void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock) {
        TemplateComponentDefinition<?> definition = tn.getComponentDefinition();
        TemplateElement templateElement = TemplateElement.fromMarkdown(fencedCodeBlock);
        if(tn.getComponentDefinition() instanceof PipelineDefinitionDefinition pd) {
            if(PipelineDefinitionDefinition.INIT_SCRIPT.equalsIgnoreCase(templateElement.getName())) {
                pd.getInitScriptElement().set(templateElement);

            } else if(PipelineDefinitionDefinition.INIT_PARAMS.equalsIgnoreCase(templateElement.getName())) {
                pd.getInitParamsElement().set(templateElement);

            } else if (PipelineDefinitionDefinition.DEFINITION.equalsIgnoreCase(templateElement.getName())) {
                pd.getDefinitionElement().set(templateElement);

            }
        }
    }

    @Override
    public AnnotatorDefinition[] getAnnotatorDefinitions() {
        return pipelineDefinition.getAnnotatorDefinitions();
    }

    @Override
    public RendererDefinition[] getRendererDefinitions() {
        return pipelineDefinition.getRendererDefinitions();
    }
}
