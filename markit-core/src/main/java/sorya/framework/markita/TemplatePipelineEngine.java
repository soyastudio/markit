package sorya.framework.markita;

public interface TemplatePipelineEngine {

    String create(String id);

    String execute(String md);
}
