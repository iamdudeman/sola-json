package technology.sola.json;

import technology.sola.json.exception.JsonElementTypeException;
import technology.sola.json.serializer.SolaJsonSerializer;

/**
 * JsonElement represents any valid JSON value. Valid JSON values include {@link JsonObject}, {@link JsonArray}, string,
 * number, true, false and null.
 */
public class JsonElement {
  private final JsonElementType type;
  private Object value;

  /**
   * Creates a JsonElement with type {@link JsonElementType#NULL}.
   */
  public JsonElement() {
    type = JsonElementType.NULL;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#LONG} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(Integer value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.LONG;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#LONG} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(Long value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.LONG;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#DOUBLE} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(Float value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.DOUBLE;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#DOUBLE} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(Double value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.DOUBLE;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#STRING} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(String value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.STRING;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#JSON_OBJECT} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(JsonObject value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.JSON_OBJECT;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#JSON_ARRAY} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(JsonArray value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.JSON_ARRAY;
    this.value = value;
  }

  /**
   * Creates a JsonElement with type {@link JsonElementType#BOOLEAN} if value is non-null or {@link JsonElementType#NULL}
   * if null.
   *
   * @param value the value of the JsonElement
   */
  public JsonElement(Boolean value) {
    type = value == null ? JsonElementType.NULL : JsonElementType.BOOLEAN;
    this.value = value;
  }

  /**
   * Returns this JsonElement's value as a boolean if its type is {@link JsonElementType#BOOLEAN} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as boolean
   */
  public boolean asBoolean() {
    assertType(JsonElementType.BOOLEAN);
    return (boolean) this.value;
  }

  /**
   * Returns this JsonElement's value as an int if its type is {@link JsonElementType#LONG} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as int
   */
  public int asInt() {
    assertType(JsonElementType.LONG);
    return ((Number) this.value).intValue();
  }

  /**
   * Returns this JsonElement's value as a long if its type is {@link JsonElementType#LONG} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as long
   */
  public long asLong() {
    assertType(JsonElementType.LONG);
    return ((Number) this.value).longValue();
  }

  /**
   * Returns this JsonElement's value as a float if its type is {@link JsonElementType#DOUBLE} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as float
   */
  public float asFloat() {
    assertType(JsonElementType.DOUBLE);
    return ((Number) this.value).floatValue();
  }

  /**
   * Returns this JsonElement's value as a double if its type is {@link JsonElementType#DOUBLE} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as double
   */
  public double asDouble() {
    assertType(JsonElementType.DOUBLE);
    return ((Number) this.value).doubleValue();
  }

  /**
   * Returns this JsonElement's value as a String if its type is {@link JsonElementType#STRING} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as String
   */
  public String asString() {
    assertType(JsonElementType.STRING);
    return (String) this.value;
  }

  /**
   * Returns this JsonElement's value as a {@link JsonObject} if its type is {@link JsonElementType#JSON_OBJECT} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as {@code JsonObject}
   */
  public JsonObject asObject() {
    assertType(JsonElementType.JSON_OBJECT);
    return (JsonObject) this.value;
  }

  /**
   * Returns this JsonElement's value as a {@link JsonArray} if its type is {@link JsonElementType#JSON_ARRAY} or else throws
   * {@link JsonElementTypeException}.
   *
   * @return value as {@code JsonArray}
   */
  public JsonArray asArray() {
    assertType(JsonElementType.JSON_ARRAY);
    return (JsonArray) this.value;
  }

  /**
   * @return true if this JsonElement's type is {@link JsonElementType#NULL}
   */
  public boolean isNull() {
    return type == JsonElementType.NULL;
  }

  /**
   * @return the type of this JsonElement
   */
  public JsonElementType getType() {
    return type;
  }

  /**
   * Formats this {@link JsonElement} as a string with no indentation.
   *
   * @return formatted JSON string
   */
  @Override
  public String toString() {
    return toString(0);
  }

  /**
   * Formats this {@link JsonElement} as a string with specified spaces for indentation.
   *
   * @param spaces the spaces for each indentation
   * @return formatted JSON string with spaces for indentation
   */
  public String toString(int spaces) {
    SolaJsonSerializer solaJsonSerializer = new SolaJsonSerializer();

    solaJsonSerializer.getConfig().setSpaces(spaces);

    return solaJsonSerializer.serialize(this);
  }

  private void assertType(JsonElementType assertionType) {
    if (type != assertionType) {
      throw new JsonElementTypeException(assertionType.name(), type.name());
    }
  }
}
