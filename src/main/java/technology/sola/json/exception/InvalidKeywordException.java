package technology.sola.json.exception;

/**
 * Exception for when an invalid keyword is found during tokenization.
 */
public class InvalidKeywordException extends RuntimeException implements SolaJsonError {
  /**
   * The expected keyword.
   */
  private final String expected;
  /**
   * The keyword actually seen.
   */
  private final String actual;
  /**
   * Index where the error was found.
   */
  private final int startIndex;

  /**
   * Creates a new instance of this exception.
   *
   * @param keyword     the expected keyword
   * @param current     the keyword currently read
   * @param invalidChar the invalid character for the expected keyword
   * @param startIndex  index where the invalid character was found
   */
  public InvalidKeywordException(String keyword, String current, char invalidChar, int startIndex) {
    super("Expected keyword [" + keyword + "] but received [" + current + invalidChar + "] at [" + startIndex + "]");
    this.expected = keyword;
    this.actual = current + invalidChar;
    this.startIndex = startIndex;
  }

  /**
   * @return the expected keyword
   */
  public String getExpected() {
    return expected;
  }

  /**
   * @return the received keyword
   */
  public String getActual() {
    return actual;
  }

  /**
   * @return index where the invalid character was found
   */
  public int getStartIndex() {
    return startIndex;
  }
}
