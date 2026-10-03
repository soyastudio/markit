package sorya.framework.markita;

import java.util.Map;

public interface TemplateContext {
    String getId();

    TemplateContextAttribute getInitAnnotation();

    Map<String, Object> getMetadata();

    String getTemplate();

    String getMessage();

    TemplateContextAttribute getParametersAnnotation();

    String[] getAnnotationNames();

    TemplateContextAttribute getAnnotation(String name);

    Map<String, TemplateContextAttribute> annotations();

    Map<String, TemplateContextAttribute> evaluations();

    String toString();

}
