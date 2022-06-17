package technology.sola.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonElementBuilderTest {
  @Test
  void object() {
    var result = JsonElementBuilder.startObject()
      .addInt("int", 2)
      .addLong("long", 3L)
      .addFloat("float", 1.5f)
      .addDouble("double", 2.5)
      .addBoolean("boolean", true)
      .addString("string", "value")
      .addNull("null")
      .addArray("array", array -> array.addString("test"))
      .addObject("object", object -> object.addString("string", "value"))
      .finish();

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

  @Test
  void array() {
    var result = JsonElementBuilder.startArray()
      .addInt(2)
      .addLong(3L)
      .addFloat(1.5f)
      .addDouble(2.5)
      .addBoolean(true)
      .addString("value")
      .addNull()
      .addArray(array -> array.addString("test"))
      .addObject(object -> object.addString("string", "value"))
      .finish();

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
