package technology.sola.json.exception;

public class InvalidControlCharacterException extends RuntimeException {
  private final int startIndex;

  public InvalidControlCharacterException(int startIndex) {
    super("Invalid control character at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
