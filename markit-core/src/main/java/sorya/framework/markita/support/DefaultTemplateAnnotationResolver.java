package sorya.framework.markita.support;

import sorya.framework.markita.TemplateContextAttribute;

public class DefaultTemplateAnnotationResolver implements TemplateAnnotationResolver {

    @Override
    public Object resolve(TemplateContextAttribute annotation) {
        if (annotation.getPayload() == null) {
            return null;
        } else {
            String format = annotation.getFormat();
        }

        return null;
    }
}
