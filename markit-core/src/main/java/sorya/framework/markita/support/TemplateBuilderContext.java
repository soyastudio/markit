package sorya.framework.markita.support;

import org.commonmark.ext.front.matter.YamlFrontMatterBlock;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterNode;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import sorya.framework.markita.util.TextBuilder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class TemplateBuilderContext {

    public static final String ROOT_TITLE = "Complex Template Design Worksheet";
    public static final String FUNCTIONS = "FUNCTIONS";
    public static final String ANNOTATORS = "ANNOTATORS";
    public static final String EVALUATORS = "EVALUATORS";
    public static final String RENDERERS = "RENDERERS";

    public static final String EMPTY_TEMPLATE;
    public static final TemplateBlock TEMPLATE_BLOCK_TEMPLATE;

    private final TemplateMetadata metadata = new TemplateMetadata();
    private TemplateMarkdownNode root = null;

    private Map<String, TemplateMarkdownNode> indexMap;

    static {
        try {
            try (InputStream inputStream = DefaultTemplateBuilder.class.getClassLoader().getResourceAsStream("META-INF/templates/empty-template.md")) {
                if (inputStream != null) {
                    EMPTY_TEMPLATE = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } else {
                    throw new ExceptionInInitializerError("Cannot find empty-template!");
                }
            }

            try (InputStream inputStream = DefaultTemplateBuilder.class.getClassLoader().getResourceAsStream("META-INF/templates/template-builder-template.md")) {
                if (inputStream != null) {
                    TEMPLATE_BLOCK_TEMPLATE = new TemplateBlock();
                    String md = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    Parser parser = Parser.builder()
                            .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                            .build();
                    Node document = parser.parse(md);
                    document.accept(new AbstractVisitor() {
                        @Override
                        public void visit(FencedCodeBlock fencedCodeBlock) {
                            String format = fencedCodeBlock.getInfo();
                            String payload = fencedCodeBlock.getLiteral();
                            if (!SchemaFormat.isSchema(format)) {
                                TEMPLATE_BLOCK_TEMPLATE.setTemplateFormat(format);
                                TEMPLATE_BLOCK_TEMPLATE.setTemplate(payload);
                            } else if (SchemaFormat.INPUT_FENCE_CHAR.equals(fencedCodeBlock.getFenceCharacter())) {
                                TEMPLATE_BLOCK_TEMPLATE.setInputSchemaFormat(format);
                                TEMPLATE_BLOCK_TEMPLATE.setInputSchema(payload);
                            } else if ((SchemaFormat.OUTPUT_FENCE_CHAR.equals(fencedCodeBlock.getFenceCharacter()))) {
                                TEMPLATE_BLOCK_TEMPLATE.setOutputSchemaFormat(format);
                                TEMPLATE_BLOCK_TEMPLATE.setOutputSchema(payload);
                            }
                        }
                    });

                } else {
                    throw new ExceptionInInitializerError("Cannot find template-builder-schema-template!");
                }
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public TemplateBuilderContext(String markdown) {
        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                .build();
        Node document = parser.parse(markdown);
        document.accept(new Visitor());
    }

    private void init() {
        // Functions:
        Arrays.stream(metadata.getFunctions()).forEach(e -> {
            if (!isDesc(e)) {
                String name = e.contains("?") ? e.substring(0, e.indexOf("?")) : e.trim();
                TemplateMarkdownNode function = new TemplateMarkdownNode(name);
                function.setTitle(name);
                function.setTemplateBlock(newTemplateBlock());
                root.getChildren().get(FUNCTIONS).addChild(function);
            }

        });

        // Annotators:
        Arrays.stream(metadata.getAnnotators()).forEach(e -> {
            if (!e.startsWith("[") && !e.trim().endsWith("]")) {
                String name = e.contains("?") ? e.substring(0, e.indexOf("?")) : e.trim();
                createTemplateMarkdownNode(name, ANNOTATORS);
            }
        });

        // Renderers:
        Arrays.stream(metadata.getRenderers()).forEach(e -> {
            if (!e.startsWith("[") && !e.trim().endsWith("]")) {
                String rendererName = e.contains("?") ? e.substring(0, e.indexOf("?")) : e.trim();
                createTemplateMarkdownNode(rendererName, RENDERERS);
                createTemplateMarkdownNode(rendererName, EVALUATORS);
            }
        });

    }

    private void createTemplateMarkdownNode(String name, String container) {
        TemplateMarkdownNode nd = new TemplateMarkdownNode(name);
        nd.setTitle(name);
        nd.setTemplateBlock(newTemplateBlock());
        root.getChildren().get(container).addChild(nd);
    }

    private TemplateBlock newTemplateBlock() {
        TemplateBlock block = new TemplateBlock();
        block.setTemplateFormat(TEMPLATE_BLOCK_TEMPLATE.getTemplateFormat());
        block.setTemplate(TEMPLATE_BLOCK_TEMPLATE.getTemplate());

        block.setInputSchemaFormat(TEMPLATE_BLOCK_TEMPLATE.getInputSchemaFormat());
        block.setInputSchema(TEMPLATE_BLOCK_TEMPLATE.getInputSchema());

        block.setOutputSchemaFormat(TEMPLATE_BLOCK_TEMPLATE.getOutputSchemaFormat());
        block.setOutputSchema(TEMPLATE_BLOCK_TEMPLATE.getOutputSchema());
        return block;
    }

    private boolean isDesc(String value) {
        String uri = value.trim();
        return uri.startsWith("[") && uri.endsWith("]");
    }

    public String toString() {
        TextBuilder builder = TextBuilder.builder().setIndent(2).append("---");
        builder.newLineWithCurrentIndents("name: ").append(metadata.getName());
        builder.newLineWithCurrentIndents("owner: ").append(metadata.getOwner() == null ? "" : metadata.getOwner());

        builder.newLineWithCurrentIndents("tags:");
        builder.indentRight();
        Arrays.stream(metadata.getTags()).forEach(e -> {
            builder.newLineWithCurrentIndents("- ").append("\"").append(e).append("\"");
        });
        builder.indentLeft();

        builder.newLineWithCurrentIndents("functions:");
        builder.indentRight();
        Arrays.stream(metadata.getFunctions()).forEach(e -> {
            builder.newLineWithCurrentIndents("- ").append("\"").append(e).append("\"");
        });
        builder.indentLeft();

        builder.newLineWithCurrentIndents("renderers:");
        builder.indentRight();
        Arrays.stream(metadata.getRenderers()).forEach(e -> {
            builder.newLineWithCurrentIndents("- ").append("\"").append(e).append("\"");
        });
        builder.indentLeft();

        builder.newLineWithCurrentIndents("evaluators:");
        builder.indentRight();
        Arrays.stream(metadata.getEvaluators()).forEach(e -> {
            builder.newLineWithCurrentIndents("- ").append("\"").append(e).append("\"");
        });
        builder.indentLeft();

        builder.newLineWithCurrentIndents("annotators:");
        builder.indentRight();
        Arrays.stream(metadata.getAnnotators()).forEach(e -> {
            builder.newLineWithCurrentIndents("- ").append("\"").append(e).append("\"");
        });
        builder.indentLeft();

        builder.newLineWithCurrentIndents("---");

        root.toMarkdown(builder);

        return builder.toString();
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

            }
        }

        @Override
        public void visit(Heading heading) {

            if (heading.getLevel() == 1 && root == null) {
                root = new TemplateMarkdownNode(TemplateMarkdownNode.ROOT);
                if (heading.getFirstChild() instanceof Text txt) {
                    root.setTitle(txt.getLiteral());
                } else {
                    root.setTitle(ROOT_TITLE);
                }

                TemplateMarkdownNode functions = new TemplateMarkdownNode(FUNCTIONS);
                functions.setTitle("TEMPLATE " + FUNCTIONS);
                root.addChild(functions);

                TemplateMarkdownNode renderers = new TemplateMarkdownNode(RENDERERS);
                renderers.setTitle("TEMPLATE " + RENDERERS);
                root.addChild(renderers);

                TemplateMarkdownNode evaluators = new TemplateMarkdownNode(EVALUATORS);
                evaluators.setTitle("RENDERER " + EVALUATORS);
                root.addChild(evaluators);

                TemplateMarkdownNode annotators = new TemplateMarkdownNode(ANNOTATORS);
                annotators.setTitle("TEMPLATE " + ANNOTATORS);
                root.addChild(annotators);

                load(heading, root, 2);

                init();

            } else if (heading.getLevel() == 2) {
                if (heading.getFirstChild() != null && heading.getFirstChild() instanceof Link link) {
                    String dest = link.getDestination();
                    while (dest.startsWith("#")) {
                        dest = dest.substring(1);
                    }

                    TemplateMarkdownNode node = root.findByPath(dest);
                    load(heading, node, 3);
                }
            } else if (heading.getLevel() == 3 && heading.getFirstChild() instanceof Link link) {
                String dest = link.getDestination();
                while (dest.startsWith("#")) {
                    dest = dest.substring(1);
                }

                TemplateMarkdownNode node = root.findByPath(dest);
                load(heading, node, 3);

            }
        }

        private void load(Heading heading, TemplateMarkdownNode node, int level) {
            Node sibling = heading.getNext();
            while (sibling != null) {
                if (sibling instanceof Heading h2 && h2.getLevel() <= level) {
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

        @Override
        public void visit(OrderedList orderedList) {

        }
    }
}
