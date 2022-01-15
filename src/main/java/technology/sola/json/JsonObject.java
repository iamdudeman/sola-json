package technology.sola.json;

import java.util.HashMap;

public class JsonObject extends HashMap<String, JsonElement> {
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

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("{");

    forEach((key, value) -> stringBuilder.append(String.format("\"%s\":%s,", key, value.toString())));

    if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
    stringBuilder.append("}");

    return stringBuilder.toString();
  }
}
