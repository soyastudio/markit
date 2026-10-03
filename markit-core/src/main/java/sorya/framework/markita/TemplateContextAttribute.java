package sorya.framework.markita;

import java.util.ArrayList;
import java.util.List;

public class TemplateContextAttribute implements TemplateComponent {

    private final String name;
    private final String title;
    private final List<String> descriptions = new ArrayList<>();

    private final String format;
    private String payload;

    private Object value;
    private String message;

    public TemplateContextAttribute(String name, String title, String format) {
        this.name = name;
        this.title = title;
        this.format = format;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<String> getDescriptions() {
        return descriptions;
    }

    @Override
    public String getFormat() {
        return format;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
