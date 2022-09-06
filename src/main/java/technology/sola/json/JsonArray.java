package technology.sola.json;

import technology.sola.json.serializer.SolaJsonSerializer;

import java.util.ArrayList;

/**
 * JsonArray is an array of {@link JsonElement}s. It includes methods for accessing members as various
 * Java types.
 */
public class JsonArray extends ArrayList<JsonElement> {
  public JsonArray() {
  }

  public JsonArray(int initialCapacity) {
    super(initialCapacity);
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

  @Override
  public boolean add(JsonElement jsonElement) {
    return super.add(jsonElement == null ? new JsonElement() : jsonElement);
  }

  @Override
  public void add(int index, JsonElement element) {
    super.add(index, element == null ? new JsonElement() : element);
  }

  public boolean add(JsonObject value) {
    return add(new JsonElement(value));
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

  @Override
  public String toString() {
    return toString(0);
  }

  /**
   * Formats this {@link JsonArray} as a string with desired spaces for indentation.
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
