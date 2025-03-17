package technology.sola.json.parser;

import technology.sola.json.tokenizer.Token;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a node in the JSON syntax tree.
 *
 * @param type     the {@link AstNodeType} of this node
 * @param token    the {@link Token} for this node
 * @param children the children nodes or empty array
 */
public record AstNode(AstNodeType type, Token token, List<AstNode> children) {
  /**
   * Creates a new {@link AstNode} of type {@link AstNodeType#ARRAY}.
   *
   * @param children the children nodes
   * @return an {@code AstNode} of type {@code AstNodeType#ARRAY}
   */
  public static AstNode array(List<AstNode> children) {
    return new AstNode(AstNodeType.ARRAY, null, children);
  }

  /**
   * Creates a new {@link AstNode} of type {@link AstNodeType#PAIR}.
   *
   * @param nameToken the {@link Token} that represents the name for this pair
   * @param valueNode the {@code AstNode} the represents the value for this pair
   * @return an {@code AstNode} of type {@code AstNodeType#PAIR}
   */
  public static AstNode pair(Token nameToken, AstNode valueNode) {
    return new AstNode(AstNodeType.PAIR, nameToken, List.of(valueNode));
  }

  /**
   * Creates a new {@link AstNode} of type {@link AstNodeType#OBJECT}.
   *
   * @param pairs the children nodes
   * @return an {@code AstNode} of type {@code AstNodeType#OBJECT}
   */
  public static AstNode object(List<AstNode> pairs) {
    return new AstNode(AstNodeType.OBJECT, null, pairs);
  }

  /**
   * Creates a new {@link AstNode} of type {@link AstNodeType#VALUE}.
   *
   * @param token the {@link Token} containing the value for this node
   * @return an {@code AstNode} of type {@code AstNodeType#VALUE}
   */
  public static AstNode value(Token token) {
    return new AstNode(AstNodeType.VALUE, token, List.of());
  }

  @Override
  public String toString() {
    return "AstNode{" +
      "type=" + type +
      ", token=" + token +
      ", children=" + Arrays.toString(children.toArray()) +
      '}';
  }
}
