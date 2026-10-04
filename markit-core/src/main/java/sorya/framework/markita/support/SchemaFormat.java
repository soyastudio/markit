package sorya.framework.markita.support;

public enum SchemaFormat {
    YAML, JSON, XML;


    static final String INPUT_FENCE_CHAR = "`";
    static final String OUTPUT_FENCE_CHAR = "~";

    static boolean isSchema(String format) {
        if (format == null) return false;
        try {
            SchemaFormat.valueOf(format.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
