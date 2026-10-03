package sorya.framework.markita;

public interface Template {

    String getName();

    String toString();

    TemplatePipeline create();

}
