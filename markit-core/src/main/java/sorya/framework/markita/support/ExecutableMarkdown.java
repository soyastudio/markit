package sorya.framework.markita.support;

import org.commonmark.ext.front.matter.YamlFrontMatterBlock;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.front.matter.YamlFrontMatterNode;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import sorya.framework.markita.util.TextBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class ExecutableMarkdown<T extends ExecutableMarkdown.Metadata> extends AbstractVisitor {

    protected T metadata;

    protected TemplateMarkdownNode root;

    protected ExecutableMarkdown(String markdown) {
        this.metadata = metadata();
        this.root = new TemplateMarkdownNode(TemplateMarkdownNode.ROOT);

        Parser parser = Parser.builder()
                .extensions(Collections.singletonList(YamlFrontMatterExtension.create()))
                .build();
        Node document = parser.parse(markdown);
        document.accept(this);

        configure();
    }

    protected T metadata() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parameterized = (ParameterizedType) superclass;
            Class<T> genericType = (Class<T>) parameterized.getActualTypeArguments()[0];
            try {
                return genericType.getDeclaredConstructor().newInstance();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Class is not parameterized");
        }
    }

    // init markdown structure based on metadata:
    protected abstract void init();

    // create template components based on metadata and template nodes
    protected abstract void configure();

    public String getName() {
        return metadata.name;
    }

    public TemplateMarkdownNode getRootMarkdownNode() {
        return root;
    }

    public String toString() {
        TextBuilder builder = TextBuilder.builder(metadata.toString()).setIndent(2);
        root.toMarkdown(builder);
        return builder.toString();
    }

    @Override
    public void visit(CustomBlock customBlock) {
        if (customBlock instanceof YamlFrontMatterBlock) {
            Map<String, List<String>> data = new LinkedHashMap<>();
            Node node = customBlock.getFirstChild();
            while (node != null) {
                if (node instanceof YamlFrontMatterNode yn) {
                    if (!yn.getValues().isEmpty()) {
                        data.put(yn.getKey(), yn.getValues());
                    }
                }
                node = node.getNext();
            }

            metadata.configure(data);
            init();
        }
    }

    @Override
    public void visit(Heading heading) {
        if (heading.getLevel() == 1 && root != null) {
            // Top/Root Level:
            if(heading.getFirstChild() instanceof  Text txt) {
                root.setTitle(txt.getLiteral());
            } else if (heading.getFirstChild() instanceof Link link && link.getFirstChild() instanceof Text txt) {
                root.setTitle(txt.getLiteral());
            }

            describe(heading, root);

        } else if (heading.getLevel() == 2 && heading.getFirstChild() instanceof Link link) {
            // Domain/Group Level:
            TemplateMarkdownNode tn = root.findByPath(link.getDestination());
            if (link.getFirstChild() instanceof Text txt) {
                tn.setTitle(txt.getLiteral());
            }

            describe(heading, tn);

        } else if (heading.getLevel() == 3 && heading.getFirstChild() instanceof Link link) {
            // Component/Template Level:
            TemplateMarkdownNode tn = root.findByPath(link.getDestination());
            if (link.getFirstChild() instanceof Text txt) {
                tn.setTitle(txt.getLiteral());
            }

            describe(heading, tn);
        }
    }

    protected void describe(Heading heading, TemplateMarkdownNode tn) {
        Node node = heading.getNext();
        while (node != null) {
            if (node instanceof Heading h2 && h2.getLevel() <= heading.getLevel() + 1) {
                break;
            }

            if(heading.getLevel() == 3 && node instanceof FencedCodeBlock fencedCodeBlock) {
                processFencedBlock(tn, fencedCodeBlock);

            } else {
                process(tn, node);
            }

            node = node.getNext();
        }
    }

    protected void process(TemplateMarkdownNode tn, Node nd) {
        if(nd instanceof HtmlBlock block) {
            tn.getDescriptions().add(block.getLiteral());
        }
    }

    protected abstract void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock);

    public static abstract class Metadata {

        protected String name;
        protected String owner;
        protected String version = "0.0.1";

        protected List<String> description;
        protected List<String> tags;
        protected List<String> metadata;

        protected void configure(Map<String, List<String>> data) {
            if (!data.containsKey("name")) {
                throw new IllegalArgumentException("Field is required in metadata: 'name'");
            } else {
                this.name = data.get("name").get(0);
            }

            if (!data.containsKey("owner")) {
                throw new IllegalArgumentException("Field is required in metadata: 'owner'");
            } else {
                this.owner = data.get("owner").get(0);
            }

            if (data.containsKey(version)) {
                this.version = data.get("version").get(0);
            }

            this.description = data.containsKey("description") ? data.get("description") : List.of();
            this.tags = data.containsKey("tags") ? data.get("tags") : List.of();
            this.metadata = data.containsKey("metadata") ? data.get("metadata") : List.of();

            Arrays.stream(getDeclaredFieldNames()).forEach(e -> {
                List<String> value = data.get(e);
                try {
                    Field field = getClass().getDeclaredField(e);
                    field.setAccessible(true);
                    if (field.getType().isAssignableFrom(List.class)) {
                        field.set(this, value == null ? List.of() : value);
                    } else if (field.getType().isArray() && field.getType().getComponentType().isAssignableFrom(String.class)) {
                        field.set(this, value == null ? new String[0] : value.toArray(new String[0]));

                    } else if (field.getType().isAssignableFrom(String.class)) {
                        if (value != null && value.size() == 1) {
                            field.set(this, value.get(0));
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            });

        }

        protected abstract String[] getDeclaredFieldNames();

        public String toString() {
            StringBuilder builder = new StringBuilder();
            appendLine("---", builder);
            appendLine("name: " + name, builder);
            appendLine("owner: " + owner, builder);
            appendLine("version: " + version, builder);
            appendLine("description:", builder);
            if (description != null) {
                description.forEach(e -> {
                    appendLine("  - " + e, builder);
                });
            }
            appendLine("tags:", builder);
            if (tags != null) {
                tags.forEach(e -> {
                    appendLine("  - " + e, builder);
                });
            }

            Arrays.stream(getDeclaredFieldNames()).forEach(e -> {
                try {
                    Field field = getClass().getDeclaredField(e);
                    field.setAccessible(true);
                    Object value = field.get(this);

                    if (value == null) {
                        appendLine(e + ": ", builder);
                    } else {
                        if (field.getType().isAssignableFrom(List.class)) {
                            appendLine(e + ": ", builder);
                            List<String> list = (List<String>) value;
                            list.forEach(v -> {
                                appendLine("  - " + v, builder);
                            });

                        } else if (field.getType().isArray() && field.getType().getComponentType().isAssignableFrom(String.class)) {
                            appendLine(e + ": ", builder);
                            String[] arr = (String[]) value;
                            Arrays.stream(arr).forEach(v -> {
                                appendLine("  - " + v, builder);
                            });

                        } else if (field.getType().isAssignableFrom(String.class)) {
                            appendLine(e + ": " + value, builder);
                        }

                    }
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            });

            appendLine("metadata:", builder);
            if (metadata != null) {
                metadata.forEach(e -> {
                    appendLine("  - " + e, builder);
                });
            }
            appendLine("---", builder);
            return builder.toString();
        }

        private void appendLine(String st, StringBuilder builder) {
            builder.append(st).append("\n");
        }
    }
}
