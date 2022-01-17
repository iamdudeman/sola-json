package technology.sola.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonArrayTest {
  private JsonArray root;

  @BeforeEach
  void setup() {
    root = new JsonArray();
  }

  @Nested
  class convenience {
    @Test
    void objectMethods() {
      JsonObject expected = new JsonObject();

      root.add(expected);
      assertEquals(expected, root.getObject(0));

      root.add((JsonObject) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void arrayMethods() {
      JsonArray expected = new JsonArray();

      root.add(expected);
      assertEquals(expected, root.getArray(0));

      root.add((JsonArray) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void stringMethods() {
      String expected = "";

      root.add(expected);
      assertEquals(expected, root.getString(0));

      root.add((String) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void doubleMethods() {
      double expected = 0.0;

      root.add(expected);
      assertEquals(expected, root.getDouble(0));

      root.add((Double) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void floatMethods() {
      float expected = 0.0f;

      root.add(expected);
      assertEquals(expected, root.getFloat(0));

      root.add((Float) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void longMethods() {
      long expected = 0L;

      root.add(expected);
      assertEquals(expected, root.getLong(0));

      root.add((Long) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void intMethods() {
      int expected = 0;

      root.add(expected);
      assertEquals(expected, root.getInt(0));

      root.add((Integer) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void booleanMethods() {
      boolean expected = true;

      root.add(expected);
      assertEquals(expected, root.getBoolean(0));

      root.add((Boolean) null);
      assertTrue(root.isNull(1));
    }

    @Test
    void nullMethods() {
      root.addNull();
      assertTrue(root.isNull(0));
    }
  }
}
