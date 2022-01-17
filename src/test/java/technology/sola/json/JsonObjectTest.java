package technology.sola.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import technology.sola.json.exception.PropertyNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectTest {
  private static final String TEST_KEY = "testKey";
  private JsonObject root;

  @BeforeEach
  void setup() {
    root = new JsonObject();
  }

  @Test
  void get_whenMissing_shouldThrowException() {
    assertThrows(PropertyNotFoundException.class, () -> root.get("test"));
  }

  @Nested
  class convenience {
    @Test
    void objectMethods() {
      JsonObject expected = new JsonObject();

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getObject(TEST_KEY));

      root.put(TEST_KEY, (JsonObject) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void arrayMethods() {
      JsonArray expected = new JsonArray();

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getArray(TEST_KEY));

      root.put(TEST_KEY, (JsonArray) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void stringMethods() {
      String expected = "";

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getString(TEST_KEY));

      root.put(TEST_KEY, (String) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void doubleMethods() {
      double expected = 0.0;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getDouble(TEST_KEY));

      root.put(TEST_KEY, (Double) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void floatMethods() {
      float expected = 0.0f;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getFloat(TEST_KEY));

      root.put(TEST_KEY, (Float) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void integerMethods() {
      int expected = 0;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getInt(TEST_KEY));

      root.put(TEST_KEY, (Integer) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void longMethods() {
      long expected = 0L;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getLong(TEST_KEY));

      root.put(TEST_KEY, (Long) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void booleanMethods() {
      boolean expected = true;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getBoolean(TEST_KEY));

      root.put(TEST_KEY, (Boolean) null);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void nullMethods() {
      root.putNull(TEST_KEY);
      assertTrue(root.isNull(TEST_KEY));
    }
  }
}
