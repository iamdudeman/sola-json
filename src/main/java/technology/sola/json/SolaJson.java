package technology.sola.json;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.mapper.JsonMapper;
import technology.sola.json.parser.JsonParser;
import technology.sola.json.serializer.JsonSerializer;
import technology.sola.json.serializer.JsonSerializerConfig;
import technology.sola.json.tokenizer.JsonTokenizer;

import java.util.List;

/**
 * SolaJson contains methods for parsing strings into {@link JsonElement}s and serializing {@code JsonElement}s into strings.
 */
@NullMarked
public class SolaJson {
  private final JsonSerializer jsonSerializer;

  /**
   * Creates a new SolaJson instance with default {@link JsonSerializerConfig}.
   */
  public SolaJson() {
    this(new JsonSerializerConfig(0));
  }

  /**
   * Creates a new SolaJson instance with desired {@link JsonSerializerConfig}.
   *
   * @param config the serializer configuration
   */
  public SolaJson(JsonSerializerConfig config) {
    jsonSerializer = new JsonSerializer(config);
  }

  /**
   * Parses a JSON string into a {@link JsonElement}.
   *
   * @param jsonString the JSON string to parse
   * @return the parsed {@code JsonElement}
   */
  public JsonElement parse(String jsonString) {
    JsonTokenizer jsonTokenizer = new JsonTokenizer(jsonString);
    JsonParser jsonParser = new JsonParser(jsonTokenizer);

    return jsonParser.parse();
  }

  /**
   * Parses a JSON string into an object of type T using a {@link JsonMapper}.
   *
   * @param jsonString the JSON string to parse
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param <T>        the type of the object to convert to
   * @return the converted Java object of type T
   */
  public <T> T parse(String jsonString, JsonMapper<T> jsonMapper) {
    return jsonMapper.toObject(parse(jsonString).asObject());
  }

  /**
   * Parses a JSON string into a {@link List} of type T using a {@link JsonMapper}.
   *
   * @param jsonString the JSON string to parse
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param <T>        the type of the object to convert to
   * @return the converted List of Java object of type T
   */
  public <T> List<T> parseList(String jsonString, JsonMapper<T> jsonMapper) {
    return jsonMapper.toList(parse(jsonString).asArray());
  }

  /**
   * Serializes a {@link JsonObject}.
   *
   * @param jsonObject the {@code JsonObject} to serialize
   * @return serialized JSON string
   */
  public String stringify(JsonObject jsonObject) {
    return jsonSerializer.serialize(jsonObject);
  }

  /**
   * Serializes a {@link JsonArray}.
   *
   * @param jsonArray the {@code JsonArray} to serialize
   * @return serialized JSON string
   */
  public String stringify(JsonArray jsonArray) {
    return jsonSerializer.serialize(jsonArray);
  }

  /**
   * Serializes an object of type T to a string using a {@link JsonMapper}.
   *
   * @param object     the object to serialize
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param <T>        the type of the object to serialize
   * @return serialized JSON string
   */
  public <T> String stringify(T object, JsonMapper<T> jsonMapper) {
    return stringify(jsonMapper.toJson(object));
  }


  /**
   * Serializes a list of objects of type T to a string using a {@link JsonMapper}.
   *
   * @param list       the list of objects to serialize
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param <T>        the type of the object to serialize
   * @return serialized JSON string
   */
  public <T> String stringify(List<T> list, JsonMapper<T> jsonMapper) {
    return stringify(jsonMapper.toJson(list));
  }
}
