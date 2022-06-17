package technology.sola.json.tokenizer;

public record Token(TokenType type, String value) {
  public Token(TokenType type) {
    this(type, null);
  }

  @Override
  public String toString() {
    return "Token{" +
      "type=" + type +
      ", value='" + value + '\'' +
      '}';
  }
}
