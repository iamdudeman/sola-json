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

    throw new InvalidCharacterException(currentChar);
  }

  private Character peek() {
    int peekIndex = textIndex + 1;

    return peekIndex < text.length() ? text.charAt(peekIndex) : null;
  }

  private void advance() {
    textIndex++;
    currentChar = textIndex < text.length() ? text.charAt(textIndex) : null;
  }
}
