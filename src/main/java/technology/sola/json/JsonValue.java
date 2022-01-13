package technology.sola.json;

public class JsonValue {
  private final JsonValueType type;
  private Object value;

  public JsonValue() {
    type = JsonValueType.NULL;
  }

  public JsonValue(int value) {
    type = JsonValueType.LONG;
    this.value = value;
  }

  public JsonValue(long value) {
    type = JsonValueType.LONG;
    this.value = value;
  }

  public JsonValue(float value) {
    type = JsonValueType.DOUBLE;
    this.value = value;
  }

  public JsonValue(double value) {
    type = JsonValueType.DOUBLE;
    this.value = value;
  }

  public JsonValue(String value) {
    type = JsonValueType.STRING;
    this.value = value;
  }

  public JsonValue(JsonObject value) {
    type = JsonValueType.JSON_OBJECT;
    this.value = value;
  }

  public JsonValue(JsonArray value) {
    type = JsonValueType.JSON_ARRAY;
    this.value = value;
  }

  public JsonValue(boolean value) {
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
