package technology.sola.json.exception;

/**
 * SolaJsonErrorWithLineAndColumn contains general information about an error that happened during JSON operations.
 */
public interface SolaJsonErrorWithLineAndColumn {
  /**
   * @return the line where the syntax error began
   */
  int getLine();

  /**
   * @return the column where the syntax error began
   */
  int getColumn();
}
