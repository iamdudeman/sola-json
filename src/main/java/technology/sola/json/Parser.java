package technology.sola.json;

import technology.sola.json.exception.InvalidSyntaxException;

public class Parser {
  private final Tokenizer tokenizer;
  private Token currentToken;

  public Parser(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
    currentToken = tokenizer.getNextToken();
  }

  public JsonElement parse() {
    throw new UnsupportedOperationException("Not yet implemented");
  }

  private void eat(TokenType tokenType) {
    if (currentToken.type() == tokenType) {
      currentToken = tokenizer.getNextToken();
    } else {
      throw new InvalidSyntaxException();
    }
  }
}
