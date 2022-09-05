package technology.sola.json.exception;

/**
 * Exception for when an invalid character is found during tokenization.
 */
public class InvalidCharacterException extends RuntimeException {
  private final char invalidCharacter;
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
