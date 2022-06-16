package technology.sola.json;

import java.util.HashMap;

/**
 * JsonObject is a {@link java.util.Map} of {@link JsonElement}. It includes methods for accessing members as various
 * Java types.
 */
public class JsonObject extends HashMap<String, JsonElement> {
  public JsonObject() {
  }

  public JsonObject(int initialCapacity) {
    super(initialCapacity);
  }

  public JsonObject getObject(String key) {
    return get(key).asObject();
  }

  public JsonObject getObject(String key, JsonObject defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asObject();
  }

  public JsonArray getArray(String key) {
    return get(key).asArray();
  }

  public JsonArray getArray(String key, JsonArray defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asArray();
  }

  public String getString(String key) {
    return get(key).asString();
  }

  public String getString(String key, String defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asString();
  }

  public double getDouble(String key) {
    return get(key).asDouble();
  }

  public Double getDouble(String key, Double defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asDouble();
  }

  public float getFloat(String key) {
    return get(key).asFloat();
  }

  public Float getFloat(String key, Float defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asFloat();
  }

  public int getInt(String key) {
    return get(key).asInt();
  }

  public Integer getInt(String key, Integer defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asInt();
  }

  public long getLong(String key) {
    return get(key).asLong();
  }

  public Long getLong(String key, Long defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asLong();
  }

  public boolean getBoolean(String key) {
    return get(key).asBoolean();
  }

  public Boolean getBoolean(String key, Boolean defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asBoolean();
  }

  public boolean isNull(String key) {
    return get(key).isNull();
  }

  public JsonElement put(String key, JsonObject value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, JsonArray value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, String value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Integer value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Long value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Double value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Float value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Boolean value) {
    return put(key, new JsonElement(value));
  }

  public JsonElement putNull(String key) {
    return put(key, new JsonElement());
  }

  @Override
  public String toString() {
    return toString(0);
  }

  public String toString(int spaces) {
    return toString(spaces, 0);
  }

  String toString(int spaces, int depth) {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("{");

    String separator = spaces > 0 ? ": " : ":";

    forEach((key, value) -> {
      if (spaces > 0) {
        stringBuilder.append("\n");
        stringBuilder.append(" ".repeat(spaces * (depth + 1)));
      }

      stringBuilder.append(String.format("\"%s\"%s%s,", key, separator, value.toString(spaces, depth + 1)));
    });

    if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
    if (spaces > 0 && size() > 0) {
      stringBuilder.append("\n");
      stringBuilder.append(" ".repeat(spaces * depth));
    }
    stringBuilder.append("}");

    return stringBuilder.toString();
  }
}
