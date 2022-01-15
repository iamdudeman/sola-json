package technology.sola.json.token;

import technology.sola.json.exception.InvalidCharacterException;
import technology.sola.json.exception.InvalidControlCharacterException;
import technology.sola.json.exception.InvalidNumberException;
import technology.sola.json.exception.StringNotClosedException;

public class Tokenizer {
  private final char[] characters;
  private Character currentChar;
  private int textIndex;

  public Tokenizer(String text) {
    characters = text.toCharArray();
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
      textIndex += 3;
      advance();
      return new Token(TokenType.TRUE, "true");
    }

    if (currentChar == 'f' && isExpectedPeek('a', 'l', 's', 'e')) {
      textIndex += 4;
      advance();
      return new Token(TokenType.FALSE, "false");
    }

    if (currentChar == 'n' && isExpectedPeek('u', 'l', 'l')) {
      textIndex += 3;
      advance();
      return new Token(TokenType.NULL, "null");
    }

    throw new InvalidCharacterException(currentChar);
  }

  private Token tokenString() {
    StringBuilder stringTokenWithEscapesBuilder = null;
    advance();
    int start = textIndex;

    while (currentChar != null && currentChar != '\"') {
      if (currentChar == '\\') {
        if (stringTokenWithEscapesBuilder == null) {
          stringTokenWithEscapesBuilder = new StringBuilder();
          stringTokenWithEscapesBuilder.append(characters, start, textIndex - start);
        }

        advanceControlCharacter(stringTokenWithEscapesBuilder);
      } else if (stringTokenWithEscapesBuilder != null) {
        stringTokenWithEscapesBuilder.append(currentChar);
      }

      advance();
    }

    if (currentChar == null) {
      throw new StringNotClosedException();
    }

    advance();

    String tokenValue = stringTokenWithEscapesBuilder == null
      ? new String(characters, start, textIndex - start - 1)
      : stringTokenWithEscapesBuilder.toString();

    return new Token(TokenType.STRING, tokenValue);
  }

  private void advanceControlCharacter(StringBuilder stringBuilder) {
    advance();
    char result = switch (currentChar) {
      case '"' -> '\"';
      case '\\' -> '\\';
      case '/' -> '/';
      case 'b' -> '\b';
      case 'f' -> '\f';
      case 'n' -> '\n';
      case 'r' -> '\r';
      case 't' -> '\t';
      case 'u' -> {
        advance();

        if (textIndex + 4 > characters.length) {
          throw new InvalidControlCharacterException();
        }

        try {
          int codePoint = Integer.parseInt(new String(characters, textIndex, 4), 16);
          textIndex += 2;
          advance();
          yield (char) codePoint;
        } catch (NumberFormatException ex) {
          throw new InvalidControlCharacterException();
        }
      }
      default -> throw new InvalidControlCharacterException();
    };

    stringBuilder.append(result);
  }

  private Token tokenNumber() {
    int startIndex = textIndex;

    advanceNumber();
    advanceFraction();
    advanceExponent();

    int characterCount = textIndex - startIndex;

    if (characterCount == 1 && characters[startIndex] == '-') {
      throw new InvalidNumberException();
    }

    return new Token(TokenType.NUMBER, new String(characters, startIndex, characterCount));
  }

  private void advanceNumber() {
    advance();

    while (currentChar != null && Character.isDigit(currentChar)) {
      advance();
    }
  }

  private void advanceFraction() {
    int startFraction = textIndex;
    if (currentChar != null && currentChar == '.') {
      advance();

      while (currentChar != null && Character.isDigit(currentChar)) {
        advance();
      }
    }
    if (textIndex - startFraction == 1) {
      throw new InvalidNumberException();
    }
  }

  private void advanceExponent() {
    if (currentChar != null && (currentChar == 'e' || currentChar == 'E')) {
      advance();

      if (currentChar == '+' || currentChar == '-') {
        advance();
      }

      while (currentChar != null && Character.isDigit(currentChar)) {
        advance();
      }
    }
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

    return peekIndex < characters.length ? characters[peekIndex] : null;
  }

  private void advance() {
    textIndex++;
    currentChar = textIndex < characters.length ? characters[textIndex] : null;
  }
}
