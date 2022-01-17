package technology.sola.json;

record TestPojo(int value, String value2) {
  static JsonMapper<TestPojo> JSON_MAPPER = new JsonMapper<>() {
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
  };
}
