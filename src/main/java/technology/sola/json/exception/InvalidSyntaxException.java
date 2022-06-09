package technology.sola.json.exception;

import technology.sola.json.token.TokenType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InvalidSyntaxException extends RuntimeException {
  public InvalidSyntaxException(TokenType actual, int index, TokenType ...expected) {
    super("Expected [" + Arrays.stream(expected).map(TokenType::name).collect(Collectors.joining(" or ")) + "] but received [" + actual.name() + "] at [" + index + "]");
  }
}
