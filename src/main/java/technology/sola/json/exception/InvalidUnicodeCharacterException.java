package technology.sola.json.exception;

/**
 * Exception thrown when an invalid unicode character is found during tokenization.
 */
public class InvalidUnicodeCharacterException extends RuntimeException implements SolaJsonError {
  private transient final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param startIndex the index where the invalid character was found
   */
  public InvalidUnicodeCharacterException(int startIndex) {
    super("Invalid unicode character must be 4 numbers in length at [" + startIndex + "]");
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
