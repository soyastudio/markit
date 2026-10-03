package sorya.framework.markita;

public interface TemplateLocator {

    Template getTemplate(String name) throws TemplateNotFoundException;

    class TemplateNotFoundException extends NullPointerException {
        public TemplateNotFoundException(String name) {
            super("Template not found: " + name);
        }
    }
}
