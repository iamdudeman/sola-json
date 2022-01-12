package technology.sola.json;

public class JsonValue {
  private final JsonValueType type;
  private boolean booleanValue;
  private long longValue;
  private double doubleValue;
  private String stringValue;
  private JsonObject jsonObjectValue;
  private JsonArray jsonArrayValue;

  public JsonValue() {
    type = JsonValueType.NULL;
  }

  public JsonValue(int value) {
    type = JsonValueType.LONG;
    longValue = value;
  }

  public JsonValue(long value) {
    type = JsonValueType.LONG;
    longValue = value;
  }

  public JsonValue(float value) {
    type = JsonValueType.DOUBLE;
    doubleValue = value;
  }

  public JsonValue(double value) {
    type = JsonValueType.DOUBLE;
    doubleValue = value;
  }

  public JsonValue(String value) {
    type = JsonValueType.STRING;
    stringValue = value;
  }

  public JsonValue(JsonObject value) {
    type = JsonValueType.JSON_OBJECT;
    jsonObjectValue = value;
  }

  public JsonValue(JsonArray value) {
    type = JsonValueType.JSON_ARRAY;
    jsonArrayValue = value;
  }

  public JsonValue(boolean value) {
    type = JsonValueType.BOOLEAN;
    booleanValue = value;
  }

  public boolean asBoolean() {
    assertType(JsonValueType.BOOLEAN);
    return booleanValue;
  }

  public int asInt() {
    return (int) asLong();
  }

  public long asLong() {
    assertType(JsonValueType.LONG);
    return longValue;
  }

  public float asFloat() {
    return (float) asDouble();
  }

  public double asDouble() {
    assertType(JsonValueType.DOUBLE);
    return doubleValue;
  }

  public String asString() {
    assertType(JsonValueType.STRING);
    return stringValue;
  }

  public JsonObject asObject() {
    assertType(JsonValueType.JSON_OBJECT);
    return jsonObjectValue;
  }

  public JsonArray asArray() {
    assertType(JsonValueType.JSON_ARRAY);
    return jsonArrayValue;
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
