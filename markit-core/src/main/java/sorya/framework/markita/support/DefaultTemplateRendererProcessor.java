package sorya.framework.markita.support;

import sorya.framework.markita.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTemplateRendererProcessor implements TemplateRendererProcessor {
    @Override
    public Object process(TemplateRenderer renderer, TemplateContext context) {
        return DefaultTemplateFunctionExecutor
                .getInstance()
                .execute(renderer.getTemplate(), evaluate(renderer, context));
    }

    protected Map<String, Object> evaluate(TemplateRenderer renderer, TemplateContext context) {
        Map<String, Object> params = new LinkedHashMap<>();
        TemplateSchema schema = renderer.getTemplate().getSchema();
        TemplateFunctionAssignment assignment = renderer.getAssignment();

        return params;
    }

}
