package technology.sola.json.exception;

public class JsonElementTypeException extends RuntimeException {
  public JsonElementTypeException(String desiredType, String actualType) {
    super(String.format("JsonElement type is [%s] but attempted to use as [%s]", actualType, desiredType));
  }
}
