package sorya.framework.markita.support;

import sorya.framework.markita.*;

import java.util.HashMap;
import java.util.Map;

public class DefaultTemplatePipelineEngine implements TemplatePipelineEngine {
    private final TemplatePipelineLocator locator;
    protected Map<String, TemplatePipeline> pipelines = new HashMap<>();

    public DefaultTemplatePipelineEngine(TemplatePipelineLocator locator) {
        this.locator = locator;
    }

    @Override
    public String create(String id) {
        return DefaultTemplateContext.newInstance(id).toString();
    }

    @Override
    public String execute(String md) {
        TemplateContext context = new DefaultTemplateContext(md);
        if(context.getTemplate() != null) {
            TemplatePipeline pipeline = locator.getTemplatePipeline(context.getTemplate());

            //pipelines.get(template.getName()).process(context);
            return context.toString();

        } else {
            return md;
        }

    }
}
