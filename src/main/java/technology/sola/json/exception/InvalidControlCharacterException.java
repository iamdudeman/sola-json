package technology.sola.json.exception;

/**
 * Exception thrown when an invalid control character is found during tokenization.
 */
public class InvalidControlCharacterException extends RuntimeException {
  private final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex the index where the invalid character was found
   */
  public InvalidControlCharacterException(int startIndex) {
    super("Invalid control character at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  /**
   * @return index where the invalid character was found
   */
  public int getStartIndex() {
    return startIndex;
  }
}
