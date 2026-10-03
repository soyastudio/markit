package sorya.framework.markita.support;

import sorya.framework.markita.*;

import java.util.HashMap;
import java.util.Map;

public class DefaultTemplateEngine implements TemplateEngine {
    private final TemplateLocator locator;
    protected Map<String, TemplatePipeline> pipelines = new HashMap<>();

    public DefaultTemplateEngine(TemplateLocator locator) {
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
            Template template = locator.getTemplate(context.getTemplate());
            if(!pipelines.containsKey(template.getName())) {
                pipelines.put(template.getName(), template.create());
            }

            pipelines.get(template.getName()).process(context);
            return context.toString();

        } else {
            return md;
        }

    }
}
