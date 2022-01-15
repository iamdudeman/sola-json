package technology.sola.json.token;

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
