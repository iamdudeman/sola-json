package technology.sola.json;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {
  @Test
  void test() {
    String input = """
      [true, null, false]
      """;

    createTest(input)
      .assertCurrentNode(AstNodeType.ARRAY)
      .child(0).assertCurrentNode(AstNodeType.VALUE, TokenType.TRUE, "true")
      .child(1).assertCurrentNode(AstNodeType.VALUE, TokenType.NULL, "null")
      .child(2).assertCurrentNode(AstNodeType.VALUE, TokenType.FALSE, "false")
    ;
  }

  private AstTester createTest(String input) {
    Tokenizer tokenizer = new Tokenizer(input);
    Parser parser = new Parser(tokenizer);

    return new AstTester(parser.parse());
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
      Assertions.assertEquals(expectedNodeType, currentNode.type(), "" + currentNode.token());

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
