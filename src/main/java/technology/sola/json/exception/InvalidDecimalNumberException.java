package technology.sola.json.exception;

/**
 * Exception thrown when an invalid decimal number is found during tokenization.
 */
public class InvalidDecimalNumberException extends RuntimeException implements SolaJsonError {
  /**
   * Index where the error was found.
   */
  private final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex the index where the invalid character was found
   */
  public InvalidDecimalNumberException(int startIndex) {
    super("Number for decimal expected starting at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  /**
   * @return index where the invalid character was found
   */
  public int getStartIndex() {
    return startIndex;
  }
}
