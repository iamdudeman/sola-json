package technology.sola.json.tokenizer;

import technology.sola.json.tokenizer.exception.*;

/**
 * A JSON tokenizer implementation.
 */
public class JsonTokenizer {
  private final char[] characters;
  private Character currentChar;
  private int textIndex;
  private int column = 1;
  private int line = 1;

  /**
   * Creates a {@link JsonTokenizer} for the specified string.
   *
   * @param text the string to tokenize
   */
  public JsonTokenizer(String text) {
    characters = text.toCharArray();
    currentChar = characters[textIndex];
  }

  /**
   * @return finds and returns the next valid token in the string
   */
  public Token getNextToken() {
    int line = this.line;
    int column = this.column;

    if (currentChar == null) {
      return new Token(TokenType.EOF, line, column);
    }

    if (currentChar == '\n') {
      this.line++;
      this.column = 1;
      advance();
      return getNextToken();
    }

    if (Character.isWhitespace(currentChar)) {
      advance();
      return getNextToken();
    }

    if (currentChar == ':') {
      advance();
      return new Token(TokenType.COLON, line, column);
    }

    if (currentChar == ',') {
      advance();
      return new Token(TokenType.COMMA, line, column);
    }

    if (currentChar == '[') {
      advance();
      return new Token(TokenType.L_BRACKET, line, column);
    }

    if (currentChar == ']') {
      advance();
      return new Token(TokenType.R_BRACKET, line, column);
    }

    if (currentChar == '{') {
      advance();
      return new Token(TokenType.L_CURLY, line, column);
    }

    if (currentChar == '}') {
      advance();
      return new Token(TokenType.R_CURLY, line, column);
    }

    if (currentChar == '"') {
      return tokenString();
    }

    if (currentChar == '-' || isDigit(currentChar)) {
      return tokenNumber();
    }

    if (currentChar == 't') {
      return tokenTrue();
    }

    if (currentChar == 'f') {
      return tokenFalse();
    }

    if (currentChar == 'n') {
      return tokenNull();
    }

    throw new InvalidCharacterException(currentChar, line, column);
  }

  private Token tokenTrue() {
    int line = this.line;
    int column = this.column;

    advance();
    if (currentChar != 'r') throw new InvalidKeywordException("true", "t", currentChar, line, column);
    advance();
    if (currentChar != 'u') throw new InvalidKeywordException("true", "tr", currentChar, line, column);
    advance();
    if (currentChar != 'e') throw new InvalidKeywordException("true", "tru", currentChar, line, column);
    advance();

    return new Token(TokenType.TRUE, line, column);
  }

  private Token tokenNull() {
    int line = this.line;
    int column = this.column;
    advance();
    if (currentChar != 'u') throw new InvalidKeywordException("null", "n", currentChar, line, column);
    advance();
    if (currentChar != 'l') throw new InvalidKeywordException("null", "nu", currentChar, line, column);
    advance();
    if (currentChar != 'l') throw new InvalidKeywordException("null", "nul", currentChar, line, column);
    advance();

    return new Token(TokenType.NULL, line, column);
  }

  private Token tokenFalse() {
    int line = this.line;
    int column = this.column;
    advance();
    if (currentChar != 'a') throw new InvalidKeywordException("false", "f", currentChar, line, column);
    advance();
    if (currentChar != 'l') throw new InvalidKeywordException("false", "fa", currentChar, line, column);
    advance();
    if (currentChar != 's') throw new InvalidKeywordException("false", "fal", currentChar, line, column);
    advance();
    if (currentChar != 'e') throw new InvalidKeywordException("false", "fals", currentChar, line, column);
    advance();

    return new Token(TokenType.FALSE, line, column);
  }

  private Token tokenString() {
    int initialColumn = column;
    // StringBuilder required if escaped character is present
    StringBuilder stringTokenWithEscapesBuilder = null;

    // These local fields save a lot of time from field lookups
    char[] buffer = characters;
    int localPos = textIndex + 1;
    char localChar = buffer[localPos];
    int start = localPos;

    while (localChar != '\"') {
      if (localChar == '\t' || localChar == '\n') {
        throw new InvalidUnicodeCharacterException(line, initialColumn);
      }

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
        throw new StringNotClosedException(line, initialColumn);
      }
      localChar = buffer[localPos];
    }

    String tokenValue = stringTokenWithEscapesBuilder == null
      ? new String(buffer, start, localPos - start)
      : stringTokenWithEscapesBuilder.toString();

    textIndex = localPos;
    column += localPos - start;
    advance();

    return new Token(TokenType.STRING, tokenValue, line, initialColumn);
  }

  private Token tokenNumber() {
    char[] characters = this.characters;
    int startColumn = column;
    int startIndex = textIndex;

    advanceNumber();
    advanceFraction();
    advanceExponent();

    int characterCount = textIndex - startIndex;

    if (characterCount == 1 && characters[startIndex] == '-') {
      throw new InvalidNegativeNumberException(line, startColumn);
    }

    return new Token(TokenType.NUMBER, new String(characters, startIndex, characterCount), line, startColumn);
  }

  private int advanceEscapeCharacter(char[] buffer, int pos, StringBuilder stringBuilder) {
    int localPos = pos + 1;

    if (localPos >= buffer.length) {
      throw new InvalidControlCharacterException(line, column + pos - textIndex);
    }

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
          throw new InvalidUnicodeCharacterException(line, column + pos - textIndex);
        }

        try {
          int codePoint = Integer.parseInt(new String(buffer, localPos, 4), 16);
          localPos += 3;
          yield (char) codePoint;
        } catch (NumberFormatException ex) {
          throw new InvalidUnicodeCharacterException(line, column + pos - textIndex);
        }
      }
      default -> throw new InvalidControlCharacterException(line, column + pos - textIndex);
    };

    stringBuilder.append(result);
    return localPos;
  }

  private void advanceNumber() {
    int startColumn = column;
    boolean hasLeadingZero = currentChar == '0';

    advance();

    while (currentChar != null && isDigit(currentChar)) {
      if (hasLeadingZero) {
        throw new InvalidLeadingZeroNumberException(line, startColumn);
      }

      advance();
    }
  }

  private void advanceFraction() {
    int startColumn = column;
    int startFraction = textIndex;

    if (currentChar != null && currentChar == '.') {
      do {
        advance();
      } while (currentChar != null && isDigit(currentChar));
    }

    if (textIndex - startFraction == 1) {
      throw new InvalidDecimalNumberException(line, startColumn);
    }
  }

  private void advanceExponent() {
    if (currentChar != null && (currentChar == 'e' || currentChar == 'E')) {
      advance();

      if (currentChar == '+' || currentChar == '-') {
        advance();
      }

      if (currentChar == null || !isDigit(currentChar)) {
        throw new InvalidExponentNumberException(line, textIndex);
      }

      while (currentChar != null && isDigit(currentChar)) {
        advance();
      }
    }
  }

  private void advance() {
    char[] characters = this.characters;

    textIndex++;
    column++;
    currentChar = textIndex < characters.length ? characters[textIndex] : null;
  }

  private boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }
}
