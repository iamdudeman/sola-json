package technology.sola.json.exception;

public class InvalidCharacterException extends RuntimeException {
  public InvalidCharacterException(char invalidCharacter) {
    super(String.format("Invalid character [%s]", invalidCharacter));
  }
}
