package technology.sola.json.tokenizer.exception;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.exception.SolaJsonParsingError;

/**
 * Exception thrown when a string was discovered to be not closed during tokenization.
 */
@NullMarked
public class StringNotClosedException extends RuntimeException implements SolaJsonParsingError {
  private transient final int line;
  private transient final int column;

  /**
   * Creates a new instance of this exception.
   *
   * @param line line where the unclosed string was started
   * @param column column where the unclosed string was started
   */
  public StringNotClosedException(int line, int column) {
    super("String starting at [" + line + ":" + column + "] not closed");

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
