package technology.sola.json.exception;

public class InvalidNegativeNumberException extends RuntimeException {
  private final int startIndex;

  public InvalidNegativeNumberException(int startIndex) {
    super("Negative number expected following '-' at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
