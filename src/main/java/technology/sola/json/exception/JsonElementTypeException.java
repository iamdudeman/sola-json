package technology.sola.json.exception;

/**
 * Exception thrown when {@link technology.sola.json.JsonElement} is used in an unexpected way.
 */
public class JsonElementTypeException extends RuntimeException implements SolaJsonError {
  /**
   * Creates a new instance of this exception.
   *
   * @param desiredType {@link technology.sola.json.JsonElementType} attempted to use as
   * @param actualType  actual {@code JsonElementType} for this JsonElement
   */
  public JsonElementTypeException(String desiredType, String actualType) {
    super(String.format("JsonElement type is [%s] but attempted to use as [%s]", actualType, desiredType));
  }
}
