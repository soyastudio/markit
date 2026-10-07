package sorya.framework.markita;

import java.util.Map;

public interface TemplateExecutor {
    Object execute(String template, Map<String, Object> parameters) throws TemplateExecutorException;

}
