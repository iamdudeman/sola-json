package technology.sola.json;

// TODO delete this PoC manual test
// TODO add unit tests for new pretty printing toString methods instead
public class PocTempMain {
  public static void main(String[] args) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.put("key", "value");
    jsonObject.put("key2", "value2");

    System.out.println(jsonObject.toString(2));


    JsonArray jsonArray = new JsonArray();

    jsonArray.addNull();
    jsonArray.add("test");
    jsonArray.add(1);

    System.out.println(jsonArray.toString(2));


    JsonObject nested = new JsonObject();

    nested.put("object", jsonObject);
    nested.putNull("test");
    nested.put("array", jsonArray);

    System.out.println(nested);
    System.out.println(nested.toString(3));
  }
}
