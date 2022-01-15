package technology.sola.json.token;

import technology.sola.json.exception.*;

public class Tokenizer {
  private final char[] characters;
  private Character currentChar;
  private int textIndex;

  public Tokenizer(String text) {
    characters = text.toCharArray();
    currentChar = characters[textIndex];
  }

  public Token getNextToken() {
    if (currentChar == null) {
      return new Token(TokenType.EOF);
    }

    if (Character.isWhitespace(currentChar)) {
      advance();
      return getNextToken();
    }

    if (currentChar == ':') {
      advance();
      return new Token(TokenType.COLON);
    }

    if (currentChar == ',') {
      advance();
      return new Token(TokenType.COMMA);
    }

    if (currentChar == '[') {
      advance();
      return new Token(TokenType.L_BRACKET);
    }

    if (currentChar == ']') {
      advance();
      return new Token(TokenType.R_BRACKET);
    }

    if (currentChar == '{') {
      advance();
      return new Token(TokenType.L_CURLY);
    }

    if (currentChar == '}') {
      advance();
      return new Token(TokenType.R_CURLY);
    }

    if (currentChar == '"') {
      return tokenString();
    }

    if (currentChar == '-' || Character.isDigit(currentChar)) {
      return tokenNumber();
    }

    if (currentChar == 't') {
      advanceKeywordTrue();
      return new Token(TokenType.TRUE);
    }

    if (currentChar == 'f') {
      advanceKeywordFalse();
      return new Token(TokenType.FALSE);
    }

    if (currentChar == 'n') {
      advanceKeywordNull();
      return new Token(TokenType.NULL);
    }

    throw new InvalidCharacterException(currentChar);
  }

  private Token tokenString() {
    // StringBuilder required if escaped character is present
    StringBuilder stringTokenWithEscapesBuilder = null;

    // These local fields save a lot of time from field lookups
    char[] buffer = characters;
    int localPos = textIndex + 1;
    char localChar = buffer[localPos];
    int start = localPos;

    while (localChar != '\"') {
      if (localChar == '\\') {
        if (stringTokenWithEscapesBuilder == null) {
          stringTokenWithEscapesBuilder = new StringBuilder();
          stringTokenWithEscapesBuilder.append(buffer, start, localPos - start);
        }

        localPos = advanceEscapeCharacter(buffer, localPos, stringTokenWithEscapesBuilder);
      } else if (stringTokenWithEscapesBuilder != null) {
        stringTokenWithEscapesBuilder.append(localChar);
      }

      localPos++;
      if (localPos >= buffer.length) {
        throw new StringNotClosedException();
      }
      localChar = buffer[localPos];
    }

    String tokenValue = stringTokenWithEscapesBuilder == null
      ? new String(buffer, start, localPos - start)
      : stringTokenWithEscapesBuilder.toString();

    localPos++;
    textIndex = localPos;
    currentChar = buffer[localPos];

    return new Token(TokenType.STRING, tokenValue);
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

  private void advanceKeywordTrue() {
    advance();
    if (currentChar != 'r') throw new InvalidKeywordException("true", "t", currentChar);
    advance();
    if (currentChar != 'u') throw new InvalidKeywordException("true", "tr", currentChar);
    advance();
    if (currentChar != 'e') throw new InvalidKeywordException("true", "tru", currentChar);
    advance();
  }

  private void advanceKeywordNull() {
    advance();
    if (currentChar != 'u') throw new InvalidKeywordException("null", "n", currentChar);
    advance();
    if (currentChar != 'l') throw new InvalidKeywordException("null", "nu", currentChar);
    advance();
    if (currentChar != 'l') throw new InvalidKeywordException("null", "nul", currentChar);
    advance();
  }

  private void advanceKeywordFalse() {
    advance();
    if (currentChar != 'a') throw new InvalidKeywordException("false", "f", currentChar);
    advance();
    if (currentChar != 'l') throw new InvalidKeywordException("false", "fa", currentChar);
    advance();
    if (currentChar != 's') throw new InvalidKeywordException("false", "fal", currentChar);
    advance();
    if (currentChar != 'e') throw new InvalidKeywordException("false", "fals", currentChar);
    advance();
  }

  private int advanceEscapeCharacter(char[] buffer, int pos, StringBuilder stringBuilder) {
    int localPos = pos + 1;
    currentChar = buffer[localPos];

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
        localPos++;

        if (localPos + 4 > buffer.length) {
          throw new InvalidControlCharacterException();
        }

        try {
          int codePoint = Integer.parseInt(new String(buffer, localPos, 4), 16);
          localPos += 3;
          yield (char) codePoint;
        } catch (NumberFormatException ex) {
          throw new InvalidControlCharacterException();
        }
      }
      default -> throw new InvalidControlCharacterException();
    };

    stringBuilder.append(result);
    return localPos;
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

  private void advance() {
    textIndex++;
    currentChar = textIndex < characters.length ? characters[textIndex] : null;
  }
}
