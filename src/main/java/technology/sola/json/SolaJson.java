package technology.sola.json;

import technology.sola.json.parser.AstNode;
import technology.sola.json.parser.AstNodeType;
import technology.sola.json.parser.Parser;
import technology.sola.json.token.Tokenizer;

public class SolaJson {
  public JsonElement parse(String jsonString) {
    Tokenizer tokenizer = new Tokenizer(jsonString);
    Parser parser = new Parser(tokenizer);
    AstNode root = parser.parse();

    return visit(root);
  }

  private JsonElement visit(AstNode astNode) {
    return switch (astNode.type()) {
      case OBJECT -> visitObject(astNode);
      case ARRAY -> visitArray(astNode);
      case VALUE -> visitValue(astNode);
      default -> throw new RuntimeException("Invalid AST");
    };
  }

  private JsonElement visitObject(AstNode astNode) {
    JsonObject jsonObject = new JsonObject(astNode.children().length);

    for (AstNode pairNode : astNode.children()) {
      if (pairNode.type() != AstNodeType.PAIR) {
        throw new RuntimeException("Invalid AST");
      }

      String key = pairNode.token().value();
      JsonElement value = visit(pairNode.children()[0]);

      jsonObject.put(key, value);
    }

    return new JsonElement(jsonObject);
  }

  private JsonElement visitArray(AstNode astNode) {
    JsonArray jsonArray = new JsonArray(astNode.children().length);

    for (AstNode childNode : astNode.children()) {
      jsonArray.add(visit(childNode));
    }

    return new JsonElement(jsonArray);
  }

  private JsonElement visitValue(AstNode astNode) {
    return switch (astNode.token().type()) {
      case STRING -> new JsonElement(astNode.token().value());
      case TRUE -> new JsonElement(true);
      case FALSE -> new JsonElement(false);
      case NULL -> new JsonElement();
      case NUMBER -> {
        String value = astNode.token().value();

        if (value.contains(".")) {
          yield new JsonElement(Double.parseDouble(value));
        } else {
          yield new JsonElement(Long.parseLong(value));
        }
      }
      default -> throw new RuntimeException("Invalid AST");
    };
  }
}
