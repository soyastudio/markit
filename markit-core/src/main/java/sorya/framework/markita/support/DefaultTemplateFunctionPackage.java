package sorya.framework.markita.support;

import org.commonmark.ext.front.matter.YamlFrontMatterBlock;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterNode;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import sorya.framework.markita.DefaultTemplateExecutor;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionPackage;
import sorya.framework.markita.util.TextBuilder;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DefaultTemplateFunctionPackage implements TemplateFunctionPackage {
    private static final String TEMPLATE;

    private final TemplateMetadata metadata = new TemplateMetadata();
    private TemplateMarkdownNode root = null;

    private final Map<String, TemplateFunction> functions = new LinkedHashMap<>();

    static {
        try {
            try (InputStream inputStream = DefaultTemplateFunctionPackage.class.getClassLoader().getResourceAsStream("META-INF/templates/template-function-package.md")) {
                if (inputStream != null) {
                    TEMPLATE = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } else {
                    throw new ExceptionInInitializerError("Cannot find template-function-package!");
                }
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public DefaultTemplateFunctionPackage(String markdown) {
        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                .build();
        Node document = parser.parse(markdown);
        document.accept(new Visitor());

        if (metadata.getName() != null) {
            Arrays.stream(metadata.getFunctions()).forEach(e -> {
                if (!e.trim().startsWith("[")) {
                    String name = e.contains("?") ? e.substring(0, e.indexOf("?")) : e;
                    TemplateBlock block = root.getChildren().get(name).getTemplateBlock();
                    TemplateFunction function = new DefaultTemplateFunction(name,
                            block.getTemplateFormat(),
                            block.getTemplate(),
                            DefaultTemplateSchema.create(block.getInputSchemaFormat(), block.getInputSchema()));
                    functions.put(name, function);

                }
            });
        }
    }

    @Override
    public String getName() {
        return metadata.getName();
    }

    @Override
    public TemplateFunction[] getFunctions() {
        return functions.values().toArray(new TemplateFunction[0]);
    }

    public TemplateMarkdownNode getRootMarkdownNode() {
        return root;
    }

    public TemplateFunction get(String functionName) {
        return functions.get(functionName);
    }

    public void addFunction(String name) {
        List<String> list = new ArrayList<>();
        Arrays.stream(metadata.getFunctions()).forEach(e -> {
            if(!e.startsWith("[")) {
                list.add(e);
            }
        });
        list.add(name);
        metadata.setFunctions(list.toArray(new String[0]));

        TemplateMarkdownNode function = new TemplateMarkdownNode(name);
        function.setTitle(name);
        function.setTemplateBlock(new TemplateBlock());

        root.addChild(function);
    }

    public void merge(DefaultTemplateFunctionPackage functionPackage) {
        List<String> list = new ArrayList<>();
        Arrays.stream(metadata.getFunctions()).forEach(e -> {
            if(!e.startsWith("[")) {
                list.add(e);
            }
        });

        Arrays.stream(functionPackage.metadata.getFunctions()).forEach(e -> {
            if(!e.startsWith("[")) {
                list.add(e);
            }
        });

        metadata.setFunctions(list.toArray(new String[0]));

        functionPackage.root.getChildren().forEach((k, v) -> {
            root.addChild(v);
        });

    }

    @Override
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

        builder.newLineWithCurrentIndents("---");

        root.toMarkdown(builder);

        return builder.toString();

    }

    public static DefaultTemplateFunctionPackage newInstance(String name) {
        String markdown = new DefaultTemplateExecutor().execute(TEMPLATE, Map.of("name", name));
        return new DefaultTemplateFunctionPackage(markdown);
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

                if (metadata.getName() != null) {
                    root = new TemplateMarkdownNode(TemplateMarkdownNode.ROOT);
                    root.setTitle("Template Function Package: " + metadata.getName());
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
        }


        @Override
        public void visit(Heading heading) {
            if (heading.getLevel() == 1 && root != null) {
                if (heading.getFirstChild() instanceof Text txt) {
                    root.setTitle(txt.getLiteral());
                }

                load(heading, root);

            } else if (heading.getLevel() == 2 && heading.getFirstChild() instanceof Link link) {
                String path = link.getDestination();
                if (path.startsWith("#")) {
                    path = path.substring(1);
                    TemplateMarkdownNode child = root.findByPath(path);
                    if (child != null) {
                        load(heading, child);
                    }
                }

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

                    if(SchemaFormat.OUTPUT_FENCE_CHAR.equals(fencedCodeBlock.getFenceCharacter())) {
                        if(format.toLowerCase().contains("output")) {
                            block.setSampleOutput(payload);
                            block.setSampleOutputFormat(fencedCodeBlock.getInfo().replace("output", "").trim());
                        } else {
                            block.setSampleInput(payload);
                            if(format.contains("input")) {
                                block.setSampleInputFormat(format.replace("input", "").trim());
                            } else {
                                block.setSampleInputFormat(format);
                            }
                        }

                    } else if (SchemaFormat.isSchema(format)) {
                        block.setInputSchemaFormat(format);
                        block.setInputSchema(payload);
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
