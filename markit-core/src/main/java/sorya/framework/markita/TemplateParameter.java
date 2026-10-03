package sorya.framework.markita;

public interface TemplateParameter {
    String getName();

    String getType();

    boolean isRequired();

    String getDefaultValue();
}
