package technology.sola.json.parser;

import technology.sola.json.tokenizer.Token;

import java.util.Arrays;

public record AstNode(AstNodeType type, Token token, AstNode... children) {
  public static AstNode array(AstNode... children) {
    return new AstNode(AstNodeType.ARRAY, null, children);
  }

  public static AstNode pair(Token nameToken, AstNode valueNode) {
    return new AstNode(AstNodeType.PAIR, nameToken, valueNode);
  }

  public static AstNode object(AstNode... pairs) {
    return new AstNode(AstNodeType.OBJECT, null, pairs);
  }

  public static AstNode value(Token token, AstNode... children) {
    return new AstNode(AstNodeType.VALUE, token, children);
  }

  @Override
  public String toString() {
    return "AstNode{" +
      "type=" + type +
      ", token=" + token +
      ", children=" + Arrays.toString(children) +
      '}';
  }
}
