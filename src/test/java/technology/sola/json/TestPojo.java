package technology.sola.json;

record TestPojo(int value, String value2) {
  static JsonMapper<TestPojo> JSON_MAPPER = new JsonMapper<>() {
    @Override
    public Class<TestPojo> getObjectClass() {
      return TestPojo.class;
    }

    @Override
    public JsonObject toJson(TestPojo testPojo) {
      JsonObject jsonObject = new JsonObject();

      jsonObject.put("value", testPojo.value());
      jsonObject.put("value2", testPojo.value2());

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
