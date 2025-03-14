package technology.sola.json.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import technology.sola.json.parser.exception.InvalidSyntaxException;
import technology.sola.json.tokenizer.TokenType;
import technology.sola.json.tokenizer.JsonTokenizer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
  @Test
  void whenInvalidRoot_shouldThrowException() {
    String input = " \"test\" ";

    InvalidSyntaxException invalidSyntaxException = assertThrows(InvalidSyntaxException.class, () -> createTest(input));
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

    InvalidSyntaxException invalidSyntaxException = assertThrows(InvalidSyntaxException.class, () -> createTest(input));
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

        createTest(input)
          .assertCurrentNode(AstNodeType.OBJECT)
          .child(0).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "test")
          .child(0, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.STRING, "value")
          ;
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

        createTest(input)
          .assertCurrentNode(AstNodeType.OBJECT)
          .child(0).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "test")
          .child(0, 0).assertCurrentNode(AstNodeType.OBJECT)
          .child(0, 0, 0).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "test2")
          .child(0, 0, 0, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.STRING, "value")
        ;
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

        createTest(input)
          .assertCurrentNode(AstNodeType.OBJECT)
          .child(0).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "string")
          .child(0, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.STRING, "test")
          .child(1).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "object")
          .child(1, 0).assertCurrentNode(AstNodeType.OBJECT)
          .child(2).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "array")
          .child(2, 0).assertCurrentNode(AstNodeType.ARRAY)
          .child(3).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "true")
          .child(3, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.TRUE)
          .child(4).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "false")
          .child(4, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.FALSE)
          .child(5).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "null")
          .child(5, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.NULL)
          .child(6).assertCurrentNode(AstNodeType.PAIR, TokenType.STRING, "number")
          .child(6, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.NUMBER, "-2.3e2")
          ;
      }
    }

    @Nested
    class rootArray {
      @Test
      void shouldHandleEmptyArray() {
        String input = """
          []
          """;

        createTest(input).assertCurrentNode(AstNodeType.ARRAY);
      }

      @Test
      void shouldHandleBasicArray() {
        String input = """
            [true, null, false]
            """;

        createTest(input)
          .assertCurrentNode(AstNodeType.ARRAY)
          .child(0).assertCurrentNode(AstNodeType.VALUE, TokenType.TRUE)
          .child(1).assertCurrentNode(AstNodeType.VALUE, TokenType.NULL)
          .child(2).assertCurrentNode(AstNodeType.VALUE, TokenType.FALSE)
          ;
      }

      @Test
      void shouldHandleNestedArray() {
        String input = """
            [true, [false]]
            """;

        createTest(input)
          .assertCurrentNode(AstNodeType.ARRAY)
          .child(0).assertCurrentNode(AstNodeType.VALUE, TokenType.TRUE)
          .child(1).assertCurrentNode(AstNodeType.ARRAY)
          .child(1, 0).assertCurrentNode(AstNodeType.VALUE, TokenType.FALSE)
          ;
      }

      @Test
      void shouldHandleArrayWithAllTypes() {
        String input = """
            ["testString", {}, [], true, false, null, -2.3e2]
            """;

        createTest(input)
          .assertCurrentNode(AstNodeType.ARRAY)
          .child(0).assertCurrentNode(AstNodeType.VALUE, TokenType.STRING, "testString")
          .child(1).assertCurrentNode(AstNodeType.OBJECT)
          .child(2).assertCurrentNode(AstNodeType.ARRAY)
          .child(3).assertCurrentNode(AstNodeType.VALUE, TokenType.TRUE)
          .child(4).assertCurrentNode(AstNodeType.VALUE, TokenType.FALSE)
          .child(5).assertCurrentNode(AstNodeType.VALUE, TokenType.NULL)
          .child(6).assertCurrentNode(AstNodeType.VALUE, TokenType.NUMBER, "-2.3e2")
        ;
      }
    }
  }

  private AstTester createTest(String input) {
    JsonTokenizer jsonTokenizer = new JsonTokenizer(input);
    JsonParser jsonParser = new JsonParser(jsonTokenizer);

    return new AstTester(jsonParser.parse());
  }

  private static class AstTester {
    private final AstNode root;
    private AstNode currentNode;

    public AstTester(AstNode root) {
      this.root = root;
      this.currentNode = root;
    }

    public AstTester child(int... pathToChild) {
      currentNode = root;

      for (int childIndex : pathToChild) {
        currentNode = currentNode.children()[childIndex];
      }

      return this;
    }

    public AstTester assertCurrentNode(AstNodeType expectedNodeType) {
      return assertCurrentNode(expectedNodeType, null, null);
    }

    public AstTester assertCurrentNode(AstNodeType expectedNodeType, TokenType expectedTokenType) {
      return assertCurrentNode(expectedNodeType, expectedTokenType, null);
    }

    public AstTester assertCurrentNode(AstNodeType expectedNodeType, TokenType expectedTokenType, String expectedValue) {
      Assertions.assertEquals(expectedNodeType, currentNode.type(), "" + currentNode);

      if (expectedTokenType != null) {
        Assertions.assertEquals(expectedTokenType, currentNode.token().type(), "" + currentNode.token());
      }

      if (expectedValue != null) {
        Assertions.assertEquals(expectedValue, currentNode.token().value(), "" + currentNode.token());
      }

      return this;
    }
  }
}
