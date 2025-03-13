package technology.sola.json.tokenizer.exception;

import technology.sola.json.exception.SolaJsonParsingError;

/**
 * Exception thrown when an invalid decimal number is found during tokenization.
 */
public class InvalidDecimalNumberException extends RuntimeException implements SolaJsonParsingError {
  private transient final int line;
  private transient final int column;

  /**
   * Creates a new instance of this exception.
   *
   * @param line the line where the invalid character was found
   * @param column the column where the invalid character was found
   */
  public InvalidDecimalNumberException(int line, int column) {
    super("Number following decimal expected starting at [" + line + ":" + column + "]");
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
}
