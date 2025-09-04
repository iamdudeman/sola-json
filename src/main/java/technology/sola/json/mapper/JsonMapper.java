package technology.sola.json.mapper;

import org.jspecify.annotations.NullMarked;
import technology.sola.json.JsonArray;
import technology.sola.json.JsonElement;
import technology.sola.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JsonMapper defines how a class will be converted to and from {@link JsonObject}s.
 *
 * @param <T> the type to define a JSON mapping for
 */
@NullMarked
public interface JsonMapper<T> {
  /**
   * Converts an object of type T into a {@link JsonObject}.
   *
   * @param object the object to convert
   * @return the {@code JsonObject} representing the converted object
   */
  JsonObject toJson(T object);

  /**
   * Converts a {@link List} of T into a {@link JsonArray}.
   *
   * @param list the {@code List} to convert
   * @return the converted {@code JsonArray}
   */
  default JsonArray toJson(List<T> list) {
    JsonArray jsonArray = new JsonArray(list.size());

    list.forEach(item -> jsonArray.add(toJson(item)));

    return jsonArray;
  }

  /**
   * Converts a {@link JsonObject} into an object of type T.
   *
   * @param jsonObject the {@code JsonObject} to convert
   * @return the converted object of type T
   */
  T toObject(JsonObject jsonObject);

  /**
   * Converts a {@link JsonArray} into a {@link List} of specified type T. This assumes that each child of {@code jsonArray}
   * is a {@link JsonObject} and of type T.
   *
   * @param jsonArray the {@code JsonArray} to convert
   * @return the converted {@code List<T>}
   */
  default List<T> toList(JsonArray jsonArray) {
    List<T> list = new ArrayList<>(jsonArray.size());

    for (JsonElement jsonElement : jsonArray) {
      list.add(toObject(jsonElement.asObject()));
    }

    return list;
  }
}
