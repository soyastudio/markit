package sorya.framework.markita;

public interface TemplateBuilder {
    String init(String templateName);

    String build(String markdown);

    Template create(String markdown);
}
