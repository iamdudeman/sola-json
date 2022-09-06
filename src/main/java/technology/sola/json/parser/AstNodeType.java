package technology.sola.json.parser;

/**
 * Represents the type for an {@link AstNode}.
 */
public enum AstNodeType {
  /**
   * Represents a JSON array.
   */
  ARRAY,
  /**
   * Represents a JSON Object.
   */
  OBJECT,
  /**
   * Represents a JSON pair.
   */
  PAIR,
  /**
   * Represents a JSON value.
   */
  VALUE,
}
