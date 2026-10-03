package soya.framework.markit.workshop.configuration;

import java.io.File;

public interface Workspace {
    File getHome();

    File getFunctionDir();

    File getTemplateDir();

    File getProjectDir();

    File getTemplateProjectDir();
}
