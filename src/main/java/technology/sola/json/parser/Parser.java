package technology.sola.json.parser;

import technology.sola.json.exception.InvalidSyntaxException;
import technology.sola.json.tokenizer.Token;
import technology.sola.json.tokenizer.TokenType;
import technology.sola.json.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Parser {
  private final Tokenizer tokenizer;
  private Token currentToken;

  public Parser(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
    currentToken = tokenizer.getNextToken();
  }

  public AstNode parse() {
    AstNode node = ruleRoot();

    if (currentToken.type() != TokenType.EOF) {
      throw new InvalidSyntaxException(currentToken.type(), tokenizer.getTextIndex(), TokenType.EOF);
    }

    return node;
  }

  private AstNode ruleRoot() {
    return switch (currentToken.type()) {
      case L_BRACKET -> ruleArray();
      case L_CURLY -> ruleObject();
      default ->
        throw new InvalidSyntaxException(currentToken.type(), tokenizer.getTextIndex(), TokenType.L_BRACKET, TokenType.L_CURLY);
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
    Token nameToken = currentToken;
    eat(TokenType.STRING);
    eat(TokenType.COLON);

    return AstNode.pair(nameToken, ruleValue());
  }

  private AstNode ruleValue() {
    Token token = currentToken;

    return switch (currentToken.type()) {
      case L_BRACKET -> ruleArray();
      case L_CURLY -> ruleObject();
      case TRUE -> {
        eat(TokenType.TRUE);
        yield AstNode.value(token);
      }
      case FALSE -> {
        eat(TokenType.FALSE);
        yield AstNode.value(token);
      }
      case NULL -> {
        eat(TokenType.NULL);
        yield AstNode.value(token);
      }
      case STRING -> {
        eat(TokenType.STRING);
        yield AstNode.value(token);
      }
      case NUMBER -> {
        eat(TokenType.NUMBER);
        yield AstNode.value(token);
      }
      default -> throw new InvalidSyntaxException(
        token.type(), tokenizer.getTextIndex(),
        TokenType.L_BRACKET, TokenType.L_CURLY, TokenType.TRUE, TokenType.FALSE, TokenType.NULL, TokenType.STRING, TokenType.NUMBER
      );
    };
  }

  private void eat(TokenType tokenType) {
    if (currentToken.type() == tokenType) {
      currentToken = tokenizer.getNextToken();
    } else {
      throw new InvalidSyntaxException(currentToken.type(), tokenizer.getTextIndex(), tokenType);
    }
  }
}
