package sorya.framework.markita.support;

import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.DefaultTemplateExecutor;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionPackage;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TemplatePackageMarkdown extends ExecutableMarkdown<TemplatePackageMarkdown.PackageMetadata> implements TemplateFunctionPackage {

    private static final String EMPTY_PACKAGE_TEMPLATE;
    public static final String FUNCTIONS = "FUNCTIONS";

    private Map<String, TemplateFunction> functions;

    static {
        try {
            try (InputStream inputStream = TemplatePackageMarkdown.class.getClassLoader().getResourceAsStream("META-INF/templates/template-function-package.md")) {
                if (inputStream != null) {
                    EMPTY_PACKAGE_TEMPLATE = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } else {
                    throw new ExceptionInInitializerError("Cannot find template-function-package!");
                }
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public TemplatePackageMarkdown(String markdown) {
        super(markdown);
    }

    public static TemplatePackageMarkdown newInstance(String name) {
        String markdown = new DefaultTemplateExecutor().execute(EMPTY_PACKAGE_TEMPLATE, Map.of("name", name));
        return new TemplatePackageMarkdown(markdown);
    }

    @Override
    public TemplateFunction[] getFunctions() {
        return functions.values().toArray(new TemplateFunction[0]);
    }

    public TemplateFunction get(String functionName) {
        return functions.get(functionName);
    }

    public TemplateMarkdownNode getFunctionNode(String functionName) {
        return root.getChildren().get(FUNCTIONS).getChildren().get(functionName);
    }

    public void addFunction(String name) {
        if (functions.containsKey(name)) {
            throw new IllegalArgumentException("Function already exists: " + name);
        }
        metadata.functions.add(name);

        root.getChildren().get(FUNCTIONS).addChild(new TemplateMarkdownNode(name, new FunctionDefinition(name, null)));
    }

    public void merge(TemplateFunctionPackage functionPackage) {
        if (functionPackage instanceof TemplatePackageMarkdown packageMarkdown) {
            this.metadata.functions.addAll(packageMarkdown.metadata.functions);

            TemplateMarkdownNode to = root.getChildren().get(FUNCTIONS);
            TemplateMarkdownNode from = packageMarkdown.root.getChildren().get(FUNCTIONS);
            from.getChildren().values().forEach(to::addChild);
        }

    }

    @Override
    protected void init() {
        TemplateMarkdownNode functions = new TemplateMarkdownNode(FUNCTIONS);
        functions.setTitle("TEMPLATE " + FUNCTIONS);
        root.addChild(functions);

        metadata.functions.forEach(e -> {
            if (!e.startsWith("[")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                TemplateMarkdownNode node = new TemplateMarkdownNode(descriptor.getName(), new FunctionDefinition(descriptor.getName(), descriptor.getProperties()));
                functions.addChild(node);
            }
        });
    }

    @Override
    protected void configure() {
        this.functions = new LinkedHashMap<>();
        TemplateMarkdownNode parent = root.getRoot().getChildren().get(FUNCTIONS);
        metadata.functions.forEach(e -> {
            if (!e.startsWith("[")) {
                TemplateComponentDescriptor descriptor = new TemplateComponentDescriptor(e);
                String name = descriptor.getName();

                TemplateMarkdownNode node = parent.getChild(name);
                if (node != null && node.getComponentDefinition() != null && node.getComponentDefinition() instanceof FunctionDefinition definition) {
                    functions.put(name, definition.create());

                } else {
                    throw new IllegalArgumentException("Function is not defined correctly: " + name);
                }
            }
        });
    }

    @Override
    protected void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock) {
        FunctionDefinition definition = (FunctionDefinition) tn.getComponentDefinition();
        if (definition == null) {
            definition = new FunctionDefinition(tn.getName(), null);
            tn.setComponentDefinition(definition);
        }

        TemplateElement templateElement = TemplateElement.fromMarkdown(fencedCodeBlock);

        if (TemplateComponentDefinition.SCHEMA_TOKEN.equalsIgnoreCase(templateElement.getName())) {
            definition.setSchemaElement(templateElement);

        } else if (TemplateComponentDefinition.TEMPLATE_TOKEN.equalsIgnoreCase(templateElement.getName())) {
            definition.setTemplateElement(templateElement);

        }

    }

    static class PackageMetadata extends Metadata {

        List<String> functions = List.of();

        @Override
        protected String[] getDeclaredFieldNames() {
            return new String[]{"functions"};
        }
    }
}
