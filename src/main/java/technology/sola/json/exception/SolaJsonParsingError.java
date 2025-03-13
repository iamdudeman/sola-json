package technology.sola.json.exception;

/**
 * SolaJsonParsingError contains general information about an error that happened while parsing JSON.
 */
public interface SolaJsonParsingError {
  /**
   * @return the line where the syntax error began
   */
  int getLine();

  /**
   * @return the column where the syntax error began
   */
  int getColumn();
}
