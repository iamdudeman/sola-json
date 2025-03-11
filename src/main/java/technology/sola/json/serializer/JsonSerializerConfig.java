package technology.sola.json.serializer;

/**
 * Holds configuration properties for {@link JsonSerializer}.
 *
 * @param spaces the spaces used for indentation. If 0 then no new lines will be added.
 */
public record JsonSerializerConfig(
  int spaces
) {
}
