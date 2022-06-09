package technology.sola.json.exception;

public class InvalidUnicodeCharacterException extends RuntimeException {
  private final int startIndex;

  public InvalidUnicodeCharacterException(int startIndex) {
    super("Invalid unicode character must be 4 numbers in length at [" + startIndex + "]");
    this.startIndex = startIndex;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
