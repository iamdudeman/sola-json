package technology.sola.json.tokenizer.exception;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.exception.SolaJsonParsingError;

/**
 * Exception thrown when an invalid number starts with a leading zero is found during tokenization.
 */
@NullMarked
public class InvalidLeadingZeroNumberException extends RuntimeException implements SolaJsonParsingError {
  private transient final int line;
  private transient final int column;

  /**
   * Creates a new instance of this exception.
   *
   * @param line   the line where the invalid character was found
   * @param column the column where the invalid character was found
   */
  public InvalidLeadingZeroNumberException(int line, int column) {
    super("Negative number expected following '-' at [" + line + ":" + column + "]");
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
