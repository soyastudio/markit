package sorya.framework.markita.support;

import sorya.framework.markita.TemplateComponent;

import java.util.Map;

public abstract class AbstractTemplateComponent implements TemplateComponent {
    private final String name;
    private final Map<String, String> properties;

    public AbstractTemplateComponent(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
