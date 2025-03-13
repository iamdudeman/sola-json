package technology.sola.json.tokenizer.exception;

import technology.sola.json.exception.SolaJsonParsingError;

/**
 * Exception for when an invalid keyword is found during tokenization.
 */
public class InvalidKeywordException extends RuntimeException implements SolaJsonParsingError {
  private transient final String expected;
  private transient final String actual;
  private transient final int line;
  private transient final int column;

  /**
   * Creates a new instance of this exception.
   *
   * @param keyword     the expected keyword
   * @param current     the keyword currently read
   * @param invalidChar the invalid character for the expected keyword
   * @param line        line where the invalid character was found
   * @param column      column where the invalid character was found
   */
  public InvalidKeywordException(String keyword, String current, char invalidChar, int line, int column) {
    super("Expected keyword [" + keyword + "] but received [" + current + invalidChar + "] at [" + line + ":" + column + "]");
    this.expected = keyword;
    this.actual = current + invalidChar;
    this.line = line;
    this.column = column;
  }

  @Override
  public int getLine() {
    return line;
  }

  @Override
  public int getColumn() {
    return column;
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
}
