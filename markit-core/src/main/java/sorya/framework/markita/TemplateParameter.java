package sorya.framework.markita;

public interface TemplateParameter extends TemplateComponent {
    String getName();

    String getDescription();

    String getType();

    boolean isRequired();

    String getDefaultValue();

    String[] getExamples();
}
