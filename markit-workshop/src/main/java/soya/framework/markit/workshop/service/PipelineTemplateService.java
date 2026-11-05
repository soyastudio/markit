package soya.framework.markit.workshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sorya.framework.markita.PipelineTemplateLocator;
import soya.framework.markit.workshop.configuration.Workspace;

import java.io.File;
import java.io.IOException;

@Service
public class PipelineTemplateService {

    private File templateProjectDir;

    @Autowired
    PipelineTemplateLocator locator;

    public PipelineTemplateService(Workspace workspace) {
        this.templateProjectDir = workspace.getTemplateProjectDir();
    }

    public String get(String name) {
        return locator.getTemplatePipeline(name).toString();
    }

    public String process(String templateName) throws IOException {
        return "TODO: ";
    }

}
