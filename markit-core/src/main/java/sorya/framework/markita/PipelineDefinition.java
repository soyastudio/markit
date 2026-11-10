package sorya.framework.markita;

import java.util.Map;

public interface PipelineDefinition {
    String[] getFunctionNames();

    String[] getAnnotatorTemplateNames();

    Map<String, String> getAnnotatorDefinitions();
}
