package technology.sola.json.exception;

public class InvalidDecimalNumberException extends RuntimeException {
  private final int startIndex;

  public InvalidDecimalNumberException(int startIndex) {
    super("Number for decimal expected starting at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
