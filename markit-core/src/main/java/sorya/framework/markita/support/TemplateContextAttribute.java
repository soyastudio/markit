package sorya.framework.markita.support;

import sorya.framework.markita.Annotator;

import java.util.ArrayList;
import java.util.List;

public class TemplateContextAttribute implements Annotator, MarkdownTemplateComponent {

    private final String name;
    private final String title;
    private final List<String> descriptions = new ArrayList<>();

    private final String scriptType;
    private String script;

    private Object value;
    private String message;

    public TemplateContextAttribute(String name, String title, String scriptType) {
        this.name = name;
        this.title = title;
        this.scriptType = scriptType;
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

    public String getScriptType() {
        return scriptType;
    }

    @Override
    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
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
