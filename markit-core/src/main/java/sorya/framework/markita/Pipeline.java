package sorya.framework.markita;

import java.util.Map;

public interface Pipeline {
    Map<String, Object> getParameters();

    Map<String, TemplateAnnotation> getAnnotations();
}
