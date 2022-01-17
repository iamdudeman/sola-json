package technology.sola.json;

import org.junit.jupiter.api.Test;
import technology.sola.json.exception.JsonElementTypeException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonElementTest {
  @Test
  void whenAccessingIncorrectType_shouldThrowException() {
    JsonElement jsonElement = new JsonElement();

    assertThrows(JsonElementTypeException.class, jsonElement::asArray);
  }
}
