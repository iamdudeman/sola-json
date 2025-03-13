package technology.sola.json.exception;

/**
 * SolaJsonError contains general information about an error that happened while parsing JSON.
 */
public interface SolaJsonError {
  /**
   * @return the line where the syntax error began
   */
  int getLine();

  /**
   * @return the column where the syntax error began
   */
  int getColumn();
}
