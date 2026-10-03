package soya.framework.markit.workshop.service;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import sorya.framework.markita.TemplateEngine;
import sorya.framework.markita.util.TextBuilder;
import sorya.framework.markita.util.ZipEncryptUtils;
import soya.framework.markit.workshop.configuration.Workspace;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ProjectService {

    private final File projectDir;

    @Value("${soya.framework.markit.empty-project-template}")
    private Resource emptyProjectTemplate;

    @Value("${soya.framework.markit.secret-key}")
    private String secretKey;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private TemplateService templateService;

    public ProjectService(Workspace workspace) {
        projectDir = workspace.getProjectDir();
        try {
            File file = new File(workspace.getTemplateDir(), "azure-sql-ingestion-template.md");
            String md = Files.readString(Path.of(file.toURI()));
            String key = "JMVPebNWUd1LTYiVL9Ymrg1ycNnLceJILhhoppZa3DU=";
            String compressed = ZipEncryptUtils.zipAndEncrypt(md, ZipEncryptUtils.getSecretKey(key));

            TextBuilder builder = TextBuilder.builder("template: ").setIndent(2);
            builder.indentRight();
            for (int i = 0; i < compressed.length(); i++) {
                if (i % 128 == 0) {
                    builder.newLineWithCurrentIndents("- ");
                }
                builder.append(compressed.charAt(i));
            }
            builder.indentLeft();
            System.out.println(builder.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String create(String id, String template) {
        try {
            return templateEngine.create(id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String get(String id, String template, boolean update) throws IOException{
        String result = null;

        File dir = new File(projectDir, id);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File worksheet = new File(dir, id + ".md");
        if(!worksheet.exists()) {

        } else {
            result = Files.readString(Path.of(worksheet.toURI()));
        }

        return result;
    }

    public String process(String projectId) throws IOException {
        File dir = new File(projectDir, projectId);
        if (!dir.exists()) {
            dir.mkdir();
        }

        File worksheet = new File(dir, projectId + ".md");
        String markdown;
        if (!worksheet.exists()) {
            // 1. create new
            worksheet.createNewFile();
            markdown = templateEngine.create(projectId);

        } else {
            markdown = Files.readString(Path.of(worksheet.toURI()));
            markdown = templateEngine.execute(markdown);
        }

        Files.writeString(Path.of(worksheet.toURI()), createDefault(projectId));

        return markdown;
    }

    private String createDefault(String projectId) throws IOException {
        Map<String, Object> context = new LinkedHashMap<>();
        context.put("projectId", projectId);


        Template template = Mustache.compiler().compile(emptyProjectTemplate.getContentAsString(StandardCharsets.UTF_8));
        return template.execute(context);
    }

}
