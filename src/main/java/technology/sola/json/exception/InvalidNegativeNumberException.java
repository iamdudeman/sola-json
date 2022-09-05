package technology.sola.json.exception;

/**
 * Exception thrown when an invalid negative number is found during tokenization.
 */
public class InvalidNegativeNumberException extends RuntimeException {
  private final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex the index where the invalid character was found
   */
  public InvalidNegativeNumberException(int startIndex) {
    super("Negative number expected following '-' at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  /**
   * @return index where the invalid character was found
   */
  public int getStartIndex() {
    return startIndex;
  }
}
