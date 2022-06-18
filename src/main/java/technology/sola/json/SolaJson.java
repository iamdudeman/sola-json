package technology.sola.json;

import technology.sola.json.mapper.JsonMapper;
import technology.sola.json.parser.AstNode;
import technology.sola.json.parser.AstNodeType;
import technology.sola.json.parser.SolaJsonParser;
import technology.sola.json.tokenizer.SolaJsonTokenizer;

import java.util.List;

/**
 * SolaJson contains methods for parsing strings into {@link JsonElement}s and serializing {@code JsonElement}s into strings.
 */
public class SolaJson {
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
   * Serializes a {@link JsonElement}.
   *
   * @param jsonElement the {@code JsonElement} to serialize
   * @return serialized JSON string
   */
  public String serialize(JsonElement jsonElement) {
    return serialize(jsonElement, 0);
  }

  /**
   * Serializes a {@link JsonElement} with spaces for indentation.
   *
   * @param jsonElement the {@code JsonElement} to serialize
   * @param spaces      the spaces for indentation
   * @return serialized JSON string
   */
  public String serialize(JsonElement jsonElement, int spaces) {
    return jsonElement.toString(spaces);
  }

  /**
   * Serializes a {@link JsonObject}.
   *
   * @param jsonObject the {@code JsonObject} to serialize
   * @return serialized JSON string
   */
  public String serialize(JsonObject jsonObject) {
    return serialize(jsonObject, 0);
  }

  /**
   * Serializes a {@link JsonObject} with spaces for indentation.
   *
   * @param jsonObject the {@code JsonObject} to serialize
   * @param spaces     the spaces for indentation
   * @return serialized JSON string
   */
  public String serialize(JsonObject jsonObject, int spaces) {
    return jsonObject.toString(spaces);
  }

  /**
   * Serializes a {@link JsonArray}.
   *
   * @param jsonArray the {@code JsonArray} to serialize
   * @return serialized JSON string
   */
  public String serialize(JsonArray jsonArray) {
    return serialize(jsonArray, 0);
  }

  /**
   * Serializes a {@link JsonArray} with spaces for indentation.
   *
   * @param jsonArray the {@code JsonArray} to serialize
   * @param spaces    the spaces for indentation
   * @return serialized JSON string
   */
  public String serialize(JsonArray jsonArray, int spaces) {
    return jsonArray.toString(spaces);
  }

  /**
   * Serializes an object of type T to a string using a {@link JsonMapper}.
   *
   * @param object     the object to serialize
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param <T>        the type of the object to serialize
   * @return serialized JSON string
   */
  public <T> String serialize(T object, JsonMapper<T> jsonMapper) {
    return serialize(object, jsonMapper, 0);
  }

  /**
   * Serializes an object of type T to a string using a {@link JsonMapper} with spaces for indentation.
   *
   * @param object     the object to serialize
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param spaces     the spaces for indentation
   * @param <T>        the type of the object to serialize
   * @return serialized JSON string
   */
  public <T> String serialize(T object, JsonMapper<T> jsonMapper, int spaces) {
    return serialize(jsonMapper.toJson(object), spaces);
  }

  /**
   * Serializes a list of objects of type T to a string using a {@link JsonMapper}.
   *
   * @param list       the list of objects to serialize
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param <T>        the type of the object to serialize
   * @return serialized JSON string
   */
  public <T> String serializeList(List<T> list, JsonMapper<T> jsonMapper) {
    return serializeList(list, jsonMapper, 0);
  }

  /**
   * Serializes a list of objects of type T to a string using a {@link JsonMapper} with spaces for indentation.
   *
   * @param list       the list of objects to serialize
   * @param jsonMapper the {@code JsonMapper} to use during conversion
   * @param spaces     the spaces for indentation
   * @param <T>        the type of the object to serialize
   * @return serialized JSON string
   */
  public <T> String serializeList(List<T> list, JsonMapper<T> jsonMapper, int spaces) {
    return serialize(jsonMapper.toJson(list), spaces);
  }

  private JsonElement visit(AstNode astNode) {
    return switch (astNode.type()) {
      case OBJECT -> visitObject(astNode);
      case ARRAY -> visitArray(astNode);
      case VALUE -> visitValue(astNode);
      default -> throw new RuntimeException("Invalid AST");
    };
  }

  private JsonElement visitObject(AstNode astNode) {
    JsonObject jsonObject = new JsonObject(astNode.children().length);

    for (AstNode pairNode : astNode.children()) {
      if (pairNode.type() != AstNodeType.PAIR) {
        throw new RuntimeException("Invalid AST");
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
      default -> throw new RuntimeException("Invalid AST");
    };
  }
}
