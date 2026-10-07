package sorya.framework.markita.templates;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import sorya.framework.markita.TemplateExecutor;
import sorya.framework.markita.TemplateExecutorException;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class FreemarkerExecutor implements TemplateExecutor {
    private static Version VERSION;

    @Override
    public Object execute(String templateString, Map<String, Object> parameters) {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_34);
            Template template = new Template("templateName", new StringReader(templateString), cfg);

            StringWriter out = new StringWriter();
            template.process(parameters, out);

            return out.toString();
        } catch (Exception e) {
            return new TemplateExecutorException(e);
        }
    }
}
