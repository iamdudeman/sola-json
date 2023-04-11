package technology.sola.json.exception;

/**
 * Exception thrown when a string was discovered to be not closed during tokenization.
 */
public class StringNotClosedException extends RuntimeException {
  /**
   * Index where the error was found.
   */
  private final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex index where the unclosed string was started
   */
  public StringNotClosedException(int startIndex) {
    super("String starting at [" + startIndex + "] not closed");

    this.startIndex = startIndex;
  }

  /**
   * @return index where the unclosed string was started
   */
  public int getStartIndex() {
    return startIndex;
  }
}
