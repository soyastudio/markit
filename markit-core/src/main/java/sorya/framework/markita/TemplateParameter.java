package sorya.framework.markita;

public interface TemplateParameter {
    String getName();

    String getDescription();

    String getType();

    boolean isRequired();

    String getDefaultValue();

    String[] getExamples();
}
