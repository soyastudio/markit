package soya.framework.markit.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionInvoker;
import sorya.framework.markita.TemplateFunctionPackage;
import sorya.framework.markita.support.DefaultTemplateFunctionPackage;
import sorya.framework.markita.support.FunctionName;
import sorya.framework.markita.support.TemplateMarkdownNode;
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

    @Autowired
    private TemplateFunctionInvoker templateFunctionInvoker;

    private final File functionDir;
    private final Map<String, DefaultTemplateFunctionPackage> pkgs = new HashMap<>();

    public FunctionService(Workspace workspace) {
        this.functionDir = workspace.getFunctionDir();
        Path path = functionDir.toPath();
        try (Stream<Path> stream = Files.walk(path)) {
            stream.filter(Files::isRegularFile) // Finds only files, not directories
                    .forEach(e -> {
                        try {
                            String markdown = Files.readString(e);
                            DefaultTemplateFunctionPackage functionPackage = new DefaultTemplateFunctionPackage(markdown);
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

    private String packageFileName(String packageName) {
        return packageName.replace(".", "-") + ".md";
    }

    public String build(String packageName) throws IOException {
        File worksheet = new File(functionDir, packageFileName(packageName));

        String markdown;
        if (!worksheet.exists()) {
            worksheet.createNewFile();
            markdown = DefaultTemplateFunctionPackage.newInstance(packageName).toString();
        } else {
            markdown = Files.readString(worksheet.toPath());
            DefaultTemplateFunctionPackage fp = new DefaultTemplateFunctionPackage(markdown);
            if (fp.getName() != null && fp.getName().equals(packageName)) {
                markdown = fp.toString();
            } else {
                markdown = DefaultTemplateFunctionPackage.newInstance(packageName).toString();
            }
        }

        Files.writeString(worksheet.toPath(), markdown);
        return markdown;
    }

    // =========== methods for functions:
    public String getFunction(String fullName) {
        FunctionName name = new FunctionName(fullName);
        DefaultTemplateFunctionPackage fp = pkgs.get(name.getPackageName());
        TemplateMarkdownNode node = fp.getRootMarkdownNode().getChildren().get(name.getFunctionName());
        TextBuilder builder = TextBuilder.builder();
        node.toMarkdown(builder);

        return builder.toString();
    }

    public String create(String fullName, String templateFormate, String schemaFormat, String template) {
        FunctionName name = new FunctionName(fullName);
        DefaultTemplateFunctionPackage pkg = DefaultTemplateFunctionPackage.newInstance(name.getPackageName());
        pkg.addFunction(name.getFunctionName());

        return pkg.toString();
    }

    public String saveOrUpdate(String markdown) {
        String result = markdown;
        DefaultTemplateFunctionPackage pkg = new DefaultTemplateFunctionPackage(markdown);
        if (pkgs.containsKey(pkg.getName())) {
            DefaultTemplateFunctionPackage functionPackage = pkgs.get(pkg.getName());
            functionPackage.merge(pkg);
            result = functionPackage.toString();
        } else {

        }

        return result;
    }

    public String invoke(String fullName, String json) {
        int lastPoint = fullName.lastIndexOf(".");
        String packageNam = fullName.substring(0, lastPoint);
        String functionName = fullName.substring(lastPoint + 1);

        DefaultTemplateFunctionPackage pkg = (DefaultTemplateFunctionPackage) pkgs.get(packageNam);
        TemplateFunction function = pkg.get(functionName);

        return (String) templateFunctionInvoker.invoke(function, json);

    }

    public String generate(String fullName, String templateFormat, String schemaFormat, String requirement) {
        System.out.println("============== template format: " + templateFormat);
        System.out.println("============== schema format: " + schemaFormat);

        return "generate using AI.";
    }
}
