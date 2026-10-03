package soya.framework.markit.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorya.framework.markita.TemplateBuilder;
import sorya.framework.markita.TemplateLocator;
import soya.framework.markit.workshop.configuration.Workspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class TemplateService {

    private File templateProjectDir;

    @Autowired
    TemplateLocator locator;

    @Autowired
    TemplateBuilder templateBuilder;

    public TemplateService(Workspace workspace) {
        this.templateProjectDir = workspace.getTemplateProjectDir();
    }

    public String get(String name) {
        return locator.getTemplate(name).toString();
    }

    public String process(String templateName) throws IOException {
        File dir = new File(templateProjectDir, templateName);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File worksheet = new File(dir, templateName + ".md");
        String markdown;
        if(!worksheet.exists()) {
            worksheet.createNewFile();
            markdown = templateBuilder.init(templateName);
        } else {
            markdown = Files.readString(worksheet.toPath());
            markdown = templateBuilder.build(markdown);
        }

        Files.writeString(worksheet.toPath(), markdown);
        return markdown;
    }

}
