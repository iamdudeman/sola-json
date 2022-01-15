package technology.sola.json;

import java.util.ArrayList;

public class JsonArray extends ArrayList<JsonElement> {
  public JsonArray() {
  }

  public JsonArray(int initialCapacity) {
    super(initialCapacity);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("[");

    forEach(item -> {
      stringBuilder.append(item.toString());
      stringBuilder.append(",");
    });

    if (stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
      stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
    stringBuilder.append("]");

    return stringBuilder.toString();
  }
}
