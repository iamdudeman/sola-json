package technology.sola.json.token;

public record Token(TokenType type, String value) {
  @Override
  public String toString() {
    return "Token{" +
      "type=" + type +
      ", value='" + value + '\'' +
      '}';
  }
}
