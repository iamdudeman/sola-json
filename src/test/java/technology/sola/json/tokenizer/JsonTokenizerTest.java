package technology.sola.json.tokenizer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import technology.sola.json.exception.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonTokenizerTest {
  @Nested
  class getNextToken {
    @Test
    void whenInvalidCharacter_shouldThrowException() {
      var input = " invalid ";

      InvalidCharacterException exception = assertThrows(InvalidCharacterException.class, () -> new JsonTokenizer(input).getNextToken());

      assertEquals('i', exception.getInvalidCharacter());
      assertEquals(1, exception.getStartIndex());
    }

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

    @Nested
    class keyword {
      @Test
      void shouldRecognizeTrue() {
        var input = " true true ";

        createTest(input)
          .assertNextToken(TokenType.TRUE)
          .assertNextToken(TokenType.TRUE)
          .assertNextToken(TokenType.EOF);
      }

      @Test
      void whenInvalidTrue_shouldThrowException() {
        var input = " tru ";

        InvalidKeywordException exception = assertThrows(InvalidKeywordException.class, () -> new JsonTokenizer(input).getNextToken());
        assertEquals("true", exception.getExpected());
        assertEquals("tru ", exception.getActual());
        assertEquals(1, exception.getStartIndex());
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
      void whenInvalidFalse_shouldThrowException() {
        var input = " fals ";

        InvalidKeywordException exception = assertThrows(InvalidKeywordException.class, () -> new JsonTokenizer(input).getNextToken());
        assertEquals("false", exception.getExpected());
        assertEquals("fals ", exception.getActual());
        assertEquals(1, exception.getStartIndex());
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
      void whenInvalidNull_shouldThrowException() {
        var input = " nul ";

        InvalidKeywordException exception = assertThrows(InvalidKeywordException.class, () -> new JsonTokenizer(input).getNextToken());
        assertEquals("null", exception.getExpected());
        assertEquals("nul ", exception.getActual());
        assertEquals(1, exception.getStartIndex());
      }
    }

    @Nested
    class string {
      @Test
      void whenValid_shouldRecognize() {
        var input = " \"test_string\" \"test\"";

        createTest(input)
          .assertNextToken(TokenType.STRING, "test_string")
          .assertNextToken(TokenType.STRING, "test")
          .assertNextToken(TokenType.EOF);
      }

      @Test
      void whenNotClosed_shouldThrowException() {
        var input = " \"test ";

        StringNotClosedException exception = assertThrows(StringNotClosedException.class, () -> createTest(input).assertNextToken(TokenType.STRING));

        assertEquals(2, exception.getStartIndex());
      }

      @Nested
      class withControlCharacters {
        @Test
        void whenControlCharacterNotFinished_shouldThrowException() {
          var input = """
            " \\ "
            """;

          InvalidControlCharacterException exception = assertThrows(
            InvalidControlCharacterException.class,
            () -> createTest(input).assertNextToken(TokenType.STRING)
          );
          assertEquals(3, exception.getStartIndex());
        }

        @Test
        void whenControlCharacterNotFinishedAtEndOfString_shouldThrowException() {
          var input = "\"\\";

          InvalidControlCharacterException exception = assertThrows(
            InvalidControlCharacterException.class,
            () -> createTest(input).assertNextToken(TokenType.STRING)
          );
          assertEquals(2, exception.getStartIndex());
        }

        @Test
        void whenInvalidUnicode_shouldThrowException() {
          var input = """
            "\\u12r3"
            """;

          InvalidUnicodeCharacterException exception = assertThrows(
            InvalidUnicodeCharacterException.class,
            () -> createTest(input).assertNextToken(TokenType.STRING)
          );
          assertEquals(3, exception.getStartIndex());
        }

        @Test
        void whenIncompleteUnicode_shouldThrowException() {
          var input = """
            "\\u12"
            """;

          InvalidUnicodeCharacterException exception = assertThrows(
            InvalidUnicodeCharacterException.class,
            () -> createTest(input).assertNextToken(TokenType.STRING)
          );
          assertEquals(3, exception.getStartIndex());
        }

        @Test
        void whenEscapedQuote_shouldRecognize() {
          var input = " \"te\\\"st\" \"te\\\"st\"  ";

          createTest(input)
            .assertNextToken(TokenType.STRING, "te\"st")
            .assertNextToken(TokenType.STRING, "te\"st")
            .assertNextToken(TokenType.EOF);
        }

        @Test
        void whenNonUnicodeControlCharacter_shouldRecognize() {
          var input = """
            "\\" \\/ \\\\ \\b \\f \\n \\r \\t"
            "\\" \\/ \\\\ \\b \\f \\n \\r \\t"
            """;

          createTest(input)
            .assertNextToken(TokenType.STRING, "\" / \\ \b \f \n \r \t")
            .assertNextToken(TokenType.STRING, "\" / \\ \b \f \n \r \t")
            .assertNextToken(TokenType.EOF);
        }

        @Test
        void whenUnicode_shouldRecognize() {
          var input = """
            "\\u1234 \\uabcd \\u0000 \\uffff"
            "\\u1234 \\uabcd \\u0000 \\uffff"
            """;

          createTest(input)
            .assertNextToken(TokenType.STRING, "\u1234 \uabcd \u0000 \uffff")
            .assertNextToken(TokenType.STRING, "\u1234 \uabcd \u0000 \uffff")
            .assertNextToken(TokenType.EOF);
        }
      }
    }

    @Nested
    class number {
      @Test
      void whenOnlyMinus_ShouldThrowException() {
        var input = " - ";

        InvalidNegativeNumberException exception = assertThrows(
          InvalidNegativeNumberException.class,
          () -> createTest(input).assertNextToken(TokenType.NUMBER)
        );
        assertEquals(1, exception.getStartIndex());
      }

      @Test
      void whenDotWithNoFraction_ShouldThrowException() {
        var input = " 2. ";

        InvalidDecimalNumberException exception = assertThrows(
          InvalidDecimalNumberException.class,
          () -> createTest(input).assertNextToken(TokenType.NUMBER)
        );
        assertEquals(2, exception.getStartIndex());
      }

      @Test
      void whenZero_ShouldRecognize() {
        var input = " 0 0";

        createTest(input)
          .assertNextToken(TokenType.NUMBER, "0")
          .assertNextToken(TokenType.NUMBER, "0")
          .assertNextToken(TokenType.EOF);
      }

      @Test
      void whenNegative_ShouldRecognize() {
        var input = " -2 -3 ";

        createTest(input)
          .assertNextToken(TokenType.NUMBER, "-2")
          .assertNextToken(TokenType.NUMBER, "-3")
          .assertNextToken(TokenType.EOF);
      }

      @Test
      void whenFraction_ShouldRecognize() {
        var input = " 2.3 -2.3";

        createTest(input)
          .assertNextToken(TokenType.NUMBER, "2.3")
          .assertNextToken(TokenType.NUMBER, "-2.3")
          .assertNextToken(TokenType.EOF);
      }

      @Test
      void whenExponent_ShouldRecognize() {
        var input = " 2e3 2E3 -2e3 2e+3 2e-3";

        createTest(input)
          .assertNextToken(TokenType.NUMBER, "2e3")
          .assertNextToken(TokenType.NUMBER, "2E3")
          .assertNextToken(TokenType.NUMBER, "-2e3")
          .assertNextToken(TokenType.NUMBER, "2e+3")
          .assertNextToken(TokenType.NUMBER, "2e-3")
          .assertNextToken(TokenType.EOF);
      }

      @Test
      void whenFractionAndExponent_ShouldRecognize() {
        var input = " 2.3e5 -3.2e-3";

        createTest(input)
          .assertNextToken(TokenType.NUMBER, "2.3e5")
          .assertNextToken(TokenType.NUMBER, "-3.2e-3")
          .assertNextToken(TokenType.EOF);
      }
    }
  }

  private TokenizerTester createTest(String input) {
    JsonTokenizer jsonTokenizer = new JsonTokenizer(input);

    return new TokenizerTester(jsonTokenizer);
  }

  private record TokenizerTester(JsonTokenizer jsonTokenizer) {
    TokenizerTester assertNextToken(TokenType expectedType) {
      assertNextToken(expectedType, null);

      return this;
    }

    TokenizerTester assertNextToken(TokenType expectedType, String expectedValue) {
      Token token = jsonTokenizer.getNextToken();
      assertEquals(expectedType, token.type(), token::toString);

      if (expectedValue != null) {
        assertEquals(expectedValue, token.value(), token::toString);
      }

      return this;
    }
  }
}
