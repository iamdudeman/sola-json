package technology.sola.json.parser;

import technology.sola.json.exception.InvalidSyntaxException;
import technology.sola.json.tokenizer.Token;
import technology.sola.json.tokenizer.TokenType;
import technology.sola.json.tokenizer.SolaJsonTokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * A JSON parser implementation.
 */
public class SolaJsonParser {
  private final SolaJsonTokenizer solaJsonTokenizer;
  private Token currentToken;
  private int textIndex;

  /**
   * Creates a new instance utilizing the provided {@link SolaJsonTokenizer}.
   *
   * @param solaJsonTokenizer the tokenizer to use for parsing
   */
  public SolaJsonParser(SolaJsonTokenizer solaJsonTokenizer) {
    this.solaJsonTokenizer = solaJsonTokenizer;
    textIndex = solaJsonTokenizer.getTextIndex();
    currentToken = solaJsonTokenizer.getNextToken();
  }

  /**
   * Parses utilizing the provided {@link SolaJsonTokenizer}.
   *
   * @return the root {@link AstNode}
   */
  public AstNode parse() {
    AstNode node = ruleRoot();

    if (currentToken.type() != TokenType.EOF) {
      throw new InvalidSyntaxException(currentToken.type(), textIndex, TokenType.EOF);
    }

    return node;
  }

  private AstNode ruleRoot() {
    return switch (currentToken.type()) {
      case L_BRACKET -> ruleArray();
      case L_CURLY -> ruleObject();
      default ->
        throw new InvalidSyntaxException(currentToken.type(), textIndex, TokenType.L_BRACKET, TokenType.L_CURLY);
    };
  }

  private AstNode ruleObject() {
    eat(TokenType.L_CURLY);
    List<AstNode> pairs = new ArrayList<>();

    if (currentToken.type() != TokenType.R_CURLY) {
      pairs.add(rulePair());
    }

    while (currentToken.type() == TokenType.COMMA) {
      eat(TokenType.COMMA);
      pairs.add(rulePair());
    }

    eat(TokenType.R_CURLY);
    return AstNode.object(pairs.toArray(new AstNode[0]));
  }

  private AstNode ruleArray() {
    eat(TokenType.L_BRACKET);
    List<AstNode> children = new ArrayList<>();

    if (currentToken.type() != TokenType.R_BRACKET) {
      children.add(ruleValue());
    }

    while (currentToken.type() == TokenType.COMMA) {
      eat(TokenType.COMMA);
      children.add(ruleValue());
    }

    eat(TokenType.R_BRACKET);
    return AstNode.array(children.toArray(new AstNode[0]));
  }

  private AstNode rulePair() {
    Token nameToken = eat(TokenType.STRING);
    eat(TokenType.COLON);

    return AstNode.pair(nameToken, ruleValue());
  }

  private AstNode ruleValue() {
    return switch (currentToken.type()) {
      case L_BRACKET -> ruleArray();
      case L_CURLY -> ruleObject();
      case TRUE -> AstNode.value(eat(TokenType.TRUE));
      case FALSE -> AstNode.value(eat(TokenType.FALSE));
      case NULL -> AstNode.value(eat(TokenType.NULL));
      case STRING -> AstNode.value(eat(TokenType.STRING));
      case NUMBER -> AstNode.value(eat(TokenType.NUMBER));
      default -> throw new InvalidSyntaxException(
        currentToken.type(), textIndex,
        TokenType.L_BRACKET, TokenType.L_CURLY, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.STRING, TokenType.NUMBER
      );
    };
  }

  private Token eat(TokenType tokenType) {
    var token = currentToken;

    if (currentToken.type() == tokenType) {
      textIndex = solaJsonTokenizer.getTextIndex();
      currentToken = solaJsonTokenizer.getNextToken();

      return token;
    } else {
      throw new InvalidSyntaxException(currentToken.type(), textIndex, tokenType);
    }
  }
}
