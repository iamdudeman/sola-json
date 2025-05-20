package technology.sola.json;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import technology.sola.json.exception.JsonElementTypeException;
import technology.sola.json.serializer.JsonSerializer;
import technology.sola.json.serializer.JsonSerializerConfig;

import java.util.LinkedHashMap;

/**
 * JsonObject is a {@link java.util.Map} of {@link JsonElement}. It includes methods for accessing members as various
 * Java types.
 */
@NullMarked
public class JsonObject extends LinkedHashMap<String, JsonElement> {
  /**
   * Constructs an empty {@link JsonObject} with the default initial capacity (16) and the default load factor (0.75).
   */
  public JsonObject() {
    super();
  }

  /**
   * Constructs an empty {@link JsonObject} with the specified initial capacity and the default load factor (0.75).
   *
   * @param initialCapacity the initial capacity
   * @throws IllegalArgumentException if the initial capacity is negative.
   */
  public JsonObject(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Constructs an empty {@link JsonObject} with the specified initial capacity and the specified load factor.
   *
   * @param initialCapacity the initial capacity
   * @param loadFactor      the load factor
   * @throws IllegalArgumentException if the initial capacity is negative or the load factor is non-positive
   */
  public JsonObject(int initialCapacity, float loadFactor) {
    super(initialCapacity, loadFactor);
  }

  /**
   * Returns the {@link JsonObject} for the specified key.
   *
   * @param key the key of the {@link JsonObject} to be returned
   * @return the {@link JsonObject} to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#JSON_OBJECT}
   */
  public JsonObject getObject(String key) {
    return get(key).asObject();
  }

  /**
   * Returns the {@link JsonObject} for the specified key. Default value is returned if key is missing.
   *
   * @param key          the key of the {@link JsonObject} to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the {@link JsonObject} to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#JSON_OBJECT}
   */
  public JsonObject getObject(String key, JsonObject defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asObject();
  }

  /**
   * Returns the {@link JsonArray} for the specified key.
   *
   * @param key the key of the {@link JsonArray} to be returned
   * @return the {@link JsonArray} to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#JSON_ARRAY}
   */
  public JsonArray getArray(String key) {
    return get(key).asArray();
  }

  /**
   * Returns the {@link JsonArray} for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the {@link JsonArray} to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the {@link JsonArray} to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#JSON_ARRAY}
   */
  public JsonArray getArray(String key, JsonArray defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asArray();
  }

  /**
   * Returns the string for the specified key.
   *
   * @param key the key of the string to be returned
   * @return the string to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#STRING}
   */
  public String getString(String key) {
    return get(key).asString();
  }

  /**
   * Returns the string for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the string to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the string to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#STRING}
   */
  public String getString(String key, String defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asString();
  }

  /**
   * Returns the double for the specified key.
   *
   * @param key the key of the double to be returned
   * @return the double to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#DOUBLE}
   */
  public double getDouble(String key) {
    return get(key).asDouble();
  }

  /**
   * Returns the double for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the double to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the double to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#DOUBLE}
   */
  public Double getDouble(String key, Double defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asDouble();
  }

  /**
   * Returns the float for the specified key.
   *
   * @param key the key of the float to be returned
   * @return the float to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#DOUBLE}
   */
  public float getFloat(String key) {
    return get(key).asFloat();
  }

  /**
   * Returns the float for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the float to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the float to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#DOUBLE}
   */
  public Float getFloat(String key, Float defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asFloat();
  }

  /**
   * Returns the integer for the specified key.
   *
   * @param key the key of the integer to be returned
   * @return the integer to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#LONG}
   */
  public int getInt(String key) {
    return get(key).asInt();
  }

  /**
   * Returns the integer for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the integer to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the integer to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#LONG}
   */
  public Integer getInt(String key, Integer defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asInt();
  }

  /**
   * Returns the long for the specified key.
   *
   * @param key the key of the long to be returned
   * @return the long to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#LONG}
   */
  public long getLong(String key) {
    return get(key).asLong();
  }

  /**
   * Returns the long for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the long to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the long to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#LONG}
   */
  public Long getLong(String key, Long defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asLong();
  }

  /**
   * Returns the boolean for the specified key.
   *
   * @param key the key of the boolean to be returned
   * @return the boolean to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#BOOLEAN}
   */
  public boolean getBoolean(String key) {
    return get(key).asBoolean();
  }

  /**
   * Returns the boolean for the specified key. Default value is returned if key is missing.
   *
   * @param key the key of the boolean to be returned
   * @param defaultValue the default value to return if key is missing
   * @return the boolean to which the specified key is mapped
   * @throws JsonElementTypeException if {@link JsonElement} at index is not of type {@link JsonElementType#BOOLEAN}
   */
  public Boolean getBoolean(String key, Boolean defaultValue) {
    var value = get(key);

    if (value == null || value.isNull()) {
      return defaultValue;
    }

    return value.asBoolean();
  }

  /**
   * Returns true if {@link JsonElementType#NULL} is at specified key.
   *
   * @param key the key to be checked
   * @return true if {@link JsonElementType#NULL} is at specified key
   */
  public boolean isNull(String key) {
    return get(key).isNull();
  }

  @Override
  @Nullable
  public JsonElement put(String key, @Nullable JsonElement value) {
    return super.put(key, value == null ? new JsonElement() : value);
  }

  /**
   * Associates a {@link JsonObject} with specified key.
   *
   * @param key   the key to associate the {@link JsonObject} with
   * @param value the {@link JsonObject} to associate
   * @return this
   */
  public JsonObject put(String key, JsonObject value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a {@link JsonArray} with specified key.
   *
   * @param key   the key to associate the {@link JsonArray} with
   * @param value the {@link JsonArray} to associate
   * @return this
   */
  public JsonObject put(String key, JsonArray value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a string with specified key.
   *
   * @param key   the key to associate the string with
   * @param value the string to associate
   * @return this
   */
  public JsonObject put(String key, String value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a integer with specified key.
   *
   * @param key   the key to associate the integer with
   * @param value the integer to associate
   * @return this
   */
  public JsonObject put(String key, Integer value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a long with specified key.
   *
   * @param key   the key to associate the long with
   * @param value the long to associate
   * @return this
   */
  public JsonObject put(String key, Long value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a double with specified key.
   *
   * @param key   the key to associate the double with
   * @param value the double to associate
   * @return this
   */
  public JsonObject put(String key, Double value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a float with specified key.
   *
   * @param key   the key to associate the float with
   * @param value the float to associate
   * @return this
   */
  public JsonObject put(String key, Float value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Associates a boolean with specified key.
   *
   * @param key   the key to associate the boolean with
   * @param value the boolean to associate
   * @return this
   */
  public JsonObject put(String key, Boolean value) {
    put(key, new JsonElement(value));

    return this;
  }

  /**
   * Puts {@link JsonElement} of type {@link JsonElementType#NULL} into this {@link JsonObject}.
   *
   * @param key the key to put a NULL JSON value into
   * @return this
   */
  public JsonObject putNull(String key) {
    put(key, new JsonElement());

    return this;
  }

  /**
   * Merges this {@link JsonObject} with another resulting in a new {@code JsonObject}.
   * <ol>
   *   <li>If key in both and each value is an object then it will recursively merge and add to result</li>
   *   <li>If key in both and each value is not an object, value from the right is added to the result</li>
   *   <li>If key in this only, then this value is added to result</li>
   *   <li>If key in other only, then other value is added to result</li>
   * </ol>
   * This does not create a deep copy of nested arrays and objects.
   *
   * @param other the object to merge with
   * @return a new {@code JsonObject} with merged properties
   */
  public JsonObject merge(JsonObject other) {
    JsonObject result = new JsonObject();

    forEach((key, value) -> {
      var otherValue = other.get(key);

      // add in any unique keys from this object
      if (otherValue == null) {
        result.put(key, value);
      } else {
        // nested merge if value in both is an object
        if (value.getType() == JsonElementType.JSON_OBJECT && otherValue.getType() == JsonElementType.JSON_OBJECT) {
          result.put(key, value.asObject().merge(otherValue.asObject()));
        } else {
          result.put(key, otherValue);
        }
      }
    });

    // add in any unique keys from the other object
    other.forEach((key, value) -> {
      var thisValue = this.get(key);

      if (thisValue == null) {
        result.put(key, value);
      }
    });

    return result;
  }

  /**
   * Formats this {@link JsonObject} as a string with no indentation.
   *
   * @return formatted JSON string
   */
  @Override
  public String toString() {
    return toString(0);
  }

  /**
   * Formats this {@link JsonObject} as a string with specified spaces for indentation.
   *
   * @param spaces the spaces for each indentation
   * @return formatted JSON string with spaces for indentation
   */
  public String toString(int spaces) {
    JsonSerializer jsonSerializer = new JsonSerializer(
      new JsonSerializerConfig(spaces)
    );

    return jsonSerializer.serialize(this);
  }
}
