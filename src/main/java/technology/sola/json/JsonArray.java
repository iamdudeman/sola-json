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

  public boolean add(JsonObject value) {
    return super.add(new JsonElement(value));
  }

  public boolean add(JsonArray value) {
    return add(new JsonElement(value));
  }

  public boolean add(String value) {
    return add(new JsonElement(value));
  }

  public boolean add(Integer value) {
    return add(new JsonElement(value));
  }

  public boolean add(Long value) {
    return add(new JsonElement(value));
  }

  public boolean add(Float value) {
    return add(new JsonElement(value));
  }

  public boolean add(Double value) {
    return add(new JsonElement(value));
  }

  public boolean add(Boolean value) {
    return add(new JsonElement(value));
  }

  public boolean addNull() {
    return add(new JsonElement());
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
