package sorya.framework.markita.support;

public class DefaultTemplateAnnotationResolver implements TemplateAnnotationResolver {

    @Override
    public Object resolve(TemplateContextAttribute annotation) {
        if (annotation.getScript() == null) {
            return null;
        } else {
            String format = annotation.getScriptType();
        }

        return null;
    }
}
