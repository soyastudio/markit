package sorya.framework.markita.support;

import java.util.ArrayList;
import java.util.List;

public class PipelineMetadata extends ExecutableMarkdown.Metadata {

    private List<String> annotators = new ArrayList<>();
    private List<String> renderers = new ArrayList<>();
    private String template;

    public List<String> getAnnotators() {
        return annotators;
    }

    public void setAnnotators(List<String> annotators) {
        this.annotators = annotators;
    }

    public List<String> getRenderers() {
        return renderers;
    }

    public void setRenderers(List<String> renderers) {
        this.renderers = renderers;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    protected String[] getDeclaredFieldNames() {
        return new String[]{"annotators", "renderers", "template"};
    }
}
