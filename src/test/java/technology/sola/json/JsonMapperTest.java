package technology.sola.json;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonMapperTest {
  @Test
  void toJson_shouldHandleLists() {
    List<TestPojo> list = List.of(
      new TestPojo(1, "test"),
      new TestPojo(2, "test")
    );

    JsonMapper<TestPojo> jsonMapper = new TestPojoJsonMapper();
    JsonArray result = jsonMapper.toJson(list);

    assertEquals(1, result.getObject(0).getInt("value"));
    assertEquals(2, result.getObject(1).getInt("value"));
  }

  @Test
  void toList_shouldHandleJsonArray() {
    JsonArray jsonArray = new JsonArray();
    JsonObject first = new JsonObject();
    first.put("value", 1);
    first.put("value2", "test");
    jsonArray.add(first);
    JsonObject second = new JsonObject();
    second.put("value", 2);
    second.put("value2", "test");
    jsonArray.add(second);

    JsonMapper<TestPojo> jsonMapper = new TestPojoJsonMapper();
    List<TestPojo> list = jsonMapper.toList(jsonArray);

    assertEquals(1, list.get(0).value());
    assertEquals(2, list.get(1).value());
  }

  private static class TestPojoJsonMapper implements JsonMapper<TestPojo> {
    @Override
    public JsonObject toJson(TestPojo object) {
      JsonObject jsonObject = new JsonObject();

      jsonObject.put("value", object.value());
      jsonObject.put("value2", object.value2());

      return jsonObject;
    }

    @Override
    public TestPojo toObject(JsonObject jsonObject) {
      return new TestPojo(
        jsonObject.getInt("value"),
        jsonObject.getString("value2")
      );
    }
  }

  private record TestPojo(int value, String value2) {
  }
}
