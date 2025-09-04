package technology.sola.json;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.mapper.JsonMapper;

@NullMarked
public record TestPojo(int value, String value2) {
  public static JsonMapper<TestPojo> JSON_MAPPER = new JsonMapper<>() {
    @Override
    public Class<TestPojo> getObjectClass() {
      return TestPojo.class;
    }

    @Override
    public JsonObject toJson(TestPojo testPojo) {
      JsonObject jsonObject = new JsonObject();

      jsonObject.put("value2", testPojo.value2());
      jsonObject.put("value", testPojo.value());

      return jsonObject;
    }

    @Override
    public TestPojo toObject(JsonObject jsonObject) {
      return new TestPojo(
        jsonObject.getInt("value"),
        jsonObject.getString("value2")
      );
    }
  };
}
