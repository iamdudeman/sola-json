package technology.sola.json;

import technology.sola.json.exception.InvalidCharacterException;

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
