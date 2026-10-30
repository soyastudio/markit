package sorya.framework.markita;

import java.util.Map;

public interface TemplateFunctionInvoker {
    Object invoke(TemplateFunction function, Map<String, Object> input);
}
