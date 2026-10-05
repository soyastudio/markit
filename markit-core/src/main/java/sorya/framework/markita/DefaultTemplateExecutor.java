package sorya.framework.markita;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class DefaultTemplateExecutor implements TemplateExecutor {
    @Override
    public String execute(String template, Map<String, Object> parameters) {
        return new StringSubstitutor(parameters).replace(template);
    }
}
