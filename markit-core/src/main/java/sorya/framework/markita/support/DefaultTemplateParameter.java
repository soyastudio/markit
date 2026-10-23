package sorya.framework.markita.support;

import sorya.framework.markita.TemplateParameter;

public class DefaultTemplateParameter implements TemplateParameter {

    private String name;
    private String type = "string";
    private String description;
    private boolean required;
    private String defaultValue;
    private String[] examples;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String[] getExamples() {
        return examples;
    }

    public void setExamples(String[] examples) {
        this.examples = examples;
    }

    public String forExample() {
        if (getExamples() != null && getExamples().length > 0) {
            return getExamples()[0];

        } else if (defaultValue != null) {
            return defaultValue;

        } else if (isRequired()) {
            return "exampleOf" + name;

        } else {
            return null;

        }
    }
}
