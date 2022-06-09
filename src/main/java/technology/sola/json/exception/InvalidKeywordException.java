package technology.sola.json.exception;

public class InvalidKeywordException extends RuntimeException {
  private final String expectedKeyword;
  private final String receivedKeyword;
  private final int startIndex;

  public InvalidKeywordException(String keyword, String current, char invalidChar, int startIndex) {
    super("Expected keyword [" + keyword + "] but received [" + current + invalidChar + "] at [" + startIndex + "]");
    this.expectedKeyword = keyword;
    this.receivedKeyword = current + invalidChar;
    this.startIndex = startIndex;
  }

  public String getExpectedKeyword() {
    return expectedKeyword;
  }

  public String getReceivedKeyword() {
    return receivedKeyword;
  }

  public int getStartIndex() {
    return startIndex;
  }
}
