package technology.sola.json;

import technology.sola.json.parser.AstNode;
import technology.sola.json.parser.AstNodeType;
import technology.sola.json.parser.Parser;
import technology.sola.json.token.Tokenizer;

public class SolaJson {
  public JsonValue parse(String jsonString) {
    Tokenizer tokenizer = new Tokenizer(jsonString);
    Parser parser = new Parser(tokenizer);
    AstNode root = parser.parse();

    return visit(root);
  }

  private JsonValue visit(AstNode astNode) {
    return switch (astNode.type()) {
      case OBJECT -> visitObject(astNode);
      case ARRAY -> visitArray(astNode);
      case VALUE -> visitValue(astNode);
      default -> throw new RuntimeException("Invalid AST");
    };
  }

  private JsonValue visitObject(AstNode astNode) {
    JsonObject jsonObject = new JsonObject(astNode.children().length);

    for (AstNode pairNode : astNode.children()) {
      if (pairNode.type() != AstNodeType.PAIR) {
        throw new RuntimeException("Invalid AST");
      }

      String key = pairNode.token().value();
      JsonValue value = visit(pairNode.children()[0]);

      jsonObject.put(key, value);
    }

    return new JsonValue(jsonObject);
  }

  private JsonValue visitArray(AstNode astNode) {
    JsonArray jsonArray = new JsonArray(astNode.children().length);

    for (AstNode childNode : astNode.children()) {
      jsonArray.add(visit(childNode));
    }

    return new JsonValue(jsonArray);
  }

  private JsonValue visitValue(AstNode astNode) {
    return switch (astNode.token().type()) {
      case STRING -> new JsonValue(astNode.token().value());
      case TRUE -> new JsonValue(true);
      case FALSE -> new JsonValue(false);
      case NULL -> new JsonValue();
      case NUMBER -> {
        String value = astNode.token().value();

        if (value.contains(".")) {
          yield new JsonValue(Double.parseDouble(value));
        } else {
          yield new JsonValue(Long.parseLong(value));
        }
      }
      default -> throw new RuntimeException("Invalid AST");
    };
  }
}
