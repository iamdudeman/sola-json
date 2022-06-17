package technology.sola.json.exception;

import technology.sola.json.tokenizer.TokenType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InvalidSyntaxException extends RuntimeException {
  public InvalidSyntaxException(TokenType actual, int index, TokenType... expected) {
    super(String.format("Expected [%s] but received [%s] at [%s]", formatExpectedTokens(expected), actual.name(), index));
  }

  private static String formatExpectedTokens(TokenType... expected) {
    return Arrays.stream(expected).map(TokenType::name).collect(Collectors.joining(" or "));
  }
}
