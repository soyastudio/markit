package sorya.framework.markita.support;

import sorya.framework.markita.TemplateAnnotation;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public class AnnotationDefinition extends TemplateComponentDefinition<TemplateAnnotation> {
    public static final String ANNOTATION_SCRIPT = "ANNOTATION-SCRIPT";
    public static final String ANNOTATION_VALUE = "ANNOTATION-VALUE";

    private final TemplateElement scriptElement = new TemplateElement();
    private final TemplateElement valueElement = new TemplateElement();

    public AnnotationDefinition(String name, Map<String, String> properties) {
        super(name, properties);
    }

    public TemplateElement getScriptElement() {
        return scriptElement;
    }

    public TemplateElement getValueElement() {
        return valueElement;
    }

    @Override
    public void toMarkdown(TextBuilder builder) {
        builder.newLineWithCurrentIndents("**Annotation Script**");
        builder.newLineWithCurrentIndents(startFence(scriptElement.getFormat(), ANNOTATION_SCRIPT));
        builder.newLineWithCurrentIndents(scriptElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();

        builder.newLineWithCurrentIndents("**Annotation Value**");
        builder.newLineWithCurrentIndents(startFence(valueElement.getFormat(), ANNOTATION_VALUE));
        builder.newLineWithCurrentIndents(valueElement.getBody());
        builder.newLineWithCurrentIndents(COMMON_FENCE_TOKEN);
        builder.newLine();
    }

    @Override
    TemplateAnnotation create() {
        return new DefaultTemplateAnnotation();
    }
}
