package sorya.framework.markita.support;

import org.commonmark.ext.front.matter.YamlFrontMatterBlock;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterNode;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.yaml.snakeyaml.Yaml;
import sorya.framework.markita.TemplateContextAttribute;
import sorya.framework.markita.TemplateContext;
import sorya.framework.markita.util.TextBuilder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultTemplateContext implements TemplateContext {

    public static final String INIT_BLOCK_NAME = "init";
    public static final String INIT_BLOCK_FORMAT = "yaml";
    public static final String INIT_BLOCK_TITLE = "";

    public static final String PARAM_BLOCK_NAME = "parameters";
    public static final String PARAM_BLOCK_FORMAT = "yaml";
    public static final String PARAM_BLOCK_TITLE = "Parameters";

    private String id;
    private TemplateContextAttribute initAnnotation;

    private Map<String, List<String>> metadata;
    private DefaultTemplate template;

    private String message;
    private TemplateContextAttribute parameters = new TemplateContextAttribute(PARAM_BLOCK_NAME, PARAM_BLOCK_TITLE, PARAM_BLOCK_FORMAT);
    private Map<String, TemplateContextAttribute> annotations = new LinkedHashMap<>();
    private Map<String, TemplateContextAttribute> evaluations = new LinkedHashMap<>();

    public DefaultTemplateContext(String md) {
        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                .build();
        Node document = parser.parse(md);
        document.accept(new Visitor());
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public TemplateContextAttribute getInitAnnotation() {
        return null;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Map.of();
    }

    @Override
    public String getTemplate() {
        return "";
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public TemplateContextAttribute getParametersAnnotation() {
        return parameters;
    }

    @Override
    public String[] getAnnotationNames() {
        return new String[0];
    }

    @Override
    public TemplateContextAttribute getAnnotation(String name) {
        return null;
    }

    @Override
    public Map<String, TemplateContextAttribute> annotations() {
        return annotations;
    }

    @Override
    public Map<String, TemplateContextAttribute> evaluations() {
        return evaluations;
    }

    @Override
    public String toString() {
        TextBuilder textBuilder = TextBuilder.builder().setIndent(2);
        return textBuilder.toString();
    }

    public static DefaultTemplateContext newInstance(String id) {
        return new DefaultTemplateContext("");
    }

    private class Visitor extends AbstractVisitor {
        @Override
        public void visit(CustomBlock customBlock) {
            if (customBlock instanceof YamlFrontMatterBlock metadataBlock) {
                metadata = new LinkedHashMap<>();
                Node node = customBlock.getFirstChild();
                while (node != null) {
                    if (node instanceof YamlFrontMatterNode yn) {
                        if (!yn.getValues().isEmpty()) {
                            metadata.put(yn.getKey(), yn.getValues());
                        }
                    }
                    node = node.getNext();
                }
            }
        }

        @Override
        public void visit(Heading heading) {
            if (heading.getLevel() == 1) {
                if (id == null) {
                    Node node = heading.getFirstChild();
                    if (node instanceof Text txt) {
                        id = txt.getLiteral();
                    }
                }
            }
        }

        @Override
        public void visit(FencedCodeBlock fencedCodeBlock) {
            String format = null;
            String name = null;
            String payload = fencedCodeBlock.getLiteral();

            String info = fencedCodeBlock.getInfo().trim();
            int index = info.indexOf(" ");
            if (index > 0) {
                format = info.substring(0, index).trim();
                String namePart = info.substring(index).trim();
                if (namePart.contains("{") && namePart.endsWith("}")) {
                    int start = namePart.indexOf("{");
                    int end = namePart.lastIndexOf("}");
                    name = namePart.substring(0, start);
                    String[] arr = namePart.substring(start + 1, end).split(",");

                } else {
                    name = namePart;
                }
            }

            if (INIT_BLOCK_NAME.equalsIgnoreCase(name)) {
                initAnnotation = new TemplateContextAttribute(name, format, "Reqirement");
                initAnnotation.setPayload(payload);
                if ("yaml".equalsIgnoreCase(format)) {
                    initAnnotation.setValue(new Yaml().load(payload));
                }
            } else {

            }
        }
    }
}
