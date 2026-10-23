package sorya.framework.markita.support;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

public class TemplateComponentDescriptor {
    private final String name;
    private final Map<String, String> properties = new LinkedHashMap<>();

    public TemplateComponentDescriptor(String exp) {
        URI uri = URI.create("https://" + exp.trim());
        this.name = uri.getHost();
        if (uri.getRawQuery() != null) {
            String[] arr = uri.getRawQuery().split("&");
            for (String s : arr) {
                String[] pair = s.split("=");
                properties.put(pair[0], pair[1]);
            }
        }
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProperties() {
        return new LinkedHashMap<>(properties);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        if (!properties.isEmpty()) {
            builder.append("?");
            properties.forEach((k, v) -> {
                builder.append(k).append("=").append(v).append("&");
            });
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
