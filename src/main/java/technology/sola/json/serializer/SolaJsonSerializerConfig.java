package technology.sola.json.serializer;

/**
 * Holds configuration properties for {@link SolaJsonSerializer}.
 *
 * @param spaces the spaces used for indentation. If 0 then no new lines will be added.
 */
public record SolaJsonSerializerConfig(
  int spaces
) {
}
