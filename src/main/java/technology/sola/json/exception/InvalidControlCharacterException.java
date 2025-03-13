package technology.sola.json.exception;

/**
 * Exception thrown when an invalid control character is found during tokenization.
 */
public class InvalidControlCharacterException extends RuntimeException implements SolaJsonError {
  private transient final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex the index where the invalid character was found
   */
  public InvalidControlCharacterException(int startIndex) {
    super("Invalid control character at [" + startIndex + "]");
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
