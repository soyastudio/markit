package sorya.framework.markita.support;

import sorya.framework.markita.TemplateParameter;

public class DefaultTemplateParameter implements TemplateParameter {

    private final String name;
    private final String type;
    private boolean required;
    private String defaultValue;

    public DefaultTemplateParameter(String name) {
        this.name = name;
        this.type = "String";
    }

    public DefaultTemplateParameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public DefaultTemplateParameter(String name, String type, boolean required, String defaultValue) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
