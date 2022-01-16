package technology.sola.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolaJsonTest {
  @Test
  void deserialize() {
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

    assertEquals("test2", root.getString("test"));
    assertTrue(root.isNull("null"));
    assertTrue(root.getBoolean("true"));
    assertFalse(root.getBoolean("false"));
    assertEquals("value", root.getObject("testObject").getString("test"));
    assertEquals("value", root.getArray("testArray").getObject(0).getString("test"));
  }

  @Test
  void serialize() {
    JsonArray jsonArray = new JsonArray();
    jsonArray.addNull();
    jsonArray.add(true);
    jsonArray.add(new JsonObject());

    JsonObject jsonObject = new JsonObject();
    jsonObject.put("key", "value");
    jsonObject.put("key2", false);
    jsonObject.put("array", jsonArray);

    String serialized = new SolaJson().serialize(jsonObject);

    assertEquals(
      "{\"key2\":false,\"array\":[null,true,{}],\"key\":\"value\"}",
      serialized
    );
  }
}
