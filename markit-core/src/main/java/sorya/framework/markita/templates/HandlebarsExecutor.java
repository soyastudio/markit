package sorya.framework.markita.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import sorya.framework.markita.TemplateExecutor;
import sorya.framework.markita.TemplateExecutorException;

import java.util.Map;

public class HandlebarsExecutor implements TemplateExecutor {
    @Override
    public Object execute(String templateString, Map<String, Object> parameters) {
        try {
            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compileInline(templateString);
            return template.apply(parameters);

        } catch (Exception e) {
            throw new TemplateExecutorException(e);
        }
    }
}
