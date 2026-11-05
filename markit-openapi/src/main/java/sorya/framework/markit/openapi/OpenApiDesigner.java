package sorya.framework.markit.openapi;

import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.core.models.ParseOptions;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.apache.commons.io.IOUtils;
import sorya.framework.markita.Pipeline;
import sorya.framework.markita.support.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OpenApiDesigner implements PipelineInitializer<OpenAPI> {

    private static final String OPENAPI_DESIGNER_DESCRIPTION;
    private static final String OPENAPI_META_INFO;
    private static final String OPENAPI_OPERATION;
    private static final String OPENAPI_COMPONENTS;

    static {
        try {
            OPENAPI_DESIGNER_DESCRIPTION = loadFromClasspath("openapi-designer-description.md");
            OPENAPI_META_INFO = loadFromClasspath("openapi-meta-info.yaml");
            OPENAPI_OPERATION = loadFromClasspath("openapi-operation.yaml");
            OPENAPI_COMPONENTS = loadFromClasspath("openapi-components.yaml");

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static String loadFromClasspath(String path) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        if (inputStream == null) {
            throw new NullPointerException("File not found: " + path);
        }
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

    private OpenAPI openAPI;

    @Override
    public OpenAPI getResource() {
        return openAPI;
    }

    @Override
    public void init(Pipeline pipeline) {
        PipelineMarkdown pl = (PipelineMarkdown) pipeline;
        PipelineMetadata metadata = pl.getMetadata();
        TemplateMarkdownNode root = pl.getRootMarkdownNode();
        root.getDescriptions().add(OPENAPI_DESIGNER_DESCRIPTION);

        // init params:
        TemplateMarkdownNode init = root.getChild(PipelineMarkdown.INIT_PARAMS).getChild(PipelineInitParamsDefinition.INIT_PARAMS);
        PipelineInitParamsDefinition initParamsDefinition = (PipelineInitParamsDefinition) init.getComponentDefinition();
        initParamsDefinition.getParamElement().setBody(OPENAPI_META_INFO);

        // operations:
        TemplateMarkdownNode annotators = root.getChild(PipelineMarkdown.ANNOTATORS);
        if(metadata.getMetadata("metadata.init.operations") instanceof List<?> list) {
            list.forEach(o -> {
                AnnotationDefinition annotationDefinition = new AnnotationDefinition(o.toString(), null);
                annotationDefinition.getScriptElement().setBody(OPENAPI_OPERATION.replace("operationId:", "operationId: " + o));
                pl.getRootMarkdownNode().getChild(PipelineMarkdown.ANNOTATORS).addChild(new TemplateMarkdownNode(o.toString(), "Operation Annotation: " + o,  annotationDefinition));
            });
        }

        // components:
        AnnotationDefinition annotationDefinition = new AnnotationDefinition("Components", null);
        annotationDefinition.getScriptElement().setFormat("yaml");
        annotationDefinition.getScriptElement().setBody(OPENAPI_COMPONENTS);
        annotators.addChild(new TemplateMarkdownNode("Components", "Component Annotation", annotationDefinition));
    }

    private OpenAPI parse(String url) {
        // 1. Optional: Configure parse options (e.g., resolving $ref references)
        ParseOptions options = new ParseOptions();
        options.setResolve(true);
        options.setResolveFully(true);

        // 2. Parse the specification
        SwaggerParseResult result = new OpenAPIParser().readLocation(url, null, options);

        // 3. Check for validation messages or errors
        if (result.getMessages() != null && !result.getMessages().isEmpty()) {
            System.out.println("Validation messages / errors:");
            result.getMessages().forEach(System.out::println);
        }

        // 4. Extract the OpenAPI POJO
        return result.getOpenAPI();
    }
}
