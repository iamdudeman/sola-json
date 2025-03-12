package technology.sola.json.exception;

import technology.sola.json.tokenizer.TokenType;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Exception thrown when invalid syntax is discovered during parsing.
 */
public class InvalidSyntaxException extends RuntimeException implements SolaJsonError {
  /**
   * Index where the error was found.
   */
  private final int startIndex;
  /**
   * The actual {@link TokenType} seen.
   */
  private final TokenType actual;
  /**
   * The expected {@link TokenType}.
   */
  private final TokenType[] expected;

  /**
   * Creates a new instance of this exception.
   *
   * @param actual     the {@link TokenType} that was received
   * @param startIndex the index where the syntax error began
   * @param expected   the possible {@link TokenType}s that were expected
   */
  public InvalidSyntaxException(TokenType actual, int startIndex, TokenType... expected) {
    super(String.format("Expected [%s] but received [%s] at [%s]", formatExpectedTokens(expected), actual.name(), startIndex));

    this.actual = actual;
    this.startIndex = startIndex;
    this.expected = expected;
  }

  /**
   * @return the index where the syntax error began
   */
  public int getStartIndex() {
    return startIndex;
  }

  /**
   * @return the received {@link TokenType}
   */
  public TokenType getActual() {
    return actual;
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
