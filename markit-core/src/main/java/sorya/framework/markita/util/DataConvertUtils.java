package sorya.framework.markita.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Type;
import java.util.Map;

public final class DataConvertUtils {
    public static final String JSON_FORMAT = "JSON";
    public static final String YAML_FORMAT = "YAML";
    public static final String XML_FORMAT = "XML";
    public static final String CSV_FORMAT = "CSV";

    private DataConvertUtils() {
    }

    public static Map<String, Object> toMap(String format, String data) {
        switch (format.toUpperCase()) {
            case JSON_FORMAT -> {
                return fromJson(data);
            }
            case YAML_FORMAT -> {
                return fromYaml(data);
            }
            case XML_FORMAT -> {
                return fromXml(data);
            }
            case CSV_FORMAT -> {
                return fromCsv(data);
            }
            default -> throw new IllegalArgumentException("Format is not supported: " + format);
        }
    }

    private static Map<String, Object> fromJson(String data) {
        Gson gson = new Gson();

        // Define the target Map type
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();

        // Convert to Map
        return gson.fromJson(data, mapType);
    }

    private static Map<String, Object> fromYaml(String data) {
        return new Yaml().load(data);
    }

    private static Map<String, Object> fromXml(String data) {
        return null;
    }

    private static Map<String, Object> fromCsv(String data) {
        return null;
    }
}
