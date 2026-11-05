package sorya.framework.markita.support;

import java.util.ArrayList;
import java.util.List;

public class PipelineMetadata extends ExecutableMarkdown.Metadata {

    private String status;
    private String initializer;

    private List<String> annotators = new ArrayList<>();

    public String getInitializer() {
        return initializer;
    }

    public void setInitializer(String initializer) {
        this.initializer = initializer;
    }

    public List<String> getAnnotators() {
        return annotators;
    }

    public void setAnnotators(List<String> annotators) {
        this.annotators = annotators;
    }

    @Override
    protected String[] getDeclaredFieldNames() {
        return new String[]{"initializer", "annotators"};
    }
}
