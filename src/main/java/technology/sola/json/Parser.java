package technology.sola.json;

import technology.sola.json.exception.InvalidSyntaxException;

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
      throw new InvalidSyntaxException();
    }

    return node;
  }

  private AstNode ruleRoot() {
    return switch (currentToken.type()) {
      case L_BRACKET -> ruleArray();
      case L_CURLY -> ruleObject();
      default -> throw new InvalidSyntaxException();
    };
  }

  private AstNode ruleObject() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private AstNode ruleArray() {
    eat(TokenType.L_BRACKET);
    List<AstNode> children = new ArrayList<>();
    children.add(ruleValue());

    while (currentToken.type() == TokenType.COMMA) {
      eat(TokenType.COMMA);
      children.add(ruleValue());
    }

    eat(TokenType.R_BRACKET);

    return AstNode.arrayNode(children.toArray(new AstNode[0]));
  }

  private AstNode ruleValue() {
    Token token = currentToken;

    return switch (currentToken.type()) {
      case TRUE -> {
        eat(TokenType.TRUE);
        yield AstNode.valueNode(token);
      }
      case FALSE -> {
        eat(TokenType.FALSE);
        yield AstNode.valueNode(token);
      }
      case NULL -> {
        eat(TokenType.NULL);
        yield AstNode.valueNode(token);
      }
      default -> throw new RuntimeException("Unrecognized value type");
    };
  }

  private void eat(TokenType tokenType) {
    if (currentToken.type() == tokenType) {
      currentToken = tokenizer.getNextToken();
    } else {
      throw new InvalidSyntaxException();
    }
  }
}
