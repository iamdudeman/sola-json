package technology.sola.json;

import technology.sola.json.exception.JsonElementTypeException;

/**
 * JsonElement represents any valid JSON value. Valid JSON values include {@link JsonObject}, {@link JsonArray}, string,
 * number, true, false and null.
 */
public class JsonElement {
  private final JsonElementType type;
  private Object value;

  public JsonElement() {
    type = JsonElementType.NULL;
  }

  public JsonElement(Integer value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.LONG;
    this.value = value;
  }

  public JsonElement(Long value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.LONG;
    this.value = value;
  }

  public JsonElement(Float value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.DOUBLE;
    this.value = value;
  }

  public JsonElement(Double value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.DOUBLE;
    this.value = value;
  }

  public JsonElement(String value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.STRING;
    this.value = value;
  }

  public JsonElement(JsonObject value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.JSON_OBJECT;
    this.value = value;
  }

  public JsonElement(JsonArray value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.JSON_ARRAY;
    this.value = value;
  }

  public JsonElement(Boolean value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.BOOLEAN;
    this.value = value;
  }

  public boolean asBoolean() {
    assertType(JsonElementType.BOOLEAN);
    return (boolean) this.value;
  }

  public int asInt() {
    assertType(JsonElementType.LONG);
    return ((Number) this.value).intValue();
  }

  public long asLong() {
    assertType(JsonElementType.LONG);
    return (long) this.value;
  }

  public float asFloat() {
    assertType(JsonElementType.DOUBLE);
    return ((Number) this.value).floatValue();
  }

  public double asDouble() {
    assertType(JsonElementType.DOUBLE);
    return (double) this.value;
  }

  public String asString() {
    assertType(JsonElementType.STRING);
    return (String) this.value;
  }

  public JsonObject asObject() {
    assertType(JsonElementType.JSON_OBJECT);
    return (JsonObject) this.value;
  }

  public JsonArray asArray() {
    assertType(JsonElementType.JSON_ARRAY);
    return (JsonArray) this.value;
  }

  public boolean isNull() {
    return type == JsonElementType.NULL;
  }

  public JsonElementType getType() {
    return type;
  }

  @Override
  public String toString() {
    return toString(0);
  }

  public String toString(int spaces) {
    return toString(spaces, 0);
  }

  String toString(int spaces, int depth) {
    if (type == JsonElementType.STRING) {
      return "\"" + escapeNonUnicode(value.toString()) + "\"";
    }

    if (type == JsonElementType.JSON_ARRAY) {
      return asArray().toString(spaces, depth);
    }

    if (type == JsonElementType.JSON_OBJECT) {
      return asObject().toString(spaces, depth);
    }

    return value == null ? "null" : value.toString();
  }

  private static String escapeNonUnicode(String s) {
    return s.replace("\\", "\\\\")
      .replace("\t", "\\t")
      .replace("\b", "\\b")
      .replace("\n", "\\n")
      .replace("\r", "\\r")
      .replace("\f", "\\f")
      .replace("\"", "\\\"")
      // line separator
      .replace("\u2028", "\\u2028")
      // paragraph separator
      .replace("\u2029", "\\u2029")
      ;
  }

  private void assertType(JsonElementType assertionType) {
    if (type != assertionType) {
      throw new JsonElementTypeException(assertionType.name(), type.name());
    }
  }
}
