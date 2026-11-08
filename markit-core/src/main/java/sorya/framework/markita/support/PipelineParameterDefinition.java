package sorya.framework.markita.support;

import sorya.framework.markita.TemplateParameter;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class PipelineParameterDefinition extends TemplateComponentDefinition<DefaultTemplateParameter> {

    public static String PARAMETER = "PARAMETER";

    private final TemplateElement parameterElement = new TemplateElement("yaml", "");

    public PipelineParameterDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public TemplateElement getParameterElement() {
        return parameterElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {

        builder.newLineWithCurrentIndents(startFence(parameterElement.getFormat(), PARAMETER));
        builder.newLineWithCurrentIndents(parameterElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();
    }

    @Override
    DefaultTemplateParameter create() {
        return null;
    }
}
