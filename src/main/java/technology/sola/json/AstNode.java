package technology.sola.json;

public record AstNode(AstNodeType type, Token token, AstNode... children) {
  public static AstNode arrayNode(AstNode... children) {
    return new AstNode(AstNodeType.ARRAY, null, children);
  }

  public static AstNode valueNode(Token token, AstNode... children) {
    return new AstNode(AstNodeType.VALUE, token, children);
  }
}
