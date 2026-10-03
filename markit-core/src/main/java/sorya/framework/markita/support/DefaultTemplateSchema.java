package sorya.framework.markita.support;

import sorya.framework.markita.TemplateParameter;
import sorya.framework.markita.TemplateSchema;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTemplateSchema implements TemplateSchema {
    private final Map<String, TemplateParameter> params;

    private DefaultTemplateSchema(Map<String, TemplateParameter> params) {
        this.params = params;
    }

    @Override
    public TemplateParameter[] parameters() {
        return params.values().toArray(new TemplateParameter[0]);
    }

    public TemplateParameter get(String name) {
        return params.get(name);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final Map<String, TemplateParameter> params = new LinkedHashMap<>();

        private Builder() {
        }

        public Builder addParameter(String name) {
            if(params.containsKey(name)) {
                throw new IllegalStateException("Parameter already exists: " + name);
            }
            params.put(name, new DefaultTemplateParameter(name));
            return this;
        }

        public Builder addParameter(String name, String type, boolean required, String defaultValue) {
            if(params.containsKey(name)) {
                throw new IllegalStateException("Parameter already exists: " + name);
            }
            params.put(name, new DefaultTemplateParameter(name, type, required, defaultValue));
            return this;
        }

        public DefaultTemplateSchema create() {
            return new DefaultTemplateSchema(params);
        }
    }

}
