package sorya.framework.markita.support;

import org.commonmark.ext.front.matter.YamlFrontMatterBlock;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterNode;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionPackage;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DefaultTemplateFunctionPackage implements TemplateFunctionPackage {

    private final String markdown;
    private final TemplateMetadata metadata = new TemplateMetadata();
    private TemplateMarkdownNode root = null;

    private final Map<String, TemplateFunction> functions = new LinkedHashMap<>();

    public DefaultTemplateFunctionPackage(String markdown) {
        this.markdown = markdown;
        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                .build();
        Node document = parser.parse(markdown);
        document.accept(new Visitor());

        Arrays.stream(metadata.getFunctions()).forEach(e -> {
            String name = e.contains("?") ? e.substring(0, e.indexOf("?")) : e;
            TemplateBlock block = root.getChildren().get(name).getTemplateBlock();
            TemplateFunction function = new DefaultTemplateFunction(name,
                    block.getTemplateFormat(),
                    block.getTemplate(),
                    DefaultTemplateSchema.create(block.getInputSchemaFormat(), block.getInputSchema()));
            functions.put(name, function);
        });

    }

    @Override
    public String getName() {
        return metadata.getName();
    }

    @Override
    public TemplateFunction[] getFunctions() {
        return functions.values().toArray(new TemplateFunction[0]);
    }

    @Override
    public String toString() {
        return markdown;
    }

    private class Visitor extends AbstractVisitor {

        @Override
        public void visit(CustomBlock customBlock) {
            if (customBlock instanceof YamlFrontMatterBlock) {
                Node node = customBlock.getFirstChild();
                while (node != null) {
                    if (node instanceof YamlFrontMatterNode yn) {
                        if (!yn.getValues().isEmpty()) {
                            try {
                                Field field = TemplateMetadata.class.getDeclaredField(yn.getKey());
                                field.setAccessible(true);
                                if (field.getType().isArray()) {
                                    field.set(metadata, yn.getValues().toArray(new String[0]));
                                } else {
                                    field.set(metadata, yn.getValues().get(0));
                                }
                            } catch (Exception e) {
                                if (yn.getValues().size() == 1) {
                                    metadata.getMetadata().put(yn.getKey(), yn.getValues().get(0));
                                } else {
                                    metadata.getMetadata().put(yn.getKey(), yn.getValues().toArray(new String[0]));
                                }
                            }
                        }
                    }
                    node = node.getNext();
                }

                Arrays.stream(metadata.getFunctions()).forEach(e -> {
                    if (!e.startsWith("[")) {
                        String name = e.contains("?") ? e.substring(0, e.indexOf("?")) : e.trim();
                        TemplateMarkdownNode function = new TemplateMarkdownNode(name);
                        function.setTitle(name);
                        function.setTemplateBlock(new TemplateBlock());
                        root.addChild(function);
                    }
                });
            }

        }

        @Override
        public void visit(Heading heading) {
            if (heading.getLevel() == 1 && root == null) {
                root = new TemplateMarkdownNode(TemplateMarkdownNode.ROOT);
                if (heading.getFirstChild() instanceof Text txt) {
                    root.setTitle(txt.getLiteral());
                } else {
                    root.setTitle(metadata.getName());
                }

                load(heading, root);

            } else if (heading.getLevel() == 2 && heading.getFirstChild() instanceof Text text) {

                String functionName = text.getLiteral();
                TemplateMarkdownNode child = root.getChildren().get(functionName);
                load(heading, child);
            }
        }


        private void load(Heading heading, TemplateMarkdownNode node) {
            Node sibling = heading.getNext();
            while (sibling != null) {
                if (sibling instanceof Heading h2 && h2.getLevel() <= 2) {
                    // navigate to next builder:
                    break;
                }

                if (sibling instanceof FencedCodeBlock fencedCodeBlock) {
                    TemplateBlock block = node.getTemplateBlock();
                    if (block == null) {
                        block = new TemplateBlock();
                        node.setTemplateBlock(block);
                    }

                    String payload = fencedCodeBlock.getLiteral().trim();
                    String format = fencedCodeBlock.getInfo().trim();
                    if (SchemaFormat.isSchema(format)) {
                        if (SchemaFormat.INPUT_FENCE_CHAR.endsWith(fencedCodeBlock.getFenceCharacter())) {
                            block.setInputSchemaFormat(format);
                            block.setInputSchema(payload);
                        } else if (SchemaFormat.OUTPUT_FENCE_CHAR.equals(fencedCodeBlock.getFenceCharacter())) {
                            block.setOutputSchemaFormat(format);
                            block.setOutputSchema(payload);
                        }
                    } else {
                        block.setTemplateFormat(format);
                        block.setTemplate(payload);
                    }

                } else if (sibling instanceof HtmlBlock htmlBlock) {
                    node.getDescriptions().add(htmlBlock.getLiteral().trim());
                }

                sibling = sibling.getNext();
            }
        }
    }


}
