package technology.sola.json.exception;

/**
 * Exception thrown whenever a JSON AST is not in a valid state.
 */
public class InvalidAbstractSyntaxTreeException extends RuntimeException implements SolaJsonError {
  @Override
  public int getLine() {
    // todo
    throw new RuntimeException("not yet implemented");
  }

  @Override
  public int getColumn() {
    // todo
    throw new RuntimeException("not yet implemented");
  }
}
