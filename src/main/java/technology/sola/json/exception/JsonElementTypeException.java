package technology.sola.json.exception;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.JsonElementType;

/**
 * Exception thrown when {@link technology.sola.json.JsonElement} is used in an unexpected way.
 */
@NullMarked
public class JsonElementTypeException extends RuntimeException {
  /**
   * Creates a new instance of this exception.
   *
   * @param desiredType {@link technology.sola.json.JsonElementType} attempted to use as
   * @param actualType  actual {@code JsonElementType} for this JsonElement
   */
  public JsonElementTypeException(JsonElementType desiredType, JsonElementType actualType) {
    super(String.format("JsonElement type is [%s] but attempted to use as [%s]", actualType.name(), desiredType.name()));
  }
}
