package sorya.framework.markita.support;

import org.apache.commons.text.StringSubstitutor;
import sorya.framework.markita.TemplateExecutor;

import java.util.Map;

public class DefaultTemplateExecutor implements TemplateExecutor {
    @Override
    public String execute(String template, Map<String, Object> parameters) {
        return new StringSubstitutor(parameters).replace(template);
    }
}
