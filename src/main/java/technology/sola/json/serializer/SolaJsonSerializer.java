package technology.sola.json.serializer;

import technology.sola.json.JsonArray;
import technology.sola.json.JsonElement;
import technology.sola.json.JsonObject;

/**
 * SolaJsonSerializer contains methods for serializing {@link JsonElement}s, {@link JsonObject}s and {@link JsonArray}s.
 */
public class SolaJsonSerializer {
  private final SolaJsonSerializerConfig config;

  /**
   * Creates an instance of SolaJsonSerializer with desired {@link SolaJsonSerializerConfig}.
   *
   * @param config the configuration for the serializer
   */
  public SolaJsonSerializer(SolaJsonSerializerConfig config) {
    this.config = config;
  }

  /**
   * Serializes a {@link JsonElement} to a String utilizing the current {@link SolaJsonSerializerConfig}.
   *
   * @param jsonElement the {@code JsonElement} to serialize
   * @return the serialized JSON string
   */
  public String serialize(JsonElement jsonElement) {
    StringBuilder stringBuilder = new StringBuilder();

    appendJsonElement(stringBuilder, jsonElement, 0);

    return stringBuilder.toString();
  }

  /**
   * Serializes a {@link JsonObject} to a String utilizing the current {@link SolaJsonSerializerConfig}.
   *
   * @param jsonObject the {@code JsonObject} to serialize
   * @return the serialized JSON string
   */
  public String serialize(JsonObject jsonObject) {
    StringBuilder stringBuilder = new StringBuilder();

    appendJsonObject(stringBuilder, jsonObject, 0);

    return stringBuilder.toString();
  }

  /**
   * Serializes a {@link JsonArray} to a String utilizing the current {@link SolaJsonSerializerConfig}.
   *
   * @param jsonArray the {@code JsonArray} to serialize
   * @return the serialized JSON string
   */
  public String serialize(JsonArray jsonArray) {
    StringBuilder stringBuilder = new StringBuilder();

    appendJsonArray(stringBuilder, jsonArray, 0);

    return stringBuilder.toString();
  }

  private void appendJsonElement(StringBuilder stringBuilder, JsonElement jsonElement, int depth) {
    switch (jsonElement.getType()) {
      case STRING -> stringBuilder.append("\"").append(escapeNonUnicode(jsonElement.asString())).append("\"");
      case NULL -> stringBuilder.append("null");
      case JSON_ARRAY -> appendJsonArray(stringBuilder, jsonElement.asArray(), depth);
      case JSON_OBJECT -> appendJsonObject(stringBuilder, jsonElement.asObject(), depth);
      case LONG -> stringBuilder.append(jsonElement.asLong());
      case DOUBLE -> stringBuilder.append(jsonElement.asDouble());
      case BOOLEAN -> stringBuilder.append(jsonElement.asBoolean());
    }
  }

  private String escapeNonUnicode(String s) {
    return s.replace("\\", "\\\\")
      .replace("\t", "\\t")
      .replace("\b", "\\b")
      .replace("\n", "\\n")
      .replace("\r", "\\r")
      .replace("\f", "\\f")
      .replace("\"", "\\\"")
      // line separator
      .replace("\u2028", "\\u2028")
      // paragraph separator
      .replace("\u2029", "\\u2029")
      ;
  }

  private void appendJsonObject(StringBuilder stringBuilder, JsonObject jsonObject, int depth) {
    stringBuilder.append("{");

    String separator = config.spaces() > 0 ? ": " : ":";

    jsonObject.forEach((key, value) -> {
      appendNewLine(stringBuilder, depth);

      stringBuilder.append("\"").append(key).append("\"").append(separator);
      appendJsonElement(stringBuilder, value, depth + 1);
      stringBuilder.append(",");
    });

    appendCloseObjectOrArray(stringBuilder, depth, jsonObject.size(), "}");
  }

  private void appendJsonArray(StringBuilder stringBuilder, JsonArray jsonArray, int depth) {
    stringBuilder.append("[");

    jsonArray.forEach(item -> {
      appendNewLine(stringBuilder, depth);
      appendJsonElement(stringBuilder, item, depth + 1);
      stringBuilder.append(",");
    });

    appendCloseObjectOrArray(stringBuilder, depth, jsonArray.size(), "]");
  }

  private void appendNewLine(StringBuilder stringBuilder, int depth) {
    if (config.spaces() > 0) {
      stringBuilder.append("\n");
      stringBuilder.append(" ".repeat(config.spaces() * (depth + 1)));
    }
  }

  private void appendCloseObjectOrArray(StringBuilder stringBuilder, int depth, int size, String closeCharacter) {
    if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
    if (config.spaces() > 0 && size > 0) {
      stringBuilder.append("\n");
      stringBuilder.append(" ".repeat(config.spaces() * depth));
    }
    stringBuilder.append(closeCharacter);
  }
}
