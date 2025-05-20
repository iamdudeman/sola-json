package technology.sola.json.tokenizer.exception;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.exception.SolaJsonParsingError;

/**
 * Exception for when an invalid character is found during tokenization.
 */
@NullMarked
public class InvalidCharacterException extends RuntimeException implements SolaJsonParsingError {
  private transient final char invalidCharacter;
  private transient final int line;
  private transient final int column;

  /**
   * Creates a new instance of this exception.
   *
   * @param invalidCharacter the invalid character
   * @param line       line where the invalid character was found
   * @param column       column where the invalid character was found
   */
  public InvalidCharacterException(char invalidCharacter, int line, int column) {
    super(String.format("Invalid character [%s] at [%s:%s]", invalidCharacter, line, column));

    this.invalidCharacter = invalidCharacter;
    this.line = line;
    this.column = column;
  }

  /**
   * @return the invalid character
   */
  public char getInvalidCharacter() {
    return invalidCharacter;
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
