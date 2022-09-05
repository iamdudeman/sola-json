package technology.sola.json.tokenizer;

/**
 * Creates a new token instance of {@link TokenType} with value.
 *
 * @param type  the {@code TokenType}
 * @param value the value of the token
 */
public record Token(TokenType type, String value) {
  /**
   * Creates a new token instance of {@link TokenType} that does not have a value.
   *
   * @param type the {@code TokenType}
   */
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
