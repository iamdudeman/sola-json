package technology.sola.json;

import java.util.function.Function;

public class JsonElementBuilder {
  public static JsonArrayBuilder startArray() {
    return new JsonArrayBuilder();
  }

  public static JsonObjectBuilder startObject() {
    return new JsonObjectBuilder();
  }

  public static class JsonArrayBuilder {
    private final JsonArray jsonArray;

    private JsonArrayBuilder() {
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
      return addObject(consumer.apply(new JsonObjectBuilder()).finish());
    }

    public JsonArrayBuilder addArray(JsonArray value) {
      jsonArray.add(value);
      return this;
    }

    public JsonArrayBuilder addArray(Function<JsonArrayBuilder, JsonArrayBuilder> consumer) {
      return addArray(consumer.apply(new JsonArrayBuilder()).finish());
    }

    public JsonArray finish() {
      return jsonArray;
    }
  }

  public static class JsonObjectBuilder {
    private final JsonObject jsonObject;

    private JsonObjectBuilder() {
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
      return addObject(key, consumer.apply(new JsonObjectBuilder()).finish());
    }

    public JsonObjectBuilder addArray(String key, JsonArray value) {
      jsonObject.put(key, value);
      return this;
    }

    public JsonObjectBuilder addArray(String key, Function<JsonArrayBuilder, JsonArrayBuilder> consumer) {
      return addArray(key, consumer.apply(new JsonArrayBuilder()).finish());
    }

    public JsonObject finish() {
      return jsonObject;
    }
  }

  private JsonElementBuilder() {
  }
}
