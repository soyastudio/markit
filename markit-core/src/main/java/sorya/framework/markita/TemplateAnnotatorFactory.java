package sorya.framework.markita;

import java.util.Map;

public interface TemplateAnnotatorFactory {
    Map<String, TemplateAnnotator> create(Map<String, Object> metadata, TemplateAnnotatorTemplate... annotatorTemplate);
}
