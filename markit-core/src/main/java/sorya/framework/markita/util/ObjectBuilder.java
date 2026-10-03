package sorya.framework.markita.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class ObjectBuilder {
    public static <T> T create(Class<T> type, Map<String, String> params) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(gson.toJson(params), type);
    }

    public static void build(Object o, Map<String, String> params) {
        Class<?> cls = o.getClass();
    }
}
