package technology.sola.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import technology.sola.json.serializer.SolaJsonSerializerConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolaJsonTest {
  private SolaJson solaJson;
  private SolaJson solaJsonWithSpaces;

  @BeforeEach
  void setup() {
    solaJson = new SolaJson();
    solaJsonWithSpaces = new SolaJson(new SolaJsonSerializerConfig(2));
  }

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

    String serialized = solaJson.stringify(jsonObject);

    assertEquals(
      "{\"key2\":false,\"array\":[null,true,{}],\"key\":\"value\"}",
      serialized
    );
  }

  @Test
  void serialize_withSpaces() {
    JsonArray jsonArray = new JsonArray();
    jsonArray.addNull();
    jsonArray.add(true);
    JsonObject nestedObject = new JsonObject();
    nestedObject.put("key", "value");
    jsonArray.add(nestedObject);

    JsonObject jsonObject = new JsonObject();
    jsonObject.put("key", "value");
    jsonObject.put("key2", false);
    jsonObject.put("array", jsonArray);
    jsonObject.put("key3", new JsonObject());
    jsonObject.put("key4", new JsonArray());

    String serialized = solaJsonWithSpaces.stringify(jsonObject);

    assertEquals(
        """
        {
          "key2": false,
          "array": [
            null,
            true,
            {
              "key": "value"
            }
          ],
          "key3": {},
          "key4": [],
          "key": "value"
        }""",
      serialized
    );
  }

  @Nested
  class withMapper {
    @Test
    void parse() {
      String input = """
        {
          "value": 1,
          "value2": "test"
        }
        """;

      TestPojo result = solaJson.parse(input, TestPojo.JSON_MAPPER);

      assertEquals(1, result.value());
      assertEquals("test", result.value2());
    }

    @Test
    void parseList() {
      String input = """
        [
          {
            "value": 1,
            "value2": "test"
          }
        ]
        """;

      List<TestPojo> result = solaJson.parseList(input, TestPojo.JSON_MAPPER);

      assertEquals(1, result.get(0).value());
      assertEquals("test", result.get(0).value2());
    }

    @Test
    void stringify() {
      TestPojo testPojo = new TestPojo(1, "test");

      String result = solaJson.stringify(testPojo, TestPojo.JSON_MAPPER);

      assertEquals("{\"value2\":\"test\",\"value\":1}", result);
    }

    @Test
    void stringify_withSpaces() {
      TestPojo testPojo = new TestPojo(1, "test");

      String result = solaJsonWithSpaces.stringify(testPojo, TestPojo.JSON_MAPPER);

      assertEquals("""
        {
          "value2": "test",
          "value": 1
        }""", result);
    }

    @Test
    void stringifyList() {
      List<TestPojo> list = List.of(new TestPojo(1, "test"));

      String result = solaJson.stringify(list, TestPojo.JSON_MAPPER);

      assertEquals("[{\"value2\":\"test\",\"value\":1}]", result);
    }

    @Test
    void stringifyList_withSpaces() {
      List<TestPojo> list = List.of(new TestPojo(1, "test"));

      String result = solaJsonWithSpaces.stringify(list, TestPojo.JSON_MAPPER);

      assertEquals("""
        [
          {
            "value2": "test",
            "value": 1
          }
        ]""", result);
    }
  }
}
