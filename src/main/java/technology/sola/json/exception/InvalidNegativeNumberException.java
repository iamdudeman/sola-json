package technology.sola.json.exception;

/**
 * Exception thrown when an invalid negative number is found during tokenization.
 */
public class InvalidNegativeNumberException extends RuntimeException implements SolaJsonError {
  private transient final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex the index where the invalid character was found
   */
  public InvalidNegativeNumberException(int startIndex) {
    super("Negative number expected following '-' at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  @Override
  public int getLine() {
    // todo
    throw new RuntimeException("not yet implemented");
  }

  @Override
  public int getColumn() {
    // todo
    throw new RuntimeException("not yet implemented");
  }

  /**
   * @return index where the invalid character was found
   */
  public int getStartIndex() {
    return startIndex;
  }
}
