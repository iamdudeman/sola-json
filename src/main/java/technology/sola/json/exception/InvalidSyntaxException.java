package technology.sola.json.exception;

import technology.sola.json.tokenizer.Token;
import technology.sola.json.tokenizer.TokenType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Exception thrown when invalid syntax is discovered during parsing.
 */
public class InvalidSyntaxException extends RuntimeException implements SolaJsonError {
  private transient final Token actual;
  private transient final TokenType[] expected;

  /**
   * Creates a new instance of this exception.
   *
   * @param actual   the {@link Token} that was received
   * @param expected the possible {@link TokenType}s that were expected
   */
  public InvalidSyntaxException(Token actual, TokenType... expected) {
    super(
      String.format("Expected [%s] but received [%s] at [%s:%s]",
        formatExpectedTokens(expected), actual.type().name(), actual.line(), actual.column())
    );

    this.actual = actual;
    this.expected = expected;
  }

  @Override
  public int getLine() {
    return actual.line();
  }

  @Override
  public int getColumn() {
    return actual.column();
  }

  /**
   * @return the received {@link TokenType}
   */
  public TokenType getActual() {
    return actual.type();
  }

  /**
   * @return the possible {@link TokenType}s that were expected
   */
  public TokenType[] getExpected() {
    return expected;
  }

  private static String formatExpectedTokens(TokenType... expected) {
    return Arrays.stream(expected).map(TokenType::name).collect(Collectors.joining(" or "));
  }
}
