package soya.framework.markit.workshop.service;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.FencedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorya.framework.markita.TemplateExecutor;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionInvoker;
import sorya.framework.markita.support.*;
import sorya.framework.markita.templates.HandlebarsExecutor;
import sorya.framework.markita.util.DataConvertUtils;
import sorya.framework.markita.util.TextBuilder;
import soya.framework.markit.workshop.configuration.Workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

@Service
public class FunctionService {
    private static final Logger logger = LoggerFactory.getLogger(FunctionService.class);

    @Autowired
    private TemplateFunctionInvoker templateFunctionInvoker;

    private final File functionDir;
    private final Map<String, TemplatePackageMarkdown> pkgs = new HashMap<>();

    public FunctionService(Workspace workspace) {
        this.functionDir = workspace.getFunctionDir();
        Path path = functionDir.toPath();
        try (Stream<Path> stream = Files.walk(path)) {
            stream.filter(Files::isRegularFile) // Finds only files, not directories
                    .forEach(e -> {
                        try {
                            String markdown = Files.readString(e);
                            TemplatePackageMarkdown functionPackage = new TemplatePackageMarkdown(markdown);
                            if (functionPackage.getName() != null) {
                                pkgs.put(functionPackage.getName(), functionPackage);
                            }

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // =========== methods for raw template:
    public String createTemplateUsingAi(String prompt) {
        return "TODO: create template using ai";
    }

    public String createTemplateVerificationTemplate(String templateFormat, String template) {
        StringBuilder builder = new StringBuilder("# Template Verification\n");
        builder.append("\n");
        builder.append("### Template\n");
        builder.append("```").append(templateFormat).append(" TEMPLATE\n");
        builder.append(template);
        builder.append("\n");
        builder.append("```\n");

        builder.append("\n");
        builder.append("### Input Data\n");
        builder.append("```json INPUT\n");
        builder.append(" ");
        builder.append("\n");
        builder.append("```\n");

        return builder.toString();
    }

    public String verifyTemplate(String markdown) {
        logger.info("verifyTemplate...");

        final TemplateElement[] elements = new TemplateElement[2];
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        document.accept(new AbstractVisitor() {
            @Override
            public void visit(FencedCodeBlock fencedCodeBlock) {
                TemplateElement templateElement = TemplateElement.fromMarkdown(fencedCodeBlock);
                if ("TEMPLATE".equalsIgnoreCase(templateElement.getName())) {
                    elements[0] = templateElement;
                } else if ("INPUT".equalsIgnoreCase(templateElement.getName())) {
                    elements[1] = templateElement;
                }
            }
        });

        if (elements[0] == null || elements[1] == null) {
            throw new IllegalArgumentException("Input format is not correct.");
        }

        DefaultTemplateFunction function = new DefaultTemplateFunction(null, null);
        function.setTemplateFormat(elements[0].getFormat());
        function.setTemplate(elements[0].getBody());

        return templateFunctionInvoker.invoke(function, DataConvertUtils.toMap(elements[1].getFormat(), elements[1].getBody())).toString();

    }

    public String create(String fullName, String templateFormate, String template) {
        logger.info("createTemplateFunction...");
        FunctionName name = new FunctionName(fullName);
        TemplatePackageMarkdown pkg = TemplatePackageMarkdown.newInstance(name.getPackageName());
        pkg.addFunction(name.getFunctionName());

        TemplateMarkdownNode node = pkg.getFunctionNode(name.getFunctionName());
        node.setTitle(name.getFunctionName());
        FunctionDefinition definition = (FunctionDefinition) pkg.getFunctionNode(name.getFunctionName()).getComponentDefinition();
        definition.setTemplateElement(new TemplateElement(templateFormate, template));

        logger.warn("TODO: add logic for generate schema from template!!!");

        return pkg.toString();
    }

    // =========== methods for function packages:
    public List<String> packageNames() {
        List<String> list = new ArrayList<>(pkgs.keySet());
        Collections.sort(list);
        return list;
    }

    public String get(String packageName) {
        if (pkgs.containsKey(packageName)) {
            return pkgs.get(packageName).toString();
        }

        return null;
    }

    public String build(String packageName) throws IOException {
        File worksheet = new File(functionDir, packageFileName(packageName));

        String markdown;
        if (!worksheet.exists() && worksheet.createNewFile()) {
            markdown = TemplatePackageMarkdown.newInstance(packageName).toString();
        } else {
            markdown = Files.readString(worksheet.toPath());
            TemplatePackageMarkdown fp = new TemplatePackageMarkdown(markdown);
            if (fp.getName() != null && fp.getName().equals(packageName)) {
                markdown = fp.toString();
            } else {
                markdown = TemplatePackageMarkdown.newInstance(packageName).toString();
            }
        }

        Files.writeString(worksheet.toPath(), markdown);
        return markdown;
    }

    // =========== methods for functions:
    public String getFunction(String fullName) {
        FunctionName name = new FunctionName(fullName);
        TemplatePackageMarkdown fp = pkgs.get(name.getPackageName());

        TemplateMarkdownNode node = fp.getFunctionNode(name.getFunctionName());

        TextBuilder builder = TextBuilder.builder();
        node.toMarkdown(builder);
        return builder.toString();
    }

    public String verifyFunction(String fullName, String input) {
        return null;
    }

    public String createOrMerge(String markdown) throws IOException {
        String result = markdown;
        TemplatePackageMarkdown pkg = new TemplatePackageMarkdown(markdown);
        if (pkgs.containsKey(pkg.getName())) {
            TemplatePackageMarkdown functionPackage = pkgs.get(pkg.getName());
            functionPackage.merge(pkg);
            result = functionPackage.toString();
        } else {
            result = pkg.toString();
        }

        File worksheet = new File(functionDir, packageFileName(pkg.getName()));
        if (!worksheet.exists()) {
            worksheet.createNewFile();
        }

        Files.writeString(worksheet.toPath(), markdown);
        return result;
    }

    public String invoke(String fullName, String json) {
        int lastPoint = fullName.lastIndexOf(".");
        String packageNam = fullName.substring(0, lastPoint);
        String functionName = fullName.substring(lastPoint + 1);

        TemplatePackageMarkdown pkg = (TemplatePackageMarkdown) pkgs.get(packageNam);
        TemplateFunction function = pkg.get(functionName);

        return (String) templateFunctionInvoker.invoke(function, DataConvertUtils.toMap("json", json));

    }

    // =========== methods for chatbots
    public String generateFunction(String fullName, String templateFormat, String schemaFormat, String requirement) {
        System.out.println("============== todo: generateFunction");

        return "todo: generate using AI.";
    }

    public String generatePackage(String packageName, String templateFormat, String schemaFormat, Map<String, String> requirements) {
        System.out.println("============== todo: generatePackage");

        return "todo: generate packages using AI.";
    }

    // =========== Utility methods:
    private String packageFileName(String packageName) {
        return packageName.replace(".", "-") + ".md";
    }

    private String getSchema(String schemaFormat, String templateContent) {


        return "";
    }

    private String createPrompt(String templateFormat, String schemaFormat, String requirement) {
        return requirement;
    }
}
