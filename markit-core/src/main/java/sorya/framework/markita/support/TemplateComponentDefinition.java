package sorya.framework.markita.support;

import sorya.framework.markita.TemplateComponent;
import sorya.framework.markita.util.TextBuilder;

import java.util.Map;

public abstract class TemplateComponentDefinition<T extends TemplateComponent> {
    public static final String COMMON_FENCE_TOKEN = "```";
    public static final String TEMPLATE_TOKEN = "TEMPLATE";
    public static final String SCHEMA_TOKEN = "SCHEMA";

    private final String name;
    private final Map<String, String> properties;

    public TemplateComponentDefinition(String name, Map<String, String> properties) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public abstract void toMarkdown(TextBuilder builder);

    public static String schemaStartFence(String schemaFormat) {
        return startFence(schemaFormat, SCHEMA_TOKEN);
    }

    public static String templateStartFence(String templateFormat) {
        return startFence(templateFormat, TEMPLATE_TOKEN);
    }

    public static String startFence(String format, String token) {
        String formatPart = format == null ? "" : format;
        String namePart = token == null ? "" : " " + token;
        return COMMON_FENCE_TOKEN + formatPart + namePart;
    }

    abstract T create();


}
