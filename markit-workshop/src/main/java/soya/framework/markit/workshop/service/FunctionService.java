package soya.framework.markit.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorya.framework.markita.Template;
import sorya.framework.markita.TemplateEngine;
import sorya.framework.markita.support.DefaultTemplate;
import soya.framework.markit.workshop.configuration.Workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class FunctionService {
    @Autowired
    private TemplateEngine templateEngine;

    private Map<String, String> functions = new HashMap<>();

    public FunctionService(Workspace workspace) {
        Path path = workspace.getFunctionDir().toPath();
        try (Stream<Path> stream = Files.walk(path)) {
            stream.filter(Files::isRegularFile) // Finds only files, not directories
                    .forEach(e -> {
                        try {
                            String markdown = Files.readString(e);

                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
