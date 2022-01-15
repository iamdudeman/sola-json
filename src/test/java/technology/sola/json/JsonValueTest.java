package technology.sola.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonValueTest {
  @Test
  void toString_shouldSerializeTree() {
    String input = """
      {
        "key": "value",
        "array": [
          null,
          {
            "key": "value"
          },
          true
        ],
        "object": {},
        "key2": false
      }
      """;

    JsonValue jsonValue = new SolaJson().parse(input);

    assertEquals(
      "{\"key2\":false,\"array\":[null,{\"key\":\"value\"},true],\"key\":\"value\",\"object\":{}}",
      jsonValue.toString()
    );
  }
}
