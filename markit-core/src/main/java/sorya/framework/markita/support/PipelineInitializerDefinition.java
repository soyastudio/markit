package sorya.framework.markita.support;

import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class PipelineInitializerDefinition extends TemplateComponentDefinition<PipelineInitializer> {

    public static final String INITIALIZER = "INITIALIZER";
    public static final String INIT_SCRIPT = "SCRIPT";
    public static final String INIT_SCHEMA = "SCHEMA";

    private final TemplateElement initScriptElement = new TemplateElement("python", null);
    private final TemplateElement initSchemaElement = new TemplateElement("yaml", null);

    public PipelineInitializerDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public TemplateElement getInitScriptElement() {
        return initScriptElement;
    }

    public TemplateElement getInitSchemaElement() {
        return initSchemaElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents("**Pipeline Initializer Script**");
        builder.newLineWithCurrentIndents(startFence(initScriptElement.getFormat(), INIT_SCRIPT));
        builder.newLineWithCurrentIndents(initScriptElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

        builder.newLineWithCurrentIndents("**Pipeline Initializer Schema**");
        builder.newLineWithCurrentIndents(startFence(initSchemaElement.getFormat(), INIT_SCHEMA));
        builder.newLineWithCurrentIndents(initSchemaElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();
    }

    @Override
    PipelineInitializer create() {
        PipelineInitializer initializer = new PipelineInitializer();
        initializer.setScriptType(initScriptElement.getFormat());
        initializer.setScript(initScriptElement.getBody());
        initializer.setSchemaFormat(initializer.getSchemaFormat());
        initializer.setSchema(initSchemaElement.getBody());

        return initializer;
    }
}
