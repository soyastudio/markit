package sorya.framework.markita.openapi;

import org.commonmark.node.FencedCodeBlock;
import sorya.framework.markita.support.ExecutableMarkdown;
import sorya.framework.markita.support.TemplateMarkdownNode;

public class OpenApiMarkdown extends ExecutableMarkdown<OpenApiMarkdown.OpenApiMetadata> {

    public OpenApiMarkdown(String markdown) {
        super(markdown);
    }

    @Override
    protected void init() {


    }

    @Override
    protected void configure() {

    }

    @Override
    protected void processFencedBlock(TemplateMarkdownNode tn, FencedCodeBlock fencedCodeBlock) {

    }

    static class OpenApiMetadata extends ExecutableMarkdown.Metadata {

        @Override
        protected String[] getDeclaredFieldNames() {
            return new String[0];
        }
    }
}
