package technology.sola.json;

public class JsonElement {
  private final JsonValueType type;
  private Object value;

  public JsonElement() {
    type = JsonValueType.NULL;
  }

  public JsonElement(int value) {
    type = JsonValueType.LONG;
    this.value = value;
  }

  public JsonElement(long value) {
    type = JsonValueType.LONG;
    this.value = value;
  }

  public JsonElement(float value) {
    type = JsonValueType.DOUBLE;
    this.value = value;
  }

  public JsonElement(double value) {
    type = JsonValueType.DOUBLE;
    this.value = value;
  }

  public JsonElement(String value) {
    type = JsonValueType.STRING;
    this.value = value;
  }

  public JsonElement(JsonObject value) {
    type = JsonValueType.JSON_OBJECT;
    this.value = value;
  }

  public JsonElement(JsonArray value) {
    type = JsonValueType.JSON_ARRAY;
    this.value = value;
  }

  public JsonElement(boolean value) {
    type = JsonValueType.BOOLEAN;
    this.value = value;
  }

  public boolean asBoolean() {
    assertType(JsonValueType.BOOLEAN);
    return (boolean) this.value;
  }

  public int asInt() {
    return (int) asLong();
  }

  public long asLong() {
    assertType(JsonValueType.LONG);
    return (long) this.value;
  }

  public float asFloat() {
    return (float) asDouble();
  }

  public double asDouble() {
    assertType(JsonValueType.DOUBLE);
    return (double) this.value;
  }

  public String asString() {
    assertType(JsonValueType.STRING);
    return (String) this.value;
  }

  public JsonObject asObject() {
    assertType(JsonValueType.JSON_OBJECT);
    return (JsonObject) this.value;
  }

  public JsonArray asArray() {
    assertType(JsonValueType.JSON_ARRAY);
    return (JsonArray) this.value;
  }

  public boolean isNull() {
    return type == JsonValueType.NULL;
  }

  @Override
  public String toString() {
    if (type == JsonValueType.STRING) {
      return "\"" + value + "\"";
    }

    return value == null ? "null" : value.toString();
  }

  private void assertType(JsonValueType assertionType) {
    if (type != assertionType) {
      throw new RuntimeException("Json value is not of desired type");
    }
  }

  private enum JsonValueType {
    BOOLEAN,
    DOUBLE,
    JSON_ARRAY,
    JSON_OBJECT,
    LONG,
    NULL,
    STRING,
  }
}