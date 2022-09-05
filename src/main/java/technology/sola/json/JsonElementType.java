package technology.sola.json;

/**
 * Represents different types of {@link JsonElement}s.
 */
public enum JsonElementType {
  /**
   * True or false.
   */
  BOOLEAN,
  /**
   * Long or integer.
   */
  LONG,
  /**
   * Decimal number.
   */
  DOUBLE,
  /**
   * Sting of characters.
   */
  STRING,
  /**
   * {@link JsonArray}.
   */
  JSON_ARRAY,
  /**
   * {@link JsonObject}.
   */
  JSON_OBJECT,
  /**
   * {@code null}.
   */
  NULL,
}
