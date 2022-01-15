package technology.sola.json.exception;

public class PropertyNotFoundException extends RuntimeException {
  public PropertyNotFoundException(String key) {
    super("Property with key [" + key + "] was not found");
  }
}
