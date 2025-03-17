package technology.sola.json.parser;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import technology.sola.json.JsonArray;
import technology.sola.json.JsonElement;
import technology.sola.json.JsonObject;
import technology.sola.json.parser.exception.InvalidSyntaxException;
import technology.sola.json.tokenizer.TokenType;
import technology.sola.json.tokenizer.JsonTokenizer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
  @Test
  void whenInvalidRoot_shouldThrowException() {
    String input = " \"test\" ";

    InvalidSyntaxException invalidSyntaxException = assertThrows(InvalidSyntaxException.class, () -> parse(input));
    assertEquals(2, invalidSyntaxException.getColumn());
    assertEquals(TokenType.STRING, invalidSyntaxException.getActual());
    var expectedList = List.of(invalidSyntaxException.getExpected());
    assertTrue(expectedList.contains(TokenType.L_BRACKET));
    assertTrue(expectedList.contains(TokenType.L_CURLY));
  }

  @Test
  void whenInvalidValue_shouldThrowException() {
    String input = """
      {
        "key": }
      }
      """;

    InvalidSyntaxException invalidSyntaxException = assertThrows(InvalidSyntaxException.class, () -> parse(input));
    assertEquals(2, invalidSyntaxException.getLine());
    assertEquals(10, invalidSyntaxException.getColumn());
    assertEquals(TokenType.R_CURLY, invalidSyntaxException.getActual());
    var expectedList = List.of(invalidSyntaxException.getExpected());
    assertTrue(expectedList.contains(TokenType.L_BRACKET));
    assertTrue(expectedList.contains(TokenType.L_CURLY));
    assertTrue(expectedList.contains(TokenType.TRUE));
    assertTrue(expectedList.contains(TokenType.FALSE));
    assertTrue(expectedList.contains(TokenType.NULL));
    assertTrue(expectedList.contains(TokenType.STRING));
    assertTrue(expectedList.contains(TokenType.NUMBER));
  }

  @Nested
  class parse {
    @Nested
    class rootObject {
      @Test
      void shouldHandleSinglePair() {
        String input = """
          {
            "test": "value"
          }
          """;

        JsonObject result = parse(input).asObject();

        assertEquals("value", result.getString("test"));
      }

      @Test
      void shouldHandleNestedObjects() {
        String input = """
          {
            "test": {
              "test2": "value"
            }
          }
          """;

        JsonObject result = parse(input).asObject();

        assertEquals("value", result.getObject("test").getString("test2"));
      }

      @Test
      void shouldHandlePairsOfAllValueTypes() {
        String input = """
          {
            "string": "test",
            "object": {},
            "array": [],
            "true": true,
            "false": false,
            "null": null,
            "number": -2.3e2
          }
          """;

        JsonObject result = parse(input).asObject();

        assertEquals("test", result.getString("string"));
        assertEquals(new JsonObject(), result.getObject("object"));
        assertEquals(new JsonArray(), result.getArray("array"));
        assertTrue(result.getBoolean("true"));
        assertFalse(result.getBoolean("false"));
        assertTrue(result.isNull("null"));
        assertEquals(-2.3e2f, result.getFloat("number"));
      }
    }

    @Nested
    class rootArray {
      @Test
      void shouldHandleEmptyArray() {
        String input = """
          []
          """;

        JsonArray result = parse(input).asArray();

        assertEquals(0, result.size());
      }

      @Test
      void shouldHandleBasicArray() {
        String input = """
            [true, null, false]
            """;

        JsonArray result = parse(input).asArray();

        assertTrue(result.getBoolean(0));
        assertTrue(result.isNull(1));
        assertFalse(result.getBoolean(2));
      }

      @Test
      void shouldHandleNestedArray() {
        String input = """
            [true, [false]]
            """;

        JsonArray result = parse(input).asArray();

        assertTrue(result.getBoolean(0));
        assertFalse(result.getArray(1).getBoolean(0));
      }

      @Test
      void shouldHandleArrayWithAllTypes() {
        String input = """
            ["testString", {}, [], true, false, null, -2.3e2]
            """;

        JsonArray result = parse(input).asArray();

        assertEquals("testString", result.getString(0));
        assertEquals(new JsonObject(), result.getObject(1));
        assertEquals(new JsonArray(), result.getArray(2));
        assertTrue(result.getBoolean(3));
        assertFalse(result.getBoolean(4));
        assertTrue(result.isNull(5));
        assertEquals(-2.3e2f, result.getFloat(6));
      }
    }
  }

  private JsonElement parse(String input) {
    JsonTokenizer jsonTokenizer = new JsonTokenizer(input);
    JsonParser jsonParser = new JsonParser(jsonTokenizer);

    return jsonParser.parse();
  }
}
