package sorya.framework.markita.support;

import java.util.LinkedHashMap;

public class PipelineInitializer extends AbstractTemplateComponent {
    public static final String INITIALIZER = "PIPELINE_INITIALIZER";

    private String scriptType;
    private String script;

    private String schemaFormat;
    private String schema;

    public PipelineInitializer() {
        super(INITIALIZER, new LinkedHashMap<>());
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getSchemaFormat() {
        return schemaFormat;
    }

    public void setSchemaFormat(String schemaFormat) {
        this.schemaFormat = schemaFormat;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
