package technology.sola.json.exception;

public class InvalidKeywordException extends RuntimeException {
  public InvalidKeywordException(String keyword, String current, char invalidChar) {
    super("Expected keyword [" + keyword + "] but have [" + current + invalidChar + "]");
  }
}
