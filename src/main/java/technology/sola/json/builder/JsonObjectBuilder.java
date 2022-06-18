package technology.sola.json.builder;

import technology.sola.json.JsonArray;
import technology.sola.json.JsonObject;

import java.util.function.Function;

/**
 * JsonObjectBuilder provides convenient methods for creating a new {@link JsonObject}.
 */
public class JsonObjectBuilder {
  private final JsonObject jsonObject;

  public JsonObjectBuilder() {
    jsonObject = new JsonObject();
  }

  public JsonObjectBuilder addInt(String key, Integer value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addLong(String key, Long value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addFloat(String key, Float value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addDouble(String key, Double value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addBoolean(String key, Boolean value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addString(String key, String value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addNull(String key) {
    jsonObject.putNull(key);
    return this;
  }

  public JsonObjectBuilder addObject(String key, JsonObject value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addObject(String key, Function<JsonObjectBuilder, JsonObjectBuilder> consumer) {
    return addObject(key, consumer.apply(new JsonObjectBuilder()).build());
  }

  public JsonObjectBuilder addArray(String key, JsonArray value) {
    jsonObject.put(key, value);
    return this;
  }

  public JsonObjectBuilder addArray(String key, Function<JsonArrayBuilder, JsonArrayBuilder> consumer) {
    return addArray(key, consumer.apply(new JsonArrayBuilder()).build());
  }

  public JsonObject build() {
    return jsonObject;
  }
}
