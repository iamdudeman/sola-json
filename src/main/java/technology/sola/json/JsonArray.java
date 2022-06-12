package technology.sola.json;

import java.util.ArrayList;

public class JsonArray extends ArrayList<JsonElement> {
  public JsonArray() {
  }

  public JsonArray(int initialCapacity) {
    super(initialCapacity);
  }

  public JsonElement asElement() {
    return new JsonElement(this);
  }

  public JsonObject getObject(int index) {
    return get(index).asObject();
  }

  public JsonArray getArray(int index) {
    return get(index).asArray();
  }

  public String getString(int index) {
    return get(index).asString();
  }

  public int getInt(int index) {
    return get(index).asInt();
  }

  public long getLong(int index) {
    return get(index).asLong();
  }

  public double getDouble(int index) {
    return get(index).asDouble();
  }

  public float getFloat(int index) {
    return get(index).asFloat();
  }

  public boolean getBoolean(int index) {
    return get(index).asBoolean();
  }

  public boolean isNull(int index) {
    return get(index).isNull();
  }

  public boolean add(JsonObject value) {
    if (value == null) return addNull();

    return add(value.asElement());
  }

  public boolean add(JsonArray value) {
    if (value == null) return addNull();

    return add(value.asElement());
  }

  public boolean add(String value) {
    if (value == null) return addNull();

    return add(new JsonElement(value));
  }

  public boolean add(Integer value) {
    if (value == null) return addNull();

    return add(new JsonElement(value));
  }

  public boolean add(Long value) {
    if (value == null) return addNull();

    return add(new JsonElement(value));
  }

  public boolean add(Float value) {
    if (value == null) return addNull();

    return add(new JsonElement(value));
  }

  public boolean add(Double value) {
    if (value == null) return addNull();

    return add(new JsonElement(value));
  }

  public boolean add(Boolean value) {
    if (value == null) return addNull();

    return add(new JsonElement(value));
  }

  public boolean addNull() {
    return add(new JsonElement());
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

    stringBuilder.append("[");

    forEach(item -> {
      if (spaces > 0) {
        stringBuilder.append("\n");
        stringBuilder.append(" ".repeat(spaces * (depth + 1)));
      }
      stringBuilder.append(item.toString(spaces, depth + 1));
      stringBuilder.append(",");
    });

    if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
    if (spaces > 0 && size() > 0) {
      stringBuilder.append("\n");
      stringBuilder.append(" ".repeat(spaces * depth));
    }
    stringBuilder.append("]");

    return stringBuilder.toString();
  }
}
