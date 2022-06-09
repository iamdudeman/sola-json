package technology.sola.json.exception;

public class InvalidCharacterException extends RuntimeException {
  private final char invalidCharacter;
  private final int startIndex;

  public InvalidCharacterException(char invalidCharacter, int startIndex) {
    super(String.format("Invalid character [%s] at [%s]", invalidCharacter, startIndex));

    this.invalidCharacter = invalidCharacter;
    this.startIndex = startIndex;
  }

  public char getInvalidCharacter() {
    return invalidCharacter;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
