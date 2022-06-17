package technology.sola.json.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonObjectBuilderTest {
  @Test
  void test() {
    var result = new JsonObjectBuilder()
      .addInt("int", 2)
      .addLong("long", 3L)
      .addFloat("float", 1.5f)
      .addDouble("double", 2.5)
      .addBoolean("boolean", true)
      .addString("string", "value")
      .addNull("null")
      .addArray("array", array -> array.addString("test"))
      .addObject("object", object -> object.addString("string", "value"))
      .build();

    assertEquals(2, result.getInt("int"));
    assertEquals(3L, result.getLong("long"));
    assertEquals(1.5f, result.getFloat("float"));
    assertEquals(2.5, result.getDouble("double"));
    assertEquals("value", result.getString("string"));
    assertTrue(result.getBoolean("boolean"));
    assertTrue(result.isNull("null"));
    assertEquals("test", result.getArray("array").getString(0));
    assertEquals("value", result.getObject("object").getString("string"));
  }
}
