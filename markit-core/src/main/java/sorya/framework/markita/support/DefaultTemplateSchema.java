package sorya.framework.markita.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.poi.ss.formula.functions.T;
import org.yaml.snakeyaml.Yaml;
import sorya.framework.markita.TemplateParameter;
import sorya.framework.markita.TemplateSchema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DefaultTemplateSchema implements TemplateSchema {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private List<TemplateParameter> parameters = new ArrayList<>();

    public DefaultTemplateSchema() {
    }

    public DefaultTemplateSchema(List<TemplateParameter> parameters) {
        if(parameters != null) {
            this.parameters = parameters;
        }
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public List<TemplateParameter> getParameters() {
        return parameters;
    }

    public static TemplateSchema create(String format, String schema) {
        if ("yaml".equalsIgnoreCase(format)) {
            return fromYaml(schema);
        } else if ("json".equalsIgnoreCase(format)) {
            return fromJsonSchema(schema);
        } else if ("xml".equalsIgnoreCase(format)) {
            return fromXmlSchema(schema);
        } else {
            return null;
        }
    }

    public static TemplateSchema fromYaml(String yaml) {
        List<TemplateParameter> params = new ArrayList<>();
        Map<String, Object> data = new Yaml().load(yaml);
        if(data.containsKey("$schema")) {

        } else {
            data.forEach((k, v) -> {
                if (v instanceof Map<?,?> map) {
                    Map<Object, Object> param = new LinkedHashMap<>(map);
                    param.put("name", k);
                    params.add(GSON.fromJson(GSON.toJson(param), DefaultTemplateParameter.class));
                }
            });
        }

        return new DefaultTemplateSchema(params);
    }

    public static TemplateSchema fromJsonSchema(String jsonSchema) {
        return null;
    }

    public static TemplateSchema fromXmlSchema(String xmlSchema) {
        return null;
    }

}
