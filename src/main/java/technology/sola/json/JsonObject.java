package technology.sola.json;

import java.util.HashMap;

public class JsonObject extends HashMap<String, JsonValue> {
  public JsonObject() {
  }

  public JsonObject(int initialCapacity) {
    super(initialCapacity);
  }

  public JsonObject getObject(String key) {
    return get(key).asObject();
  }

  public JsonArray getArray(String key) {
    return get(key).asArray();
  }
}
