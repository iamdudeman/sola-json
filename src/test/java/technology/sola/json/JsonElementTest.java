package technology.sola.json;

import org.junit.jupiter.api.Test;
import technology.sola.json.exception.JsonElementTypeException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonElementTest {
  @Test
  void whenAccessingIncorrectType_shouldThrowException() {
    JsonElement jsonElement = new JsonElement();

    assertThrows(JsonElementTypeException.class, jsonElement::asArray);
  }

  @Test
  void toString_whenTypeString_withEscapedCharacters_shouldProperlyEscape() {
    JsonElement jsonElement = new JsonElement("\"\\");

    String result = jsonElement.toString();

    assertEquals("\"\\\"\\\\\"", result);
  }
}
