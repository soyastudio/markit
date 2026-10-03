package sorya.framework.markita.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class UriUtils {

    private UriUtils() {
    }

    public static Map<String, String> getQueryParams(String uriString) {
        Map<String, String> paramsMap = new HashMap<>();
        if (uriString.contains("?")) {
            String query = uriString.substring(uriString.indexOf("?") + 1); // e.g., "query=java&page=2"
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) : pair;
                String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) : "";
                paramsMap.put(key, value);
            }
        }

        return paramsMap;
    }
}
