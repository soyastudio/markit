package sorya.framework.markita.support;

import sorya.framework.markita.TemplateComponent;

import java.util.List;

public interface MarkdownTemplateComponent extends TemplateComponent {

    String getTitle() ;

    List<String> getDescriptions() ;
}
