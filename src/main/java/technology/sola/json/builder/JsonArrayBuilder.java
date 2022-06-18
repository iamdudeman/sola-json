package technology.sola.json.builder;

import technology.sola.json.JsonArray;
import technology.sola.json.JsonObject;

import java.util.function.Function;

/**
 * JsonArrayBuilder provides convenient methods for creating a new {@link JsonArray}.
 */
public class JsonArrayBuilder {
  private final JsonArray jsonArray;

  public JsonArrayBuilder() {
    jsonArray = new JsonArray();
  }

  public JsonArrayBuilder addInt(Integer value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addLong(Long value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addFloat(Float value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addDouble(Double value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addBoolean(Boolean value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addString(String value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addNull() {
    jsonArray.addNull();
    return this;
  }

  public JsonArrayBuilder addObject(JsonObject value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addObject(Function<JsonObjectBuilder, JsonObjectBuilder> consumer) {
    return addObject(consumer.apply(new JsonObjectBuilder()).build());
  }

  public JsonArrayBuilder addArray(JsonArray value) {
    jsonArray.add(value);
    return this;
  }

  public JsonArrayBuilder addArray(Function<JsonArrayBuilder, JsonArrayBuilder> consumer) {
    return addArray(consumer.apply(new JsonArrayBuilder()).build());
  }

  public JsonArray build() {
    return jsonArray;
  }
}
