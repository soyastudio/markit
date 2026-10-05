package sorya.framework.markita.support;

import sorya.framework.markita.util.TextBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TemplateMarkdownNode {
    public static final String ROOT = ".";

    private final String name;
    private String title;
    private final List<String> descriptions = new ArrayList<>();

    private TemplateBlock templateBlock;

    private TemplateMarkdownNode root;
    private TemplateMarkdownNode parent;
    private final Map<String, TemplateMarkdownNode> children = new LinkedHashMap<>();
    private int level = 1;
    private String path;

    public TemplateMarkdownNode(String name) {
        this.name = name;
        this.path = name;
        this.root = this;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public TemplateBlock getTemplateBlock() {
        return templateBlock;
    }

    public void setTemplateBlock(TemplateBlock templateBlock) {
        this.templateBlock = templateBlock;
    }

    public TemplateMarkdownNode getRoot() {
        return root;
    }

    public TemplateMarkdownNode getParent() {
        return parent;
    }

    public Map<String, TemplateMarkdownNode> getChildren() {
        return children;
    }

    public int getLevel() {
        return level;
    }

    public String getPath() {
        return path;
    }

    public TemplateMarkdownNode findByPath(String path) {
        if (path.equals(this.path)) {
            return this;
        }

        String[] arr = path.split("/");
        TemplateMarkdownNode node = null;
        for (String name : arr) {
            if (ROOT.equals(name)) {
                node = getRoot();
            } else if (node != null) {
                node = node.getChildren().get(name);
            }

            if (node == null) {
                return null;
            }
        }

        return node;
    }

    public void addChild(TemplateMarkdownNode child) {
        if (!children.containsKey(child.getName())) {
            child.parent = this;
            child.root = getRoot();
            child.level = this.level + 1;
            child.path = path + "/" + child.getName();
            children.put(child.getName(), child);
        }
    }

    public void toMarkdown(TextBuilder builder) {

        builder.newLineWithCurrentIndents("#".repeat(level)).append(" ");
        if (parent == null) {
            builder.append(getTitle());
        } else {
            builder.append("[").append(getTitle()).append("](#").append(getPath()).append(")");
        }

        descriptions.forEach(e -> {
            builder.newLineWithCurrentIndents(e).newLine();
        });

        if(templateBlock != null) {
            builder.newLineWithCurrentIndents("Template");
            builder.newLineWithCurrentIndents("```").append(templateBlock.getTemplateFormat());
            builder.newLineWithCurrentIndents(templateBlock.getTemplate());
            builder.newLineWithCurrentIndents("```");
            builder.newLine();
            builder.newLineWithCurrentIndents("Input Schema");
            builder.newLineWithCurrentIndents(SchemaFormat.INPUT_FENCE_CHAR.repeat(3)).append(templateBlock.getInputSchemaFormat());
            builder.newLineWithCurrentIndents(templateBlock.getInputSchema());
            builder.newLineWithCurrentIndents(SchemaFormat.INPUT_FENCE_CHAR.repeat(3));
            builder.newLine();
            builder.newLineWithCurrentIndents("Output Schema");
            builder.newLineWithCurrentIndents(SchemaFormat.OUTPUT_FENCE_CHAR.repeat(3)).append(templateBlock.getOutputSchemaFormat());
            builder.newLineWithCurrentIndents(templateBlock.getOutputSchema());
            builder.newLineWithCurrentIndents(SchemaFormat.OUTPUT_FENCE_CHAR.repeat(3));
        }

        builder.newLine();
        children.forEach((k, v) -> {
            v.toMarkdown(builder);
        });

    }

}
