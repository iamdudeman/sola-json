package technology.sola.json.parser;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.JsonArray;
import technology.sola.json.JsonElement;
import technology.sola.json.JsonObject;
import technology.sola.json.parser.exception.InvalidSyntaxException;
import technology.sola.json.tokenizer.Token;
import technology.sola.json.tokenizer.TokenType;
import technology.sola.json.tokenizer.JsonTokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * A JSON parser implementation.
 */
@NullMarked
public class JsonParser {
  private final JsonTokenizer jsonTokenizer;
  private Token currentToken;

  /**
   * Creates a new instance utilizing the provided {@link JsonTokenizer}.
   *
   * @param jsonTokenizer the tokenizer to use for parsing
   */
  public JsonParser(JsonTokenizer jsonTokenizer) {
    this.jsonTokenizer = jsonTokenizer;
    currentToken = jsonTokenizer.getNextToken();
  }

  /**
   * Parses utilizing the provided {@link JsonTokenizer}.
   *
   * @return the root {@link JsonElement}
   */
  public JsonElement parse() {
    JsonElement node = ruleRoot();

    if (currentToken.type() != TokenType.EOF) {
      throw new InvalidSyntaxException(currentToken, TokenType.EOF);
    }

    return node;
  }

  private JsonElement ruleRoot() {
    return switch (currentToken.type()) {
      case L_BRACKET -> new JsonElement(ruleArray());
      case L_CURLY -> new JsonElement(ruleObject());
      default ->
        throw new InvalidSyntaxException(currentToken, TokenType.L_BRACKET, TokenType.L_CURLY);
    };
  }

  private JsonObject ruleObject() {
    eat(TokenType.L_CURLY);
    JsonObject jsonObject = new JsonObject();

    if (currentToken.type() != TokenType.R_CURLY) {
      var pair = rulePair();

      jsonObject.put(pair.key.value(), pair.value);
    }

    while (currentToken.type() == TokenType.COMMA) {
      eat(TokenType.COMMA);

      var pair = rulePair();

      jsonObject.put(pair.key.value(), pair.value);
    }

    eat(TokenType.R_CURLY);
    return jsonObject;
  }

  private JsonArray ruleArray() {
    eat(TokenType.L_BRACKET);
    List<JsonElement> children = new ArrayList<>();

    if (currentToken.type() != TokenType.R_BRACKET) {
      children.add(ruleValue());
    }

    while (currentToken.type() == TokenType.COMMA) {
      eat(TokenType.COMMA);
      children.add(ruleValue());
    }

    eat(TokenType.R_BRACKET);
    return new JsonArray(children);
  }

  private JsonObjectPair rulePair() {
    Token nameToken = eat(TokenType.STRING);
    eat(TokenType.COLON);

    return new JsonObjectPair(nameToken, ruleValue());
  }

  private record JsonObjectPair(Token key, JsonElement value) {
  }

  private JsonElement ruleValue() {
    return switch (currentToken.type()) {
      case L_BRACKET -> new JsonElement(ruleArray());
      case L_CURLY -> new JsonElement(ruleObject());
      case TRUE -> {
        eat(TokenType.TRUE);
        yield new JsonElement(true);
      }
      case FALSE -> {
        eat(TokenType.FALSE);
        yield new JsonElement(false);
      }
      case NULL -> {
        eat(TokenType.NULL);
        yield new JsonElement();
      }
      case STRING -> new JsonElement(eat(TokenType.STRING).value());
      case NUMBER -> {
        String value = eat(TokenType.NUMBER).value();

        if (value.contains(".") || value.contains("e") || value.contains("E")) {
          yield new JsonElement(Double.parseDouble(value));
        } else {
          yield new JsonElement(Long.parseLong(value));
        }
      }
      default -> throw new InvalidSyntaxException(
        currentToken,
        TokenType.L_BRACKET, TokenType.L_CURLY, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.STRING, TokenType.NUMBER
      );
    };
  }

  private Token eat(TokenType tokenType) {
    var token = currentToken;

    if (currentToken.type() == tokenType) {
      currentToken = jsonTokenizer.getNextToken();

      return token;
    } else {
      throw new InvalidSyntaxException(currentToken, tokenType);
    }
  }
}
