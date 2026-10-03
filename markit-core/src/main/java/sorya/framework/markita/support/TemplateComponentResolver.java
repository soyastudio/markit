package sorya.framework.markita.support;

import sorya.framework.markita.TemplateComponent;

public interface TemplateComponentResolver<T extends TemplateComponent> {
    Object resolve(T component);
}
