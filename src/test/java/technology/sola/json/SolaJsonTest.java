package technology.sola.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolaJsonTest {
  @Test
  void test() {
    String jsonString = """
      {
        "test": "test2",
        "testObject": {
          "test": "value"
        },
        "testArray": [
          {
            "test": "woot"
          }
        ]
      }
      """;

    SolaJson solaJson = new SolaJson();

    JsonObject root = solaJson.parse(jsonString).asObject();

    assertEquals("test2", root.get("test").asString());
    assertEquals("value", root.getObject("testObject").get("test").asString());
    assertEquals("woot", root.getArray("testArray").get(0).asObject().get("test").asString());
  }
}
