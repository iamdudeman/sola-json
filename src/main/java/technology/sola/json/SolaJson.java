package technology.sola.json;

import technology.sola.json.exception.InvalidAbstractSyntaxTreeException;
import technology.sola.json.mapper.JsonMapper;
import technology.sola.json.parser.AstNode;
import technology.sola.json.parser.AstNodeType;
import technology.sola.json.parser.SolaJsonParser;
import technology.sola.json.serializer.SolaJsonSerializer;
import technology.sola.json.serializer.SolaJsonSerializerConfig;
import technology.sola.json.tokenizer.SolaJsonTokenizer;

import java.util.List;

/**
 * SolaJson contains methods for parsing strings into {@link JsonElement}s and serializing {@code JsonElement}s into strings.
 */
public class SolaJson {
  private final SolaJsonSerializer solaJsonSerializer;

  /**
   * Creates a new SolaJson instance with default {@link SolaJsonSerializerConfig}.
   */
  public SolaJson() {
    this(new SolaJsonSerializerConfig(0));
  }

  /**
   * Creates a new SolaJson instance with desired {@link SolaJsonSerializerConfig}.
   *
   * @param config the serializer configuration
   */
  public SolaJson(SolaJsonSerializerConfig config) {
    solaJsonSerializer = new SolaJsonSerializer(config);
  }

  /**
   * Parses a JSON string into a {@link JsonElement}.
   *
   * @param jsonString the JSON string to parse
   * @return the parsed {@code JsonElement}
   */
  public JsonElement parse(String jsonString) {
    SolaJsonTokenizer solaJsonTokenizer = new SolaJsonTokenizer(jsonString);
    SolaJsonParser solaJsonParser = new SolaJsonParser(solaJsonTokenizer);
    AstNode root = solaJsonParser.parse();

    return visit(root);
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
    return solaJsonSerializer.serialize(jsonObject);
  }

  /**
   * Serializes a {@link JsonArray}.
   *
   * @param jsonArray the {@code JsonArray} to serialize
   * @return serialized JSON string
   */
  public String stringify(JsonArray jsonArray) {
    return solaJsonSerializer.serialize(jsonArray);
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

  private JsonElement visit(AstNode astNode) {
    return switch (astNode.type()) {
      case OBJECT -> visitObject(astNode);
      case ARRAY -> visitArray(astNode);
      case VALUE -> visitValue(astNode);
      default -> throw new InvalidAbstractSyntaxTreeException();
    };
  }

  private JsonElement visitObject(AstNode astNode) {
    JsonObject jsonObject = new JsonObject(astNode.children().length);

    for (AstNode pairNode : astNode.children()) {
      if (pairNode.type() != AstNodeType.PAIR) {
        throw new InvalidAbstractSyntaxTreeException();
      }

      String key = pairNode.token().value();
      JsonElement value = visit(pairNode.children()[0]);

      jsonObject.put(key, value);
    }

    return new JsonElement(jsonObject);
  }

  private JsonElement visitArray(AstNode astNode) {
    JsonArray jsonArray = new JsonArray(astNode.children().length);

    for (AstNode childNode : astNode.children()) {
      jsonArray.add(visit(childNode));
    }

    return new JsonElement(jsonArray);
  }

  private JsonElement visitValue(AstNode astNode) {
    return switch (astNode.token().type()) {
      case STRING -> new JsonElement(astNode.token().value());
      case TRUE -> new JsonElement(true);
      case FALSE -> new JsonElement(false);
      case NULL -> new JsonElement();
      case NUMBER -> {
        String value = astNode.token().value();

        if (value.contains(".")) {
          yield new JsonElement(Double.parseDouble(value));
        } else {
          yield new JsonElement(Long.parseLong(value));
        }
      }
      default -> throw new InvalidAbstractSyntaxTreeException();
    };
  }
}
