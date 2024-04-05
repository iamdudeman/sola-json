package technology.sola.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectTest {
  private static final String TEST_KEY = "testKey";
  private JsonObject root;

  @BeforeEach
  void setup() {
    root = new JsonObject();
  }

  @Nested
  class convenience {
    @Test
    void objectMethods() {
      JsonObject expected = new JsonObject();
      JsonObject defaultValue = new JsonObject();

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getObject(TEST_KEY));
      assertEquals(expected, root.getObject(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (JsonObject) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getObject(TEST_KEY, defaultValue));
    }

    @Test
    void arrayMethods() {
      JsonArray expected = new JsonArray();
      JsonArray defaultValue = new JsonArray();

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getArray(TEST_KEY));
      assertEquals(expected, root.getArray(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (JsonArray) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getArray(TEST_KEY, defaultValue));
    }

    @Test
    void stringMethods() {
      String expected = "";
      String defaultValue = "default";

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getString(TEST_KEY));
      assertEquals(expected, root.getString(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (String) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getString(TEST_KEY, defaultValue));
    }

    @Test
    void doubleMethods() {
      double expected = 0.0;
      double defaultValue = 10.0;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getDouble(TEST_KEY));
      assertEquals(expected, root.getDouble(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (Double) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getDouble(TEST_KEY, defaultValue));
    }

    @Test
    void floatMethods() {
      float expected = 0.0f;
      float defaultValue = 10.0f;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getFloat(TEST_KEY));
      assertEquals(expected, root.getFloat(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (Float) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getFloat(TEST_KEY, defaultValue));
    }

    @Test
    void integerMethods() {
      int expected = 0;
      int defaultValue = 10;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getInt(TEST_KEY));
      assertEquals(expected, root.getInt(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (Integer) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getInt(TEST_KEY, defaultValue));
    }

    @Test
    void longMethods() {
      long expected = 0L;
      long defaultValue = 10L;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getLong(TEST_KEY));
      assertEquals(expected, root.getLong(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (Long) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getLong(TEST_KEY, defaultValue));
    }

    @Test
    void booleanMethods() {
      boolean expected = true;
      boolean defaultValue = false;

      root.put(TEST_KEY, expected);
      assertEquals(expected, root.getBoolean(TEST_KEY));
      assertEquals(expected, root.getBoolean(TEST_KEY, defaultValue));

      root.put(TEST_KEY, (Boolean) null);
      assertTrue(root.isNull(TEST_KEY));
      assertEquals(defaultValue, root.getBoolean(TEST_KEY, defaultValue));
    }

    @Test
    void nullMethods() {
      root.putNull(TEST_KEY);
      assertTrue(root.isNull(TEST_KEY));
    }

    @Test
    void putMethodsChaining() {
      var result = new JsonObject()
        .put("int", 2)
        .put("long", 3L)
        .put("float", 1.5f)
        .put("double", 2.5)
        .put("boolean", true)
        .put("string", "value")
        .putNull("null")
        .put("array", new JsonArray().add("test"))
        .put("object", new JsonObject().put("string", "value"));

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

  @Nested
  class merge {
    @Test
    void shouldHaveUniqueKeysFromBothObjects() {
      JsonObject objectOne = new JsonObject();

      objectOne.put("test", 1);
      JsonObject objectTwo = new JsonObject();
      objectTwo.put("test2", 2);

      JsonObject result = objectOne.merge(objectTwo);

      assertEquals(1, result.getInt("test"));
      assertEquals(2, result.getInt("test2"));
    }

    @Test
    void shouldReplaceDuplicateKeysIfNotObjects() {
      JsonObject objectOne = new JsonObject();
      objectOne.put("test", 1);
      JsonObject objectTwo = new JsonObject();
      objectTwo.put("test", 2);

      assertEquals(2, objectOne.merge(objectTwo).getInt("test"));
      assertEquals(1, objectTwo.merge(objectOne).getInt("test"));
    }

    @Test
    void shouldMergeNestedObjects() {
      JsonObject objectOne = new JsonObject();
      JsonObject objectOneNested = new JsonObject();
      objectOne.put("test", objectOneNested);
      objectOneNested.put("test", 1);

      JsonObject objectTwo = new JsonObject();
      JsonObject objectTwoNested = new JsonObject();
      objectTwo.put("test", objectTwoNested);
      objectTwoNested.put("test", 2);

      assertEquals(2, objectOne.merge(objectTwo).getObject("test").getInt("test"));
      assertEquals(1, objectTwo.merge(objectOne).getObject("test").getInt("test"));
    }
  }
}
