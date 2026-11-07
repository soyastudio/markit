package sorya.framework.markita;

import java.util.List;

public interface TemplateSchema extends TemplateComponent {
    List<TemplateParameter> getParameters();
}
