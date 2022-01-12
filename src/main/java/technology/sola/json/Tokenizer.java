package technology.sola.json;

import technology.sola.json.exception.InvalidCharacterException;
import technology.sola.json.exception.InvalidNumberException;
import technology.sola.json.exception.StringNotClosedException;

public class Tokenizer {
  private final String text;
  private Character currentChar;
  private int textIndex;

  public Tokenizer(String text) {
    this.text = text;
    currentChar = text.charAt(textIndex);
  }

  public Token getNextToken() {
    if (currentChar == null) {
      return new Token(TokenType.EOF, null);
    }

    if (Character.isWhitespace(currentChar)) {
      advance();
      return getNextToken();
    }

    if (currentChar == ':') {
      advance();
      return new Token(TokenType.COLON, ":");
    }

    if (currentChar == ',') {
      advance();
      return new Token(TokenType.COMMA, ",");
    }

    if (currentChar == '[') {
      advance();
      return new Token(TokenType.L_BRACKET, "[");
    }

    if (currentChar == ']') {
      advance();
      return new Token(TokenType.R_BRACKET, "]");
    }

    if (currentChar == '{') {
      advance();
      return new Token(TokenType.L_CURLY, "{");
    }

    if (currentChar == '}') {
      advance();
      return new Token(TokenType.R_CURLY, "}");
    }

    if (currentChar == '"') {
      return tokenString();
    }

    if (currentChar == '-' || Character.isDigit(currentChar)) {
      return tokenNumber();
    }

    if (currentChar == 't' && isExpectedPeek('r', 'u', 'e')) {
      advance();
      advance();
      advance();
      advance();
      return new Token(TokenType.TRUE, "true");
    }

    if (currentChar == 'f' && isExpectedPeek('a', 'l', 's', 'e')) {
      advance();
      advance();
      advance();
      advance();
      advance();
      return new Token(TokenType.FALSE, "false");
    }

    if (currentChar == 'n' && isExpectedPeek('u', 'l', 'l')) {
      advance();
      advance();
      advance();
      advance();
      return new Token(TokenType.NULL, "null");
    }

    throw new InvalidCharacterException(currentChar);
  }

  private Token tokenString() {
    advance();
    StringBuilder stringBuilder = new StringBuilder();

    // TODO handle control characters

    while (currentChar != null && currentChar != '\"') {
      stringBuilder.append(currentChar);
      advance();
    }

    if (currentChar == null) {
      throw new StringNotClosedException();
    }

    advance();
    return new Token(TokenType.STRING, stringBuilder.toString());
  }

  private Token tokenNumber() {
    StringBuilder numberBuilder = new StringBuilder();
    numberBuilder.append(currentChar);
    advance();

    // number
    while (currentChar != null && Character.isDigit(currentChar)) {
      numberBuilder.append(currentChar);
      advance();
    }

    // fraction
    if (currentChar != null && currentChar == '.') {
      numberBuilder.append(currentChar);
      advance();

      while (currentChar != null && Character.isDigit(currentChar)) {
        numberBuilder.append(currentChar);
        advance();
      }
    }

    if (numberBuilder.charAt(numberBuilder.length() - 1) == '.') {
      throw new InvalidNumberException();
    }

    // exponent
    if (currentChar != null && (currentChar == 'e' || currentChar == 'E')) {
      numberBuilder.append(currentChar);
      advance();

      if (currentChar == '+' || currentChar == '-') {
        numberBuilder.append(currentChar);
        advance();
      }

      while (currentChar != null && Character.isDigit(currentChar)) {
        numberBuilder.append(currentChar);
        advance();
      }
    }

    if (numberBuilder.length() == 1 && numberBuilder.charAt(0) == '-') {
      throw new InvalidNumberException();
    }

    return new Token(TokenType.NUMBER, numberBuilder.toString());
  }

  private boolean isExpectedPeek(char... chars) {
    int offset = 1;
    for (char peekChar : chars) {
      if (!peekAndCheck(offset, peekChar)) {
        return false;
      }
      offset++;
    }

    return true;
  }

  private boolean peekAndCheck(int offset, char expectedChar) {
    return Character.valueOf(expectedChar).equals(peek(offset));
  }

  private Character peek(int offset) {
    int peekIndex = textIndex + offset;

    return peekIndex < text.length() ? text.charAt(peekIndex) : null;
  }

  private void advance() {
    textIndex++;
    currentChar = textIndex < text.length() ? text.charAt(textIndex) : null;
  }
}
