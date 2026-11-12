package sorya.framework.markita.support;

import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class PipelineDefinitionDefinition extends TemplateComponentDefinition<DefaultPipelineDefinition> {

    public static final String INIT_SCRIPT = "SCRIPT";
    public static final String INIT_PARAMS = "PARAMS";
    public static final String DEFINITION = "DEFINITION";

    private final TemplateElement initScriptElement = new TemplateElement("python", null);
    private final TemplateElement initParamsElement = new TemplateElement("yaml", null);
    private final TemplateElement definitionElement = new TemplateElement("json", null);

    public PipelineDefinitionDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public TemplateElement getInitScriptElement() {
        return initScriptElement;
    }

    public TemplateElement getInitParamsElement() {
        return initParamsElement;
    }

    public TemplateElement getDefinitionElement() {
        return definitionElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents("**Pipeline Initializer Script**");
        builder.newLineWithCurrentIndents(startFence(initScriptElement.getFormat(), INIT_SCRIPT));
        builder.newLineWithCurrentIndents(initScriptElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

        builder.newLineWithCurrentIndents("**Pipeline Initializer Parameters**");
        builder.newLineWithCurrentIndents(startFence(initParamsElement.getFormat(), INIT_PARAMS));
        builder.newLineWithCurrentIndents(initParamsElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

        builder.newLineWithCurrentIndents("**Pipeline Definition**");
        builder.newLineWithCurrentIndents(startFence(definitionElement.getFormat(), DEFINITION));
        builder.newLineWithCurrentIndents(definitionElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();
    }

    @Override
    DefaultPipelineDefinition create() {
        DefaultPipelineDefinition definition = new DefaultPipelineDefinition(getName());

        return definition;
    }
}
