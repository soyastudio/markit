package sorya.framework.markita.support;

import org.apache.commons.text.StringSubstitutor;
import sorya.framework.markita.Template;
import sorya.framework.markita.TemplateBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTemplateBuilder implements TemplateBuilder {


    @Override
    public String init(String templateName) {
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("name", templateName);
        return new StringSubstitutor(context).replace(TemplateBuilderContext.EMPTY_TEMPLATE);
    }

    @Override
    public String build(String markdown) {
        TemplateBuilderContext tt = new TemplateBuilderContext(markdown);
        return tt.toString();
    }

    @Override
    public Template create(String markdown) {
        return null;
    }
}
