package sorya.framework.markita.support;

import sorya.framework.markita.util.TextBuilder;

public class PipelineInitParamsDefinition extends TemplateComponentDefinition<PipelineInitParams> {
    public static final String INIT_PARAMS = "INIT-PARAMS";
    private final TemplateElement paramElement = new TemplateElement("yaml", null);

    public PipelineInitParamsDefinition() {
        super("", null);
    }

    public TemplateElement getParamElement() {
        return paramElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents(startFence(paramElement.getFormat(), INIT_PARAMS));
        builder.newLineWithCurrentIndents(paramElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();
    }

    @Override
    PipelineInitParams create() {
        return null;
    }
}
