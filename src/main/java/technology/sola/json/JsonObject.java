package technology.sola.json;

import technology.sola.json.exception.PropertyNotFoundException;

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

  public JsonElement asElement() {
    return new JsonElement(this);
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

  public int getInt(String key) {
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

  public JsonElement put(String key, JsonObject value) {
    if (value == null) return putNull(key);

    return put(key, value.asElement());
  }

  public JsonElement put(String key, JsonArray value) {
    if (value == null) return putNull(key);

    return put(key, value.asElement());
  }

  public JsonElement put(String key, String value) {
    if (value == null) return putNull(key);

    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Integer value) {
    if (value == null) return putNull(key);

    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Long value) {
    if (value == null) return putNull(key);

    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Double value) {
    if (value == null) return putNull(key);

    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Float value) {
    if (value == null) return putNull(key);

    return put(key, new JsonElement(value));
  }

  public JsonElement put(String key, Boolean value) {
    if (value == null) return putNull(key);

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
