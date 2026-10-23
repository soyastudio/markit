package sorya.framework.markita.support;

import org.commonmark.node.FencedCodeBlock;

public class TemplateElement {
    private String format = "";
    private String body = "";

    private TemplateComponentDescriptor componentDescriptor;

    public TemplateElement() {
    }

    public TemplateElement(String format, String body) {
        setFormat(format);
        setBody(body);
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        if(format != null) {
            this.format = format;
        }
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        if(body != null) {
            this.body = body.trim();
        }
    }

    public String getName() {
        return componentDescriptor != null? componentDescriptor.getName() : null;
    }

    public TemplateComponentDescriptor getComponentDescriptor() {
        return componentDescriptor;
    }

    public void setComponentDescriptor(TemplateComponentDescriptor componentDescriptor) {
        this.componentDescriptor = componentDescriptor;
    }

    public static TemplateElement fromMarkdown(FencedCodeBlock fencedCodeBlock) {
        TemplateElement block = new TemplateElement();
        block.setBody(fencedCodeBlock.getLiteral());

        String fenceInfo = fencedCodeBlock.getInfo();
        if (fenceInfo != null && !fenceInfo.isEmpty()) {
            String format = null;
            String uri = null;
            if (fenceInfo.startsWith(" ")) {
                uri = fenceInfo.trim();

            } else if (fenceInfo.contains(" ")) {
                int index = fenceInfo.indexOf(" ");
                format = fenceInfo.substring(0, index);
                block.setFormat(format);
                uri = fenceInfo.substring(index + 1).trim();

            } else {
                block.setFormat(fenceInfo);
            }

            if (uri != null) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(uri);
                block.setComponentDescriptor(descriptor);
            }
        }

        return block;
    }
}
