package technology.sola.json.tokenizer;

/**
 * Tokens that {@link JsonTokenizer} will recognize.
 */
public enum TokenType {
  /**
   * {@code :}
   */
  COLON,
  /**
   * {@code ,}
   */
  COMMA,
  /**
   * End of the file.
   */
  EOF,
  /**
   * {@code false}
   */
  FALSE,
  /**
   * {@code [}
   */
  L_BRACKET,
  /**
   * <code>{</code>
   */
  L_CURLY,
  /**
   * {@code null}
   */
  NULL,
  /**
   * {@code ((-[1-9])|0)[0-9]+(.[0-9]+)?([eE][-+]?[0-9]+)}
   */
  NUMBER,
  /**
   * {@code ]}
   */
  R_BRACKET,
  /**
   * {@code }}
   */
  R_CURLY,
  /**
   * {@code " (Any codepoint except " or \ or control characters) "}
   */
  STRING,
  /**
   * {@code true}
   */
  TRUE,
}
