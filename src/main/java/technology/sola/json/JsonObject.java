package technology.sola.json;

import technology.sola.json.exception.PropertyNotFoundException;

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

  public String getString(String key) {
    return get(key).asString();
  }

  public double getDouble(String key) {
    return get(key).asDouble();
  }

  public float getFloat(String key) {
    return get(key).asFloat();
  }

  public double getInt(String key) {
    return get(key).asInt();
  }

  public long getLong(String key) {
    return get(key).asLong();
  }

  public boolean getBoolean(String key) {
    return get(key).asBoolean();
  }

  public boolean isNull(String key) {
    return get(key).isNull();
  }

  public JsonElement get(String key) {
    JsonElement jsonElement = super.get(key);

    if (jsonElement == null) {
      throw new PropertyNotFoundException(key);
    }

    return jsonElement;
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
