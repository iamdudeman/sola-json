package technology.sola.json;

import technology.sola.json.exception.JsonElementTypeException;
import technology.sola.json.serializer.SolaJsonSerializer;

import java.util.ArrayList;

/**
 * JsonArray is an array of {@link JsonElement}s. It includes methods for accessing members as various
 * Java types.
 */
public class JsonArray extends ArrayList<JsonElement> {
  /**
   * Constructs an empty {@link JsonArray} with an initial capacity of ten.
   */
  public JsonArray() {
    super();
  }

  /**
   * Constructs an empty {@link JsonArray} with specified initial capacity.
   *
   * @param initialCapacity the initial capacity of the list
   * @throws IllegalArgumentException if specified initial capacity is negative
   */
  public JsonArray(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Returns the {@link JsonObject} at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the {@link JsonObject} to return
   * @return the {@link JsonObject} at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#JSON_OBJECT}
   */
  public JsonObject getObject(int index) {
    return get(index).asObject();
  }

  /**
   * Returns the {@link JsonArray} at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the {@link JsonArray} to return
   * @return the {@link JsonArray} at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#JSON_ARRAY}
   */
  public JsonArray getArray(int index) {
    return get(index).asArray();
  }

  /**
   * Returns the String at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the String to return
   * @return the String at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#STRING}
   */
  public String getString(int index) {
    return get(index).asString();
  }

  /**
   * Returns the integer at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the integer to return
   * @return the integer at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#LONG}
   */
  public int getInt(int index) {
    return get(index).asInt();
  }

  /**
   * Returns the long at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the long to return
   * @return the long at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#LONG}
   */
  public long getLong(int index) {
    return get(index).asLong();
  }

  /**
   * Returns the double at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the double to return
   * @return the double at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#DOUBLE}
   */
  public double getDouble(int index) {
    return get(index).asDouble();
  }

  /**
   * Returns the float at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the float to return
   * @return the float at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#DOUBLE}
   */
  public float getFloat(int index) {
    return get(index).asFloat();
  }

  /**
   * Returns the boolean at the specified position in this {@link JsonArray}.
   *
   * @param index the index of the boolean to return
   * @return the boolean at specified index
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#BOOLEAN}
   */
  public boolean getBoolean(int index) {
    return get(index).asBoolean();
  }

  /**
   * Returns true if the {@link JsonElement} at the specified position in this {@link JsonArray} as of type {@link JsonElementType#NULL}.
   *
   * @param index the index to check if {@link JsonElementType#NULL}
   * @return the true if {@link JsonElementType#NULL} is at specified index
   */
  public boolean isNull(int index) {
    return get(index).isNull();
  }

  @Override
  public boolean add(JsonElement jsonElement) {
    return super.add(jsonElement == null ? new JsonElement() : jsonElement);
  }

  @Override
  public void add(int index, JsonElement element) {
    super.add(index, element == null ? new JsonElement() : element);
  }

  /**
   * Appends the specified {@link JsonObject} to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the {@link JsonObject} to append
   * @return this
   */
  public JsonArray add(JsonObject value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified {@link JsonArray} to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the {@link JsonArray} to append
   * @return this
   */
  public JsonArray add(JsonArray value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified string to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the string to append
   * @return this
   */
  public JsonArray add(String value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified integer to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the integer to append
   * @return this
   */
  public JsonArray add(Integer value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified long to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the long to append
   * @return this
   */
  public JsonArray add(Long value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified float to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the float to append
   * @return this
   */
  public JsonArray add(Float value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified double to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the double to append
   * @return this
   */
  public JsonArray add(Double value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends the specified boolean to the end of this {@link JsonArray}. If the specified value is null
   * the {@link JsonElementType#NULL} will be appended.
   *
   * @param value the boolean to append
   * @return this
   */
  public JsonArray add(Boolean value) {
    add(new JsonElement(value));

    return this;
  }

  /**
   * Appends {@link JsonElementType#NULL} to the end of this {@link JsonArray}.
   *
   * @return this
   */
  public JsonArray addNull() {
    add(new JsonElement());

    return this;
  }

  /**
   * Formats this {@link JsonArray} as a string with no indentation.
   *
   * @return formatted JSON string
   */
  @Override
  public String toString() {
    return toString(0);
  }

  /**
   * Formats this {@link JsonArray} as a string with specified number of spaces for indentation.
   *
   * @param spaces the spaces for each indentation
   * @return formatted JSON string with spaces for indentation
   */
  public String toString(int spaces) {
    SolaJsonSerializer solaJsonSerializer = new SolaJsonSerializer();

    solaJsonSerializer.getConfig().setSpaces(spaces);

    return solaJsonSerializer.serialize(this);
  }
}
