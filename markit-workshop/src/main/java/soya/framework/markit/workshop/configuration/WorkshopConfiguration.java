package soya.framework.markit.workshop.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sorya.framework.markita.*;
import sorya.framework.markita.support.*;
import soya.framework.markit.workshop.MarkitWorkshopApplication;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class WorkshopConfiguration {

    private File WORKSPACE_HOME;

    public WorkshopConfiguration() {
        String url = MarkitWorkshopApplication.class.getProtectionDomain().getCodeSource().getLocation().toString();
        if (url.indexOf("!") > 0) {
            url = url.substring(0, url.indexOf("!"));
        }

        if (url.startsWith("jar:")) {
            url = url.substring("jar:".length());
        }

        File file = Paths.get(URI.create(url)).toFile();
        WORKSPACE_HOME = new File(file.getParentFile().getParentFile().getParentFile(), "workspace");
        if (!WORKSPACE_HOME.exists()) {
            WORKSPACE_HOME.mkdir();
        }
    }

    @Bean
    public Workspace workspace() {
        return new Workspace() {

            @Override
            public File getHome() {
                return WORKSPACE_HOME;
            }

            @Override
            public File getFunctionDir() {
                File dir = new File(WORKSPACE_HOME, "functions");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                return dir;
            }

            @Override
            public File getPipelineDir() {
                File dir = new File(WORKSPACE_HOME, "pipelines");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                return dir;
            }

            @Override
            public File getProjectDir() {

                File dir = new File(WORKSPACE_HOME, "projects");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                return dir;
            }

            @Override
            public File getTemplateProjectDir() {

                File dir = new File(WORKSPACE_HOME, "template-projects");
                if (!dir.exists()) {
                    dir.mkdir();
                }
                return dir;
            }
        };
    }

    @Bean
    public TemplateFunctionInvoker templateFunctionInvoker() {
        return new DefaultTemplateFunctionInvoker();
    }

    @Bean
    public TemplatePipelineLocator templateLocator(Workspace workspace) {
        return new DefaultTemplatePipelineLocator(workspace);
    }

    @Bean
    public TemplatePipelineEngine templateEngine(TemplatePipelineLocator locator) {
        return new DefaultTemplatePipelineEngine(locator);
    }

    static class DefaultTemplatePipelineLocator implements TemplatePipelineLocator {
        private final Map<String, TemplatePipeline> pipelines = new HashMap<>();

        DefaultTemplatePipelineLocator(Workspace workspace) {
            Path path = workspace.getPipelineDir().toPath();
            try (Stream<Path> stream = Files.walk(path)) {
                stream.filter(Files::isRegularFile) // Finds only files, not directories
                        .forEach(e -> {
                            try {
                                TemplatePipeline pipeline = new TemplatePipelineMarkdown(Files.readString(e));
                                pipelines.put(pipeline.getName(), pipeline);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public TemplatePipeline getTemplatePipeline(String name) throws TemplatePipelineNotFoundException {
            if (pipelines.containsKey(name)) {
                return pipelines.get(name);
            }
            throw new TemplatePipelineNotFoundException(name);
        }
    }
}
