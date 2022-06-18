package technology.sola.json.exception;

import technology.sola.json.tokenizer.TokenType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InvalidSyntaxException extends RuntimeException {
  private final int startIndex;
  private final TokenType actual;
  private final TokenType[] expected;

  public InvalidSyntaxException(TokenType actual, int index, TokenType... expected) {
    super(String.format("Expected [%s] but received [%s] at [%s]", formatExpectedTokens(expected), actual.name(), index));

    this.actual = actual;
    this.startIndex = index;
    this.expected = expected;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public TokenType getActual() {
    return actual;
  }

  public TokenType[] getExpected() {
    return expected;
  }

  private static String formatExpectedTokens(TokenType... expected) {
    return Arrays.stream(expected).map(TokenType::name).collect(Collectors.joining(" or "));
  }
}
