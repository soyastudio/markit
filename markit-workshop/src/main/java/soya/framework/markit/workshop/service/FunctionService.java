package soya.framework.markit.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorya.framework.markita.TemplateEngine;
import sorya.framework.markita.TemplateFunction;
import sorya.framework.markita.TemplateFunctionInvoker;
import sorya.framework.markita.TemplateFunctionPackage;
import sorya.framework.markita.support.DefaultTemplateFunctionPackage;
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

    private Map<String, TemplateFunctionPackage> pkgs = new HashMap<>();

    public FunctionService(Workspace workspace) {
        this.functionDir = workspace.getFunctionDir();
        Path path = functionDir.toPath();
        try (Stream<Path> stream = Files.walk(path)) {
            stream.filter(Files::isRegularFile) // Finds only files, not directories
                    .forEach(e -> {
                        try {
                            String markdown = Files.readString(e);
                            TemplateFunctionPackage functionPackage = new DefaultTemplateFunctionPackage(markdown);
                            pkgs.put(functionPackage.getName(), functionPackage);

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> packageNames() {
        List<String> list = new ArrayList<>(pkgs.keySet());
        Collections.sort(list);
        return list;
    }

    public String get(String packageName) {
        if(pkgs.containsKey(packageName)) {
            return pkgs.get(packageName).toString();
        }

        return null;
    }

    public String build(String packageName) throws IOException{
        String templateName = packageName.replace(".", "-");
        File worksheet = new File(functionDir, templateName + ".md");

        String markdown;
        if(!worksheet.exists()) {
            worksheet.createNewFile();
            markdown = DefaultTemplateFunctionPackage.newInstance(packageName).toString();
        } else {
            markdown = Files.readString(worksheet.toPath());
            markdown = new DefaultTemplateFunctionPackage(markdown).toString();
        }

        Files.writeString(worksheet.toPath(), markdown);
        return markdown;
    }

    public String execute(String fullName, Map<String, Object> input) {
        int lastPoint = fullName.lastIndexOf(".");
        String packageNam = fullName.substring(0, lastPoint);
        String functionName = fullName.substring(lastPoint + 1);

        DefaultTemplateFunctionPackage pkg = (DefaultTemplateFunctionPackage) pkgs.get(packageNam);
        TemplateFunction function = pkg.get(functionName);

        return templateFunctionInvoker.invoke(function, input);

    }
}
