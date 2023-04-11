package technology.sola.json.exception;

/**
 * Exception for when an invalid character is found during tokenization.
 */
public class InvalidCharacterException extends RuntimeException {
  /**
   * The character that is invalid.
   */
  private final char invalidCharacter;
  /**
   * Index where the error was found.
   */
  private final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param invalidCharacter the invalid character
   * @param startIndex       index where the invalid character was found
   */
  public InvalidCharacterException(char invalidCharacter, int startIndex) {
    super(String.format("Invalid character [%s] at [%s]", invalidCharacter, startIndex));

    this.invalidCharacter = invalidCharacter;
    this.startIndex = startIndex;
  }

  /**
   * @return the invalid character
   */
  public char getInvalidCharacter() {
    return invalidCharacter;
  }

  /**
   * @return index where the invalid character was found
   */
  public int getStartIndex() {
    return startIndex;
  }
}
