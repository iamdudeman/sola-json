package technology.sola.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolaJsonTest {
  @Test
  void test() {
    String jsonString = """
      {
        "test": "test2",
        "testObject": {
          "test": "value"
        },
        "null": null,
        "true": true,
        "false": false,
        "testArray": [
          {
            "test": "value"
          }
        ]
      }
      """;

    SolaJson solaJson = new SolaJson();

    JsonObject root = solaJson.parse(jsonString).asObject();

    assertEquals("test2", root.get("test").asString());
    assertTrue(root.get("null").isNull());
    assertTrue(root.get("true").asBoolean());
    assertFalse(root.get("false").asBoolean());
    assertEquals("value", root.getObject("testObject").get("test").asString());
    assertEquals("value", root.getArray("testArray").get(0).asObject().get("test").asString());
  }
}
