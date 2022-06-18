package technology.sola.json.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonArrayBuilderTest {
  @Test
  void test() {
    var result = new JsonArrayBuilder()
      .addInt(2)
      .addLong(3L)
      .addFloat(1.5f)
      .addDouble(2.5)
      .addBoolean(true)
      .addString("value")
      .addNull()
      .addArray(array -> array.addString("test"))
      .addObject(object -> object.addString("string", "value"))
      .build();

    assertEquals(2, result.getInt(0));
    assertEquals(3L, result.getLong(1));
    assertEquals(1.5f, result.getFloat(2));
    assertEquals(2.5, result.getDouble(3));
    assertTrue(result.getBoolean(4));
    assertEquals("value", result.getString(5));
    assertTrue(result.isNull(6));
    assertEquals("test", result.getArray(7).getString(0));
    assertEquals("value", result.getObject(8).getString("string"));
  }
}
