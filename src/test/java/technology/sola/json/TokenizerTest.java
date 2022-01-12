package technology.sola.json;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import technology.sola.json.exception.StringNotClosedException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenizerTest {
  @Nested
  class getNextToken {
    @Test
    void shouldEndWithEof() {
      var input = "  ";

      createTest(input)
        .assertNextToken(TokenType.EOF)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeColon() {
      var input = " : : ";

      createTest(input)
        .assertNextToken(TokenType.COLON)
        .assertNextToken(TokenType.COLON)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeComma() {
      var input = " , , ";

      createTest(input)
        .assertNextToken(TokenType.COMMA)
        .assertNextToken(TokenType.COMMA)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeCurly() {
      var input = " { { } } ";

      createTest(input)
        .assertNextToken(TokenType.L_CURLY)
        .assertNextToken(TokenType.L_CURLY)
        .assertNextToken(TokenType.R_CURLY)
        .assertNextToken(TokenType.R_CURLY)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeBracket() {
      var input = " [ [ ] ] ";

      createTest(input)
        .assertNextToken(TokenType.L_BRACKET)
        .assertNextToken(TokenType.L_BRACKET)
        .assertNextToken(TokenType.R_BRACKET)
        .assertNextToken(TokenType.R_BRACKET)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeTrue() {
      var input = " true true ";

      createTest(input)
        .assertNextToken(TokenType.TRUE)
        .assertNextToken(TokenType.TRUE)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeFalse() {
      var input = " false false ";

      createTest(input)
        .assertNextToken(TokenType.FALSE)
        .assertNextToken(TokenType.FALSE)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeNull() {
      var input = " null null ";

      createTest(input)
        .assertNextToken(TokenType.NULL)
        .assertNextToken(TokenType.NULL)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeString() {
      var input = " \"test_string\" \"test\" ";

      createTest(input)
        .assertNextToken(TokenType.STRING, "test_string")
        .assertNextToken(TokenType.STRING, "test");
    }

    @Test
    void whenStringNotClosed_shouldThrowException() {
      var input = " \"test ";

      assertThrows(StringNotClosedException.class, () -> createTest(input).assertNextToken(TokenType.STRING));
    }
  }

  private TokenizerTester createTest(String input) {
    Tokenizer tokenizer = new Tokenizer(input);

    return new TokenizerTester(tokenizer);
  }

  private static class TokenizerTester {
    private final Tokenizer tokenizer;

    public TokenizerTester(Tokenizer tokenizer) {
      this.tokenizer = tokenizer;
    }

    TokenizerTester assertNextToken(TokenType expectedType) {
      assertNextToken(expectedType, null);

      return this;
    }

    TokenizerTester assertNextToken(TokenType expectedType, String expectedValue) {
      Token token = tokenizer.getNextToken();
      assertEquals(expectedType, token.type(), token::toString);

      if (expectedValue != null) {
        assertEquals(expectedValue, token.value(), token::toString);
      }

      return this;
    }
  }
}
