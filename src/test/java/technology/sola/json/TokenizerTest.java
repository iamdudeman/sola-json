package technology.sola.json;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
      var input = " : ";

      createTest(input)
        .assertNextToken(TokenType.COLON)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeComma() {
      var input = " , ";

      createTest(input)
        .assertNextToken(TokenType.COMMA)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeCurly() {
      var input = " { } ";

      createTest(input)
        .assertNextToken(TokenType.L_CURLY)
        .assertNextToken(TokenType.R_CURLY)
        .assertNextToken(TokenType.EOF);
    }

    @Test
    void shouldRecognizeBracket() {
      var input = " [ ] ";

      createTest(input)
        .assertNextToken(TokenType.L_BRACKET)
        .assertNextToken(TokenType.R_BRACKET)
        .assertNextToken(TokenType.EOF);
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
