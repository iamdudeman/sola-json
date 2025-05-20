package technology.sola.json.tokenizer.exception;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.exception.SolaJsonParsingError;

/**
 * Exception thrown when an invalid control character is found during tokenization.
 */
@NullMarked
public class InvalidControlCharacterException extends RuntimeException implements SolaJsonParsingError {
  private transient final int line;
  private transient final int column;

  /**
   * Creates a new instance of this exception.
   *
   * @param line   the line where the invalid character was found
   * @param column the column where the invalid character was found
   */
  public InvalidControlCharacterException(int line, int column) {
    super("Invalid control character at [" + line + ":" + column + "]");
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
