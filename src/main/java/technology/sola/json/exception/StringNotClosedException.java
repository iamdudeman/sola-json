package technology.sola.json.exception;

public class StringNotClosedException extends RuntimeException {
  private final int startIndex;

  public StringNotClosedException(int startIndex) {
    super("String starting at [" + startIndex + "] not closed");

    this.startIndex = startIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
