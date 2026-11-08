package sorya.framework.markita.support;

import sorya.framework.markita.TemplateRenderer;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class RendererDefinition extends TemplateComponentDefinition<DefaultTemplateRenderer> {
    public static final String ASSIGNMENT_TOKEN = "ASSIGNMENT";

    private final TemplateElement assignmentElement = new TemplateElement("jsonpath", null);

    public RendererDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public TemplateElement getAssignmentElement() {
        return assignmentElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        if(getProperties() != null) {
            builder.newLineWithCurrentIndents("Function:");
            builder.newLineWithCurrentIndents("<pre><code>");
            getProperties().forEach((k,v) -> {
                builder.newLineWithCurrentIndents(k).append(": ").append(v);
            });
            builder.newLineWithCurrentIndents("</code></pre>");
            builder.newLine();
        }

        builder.newLineWithCurrentIndents("Function Assignment:")
                .newLineWithCurrentIndents(COMMON_FENCE_TOKEN).append(assignmentElement.getFormat()).append(" ").append(ASSIGNMENT_TOKEN)
                .newLineWithCurrentIndents(assignmentElement.getBody())
                .newLine()
                .newLineWithCurrentIndents(COMMON_FENCE_TOKEN)
                .newLine();

    }

    @Override
    DefaultTemplateRenderer create() {
        return new DefaultTemplateRenderer(getName(), getProperties());
    }
}
