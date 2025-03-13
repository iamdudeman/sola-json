package technology.sola.json.tokenizer;

/**
 * Creates a new token instance of {@link TokenType} with value.
 *
 * @param type   the {@code TokenType}
 * @param value  the value of the token
 * @param line   the line number where the token was found
 * @param column the column number where the token was found
 */
public record Token(TokenType type, String value, int line, int column) {
  /**
   * Creates a new token instance of {@link TokenType} that does not have a value.
   *
   * @param type   the {@code TokenType}
   * @param line   the line number where the token was found
   * @param column the column number where the token was found
   */
  public Token(TokenType type, int line, int column) {
    this(type, null, line, column);
  }

  @Override
  public String toString() {
    return "Token{" +
      "type=" + type +
      ", value='" + value + '\'' +
      '}';
  }
}
