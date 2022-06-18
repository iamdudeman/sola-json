package technology.sola.json.serializer;

/**
 * Holds configuration properties for {@link SolaJsonSerializer}.
 */
public class SolaJsonSerializerConfig {
  private int spaces = 0;

  /**
   * Returns the spaces used for indentation. If 0 then no new lines will be added.
   *
   * @return the spaces used for indentation
   */
  public int getSpaces() {
    return spaces;
  }

  /**
   * Sets the spaces used for indentation. If 0 then no new lines will be added.
   *
   * @param spaces the spaces used for indentation
   */
  public void setSpaces(int spaces) {
    this.spaces = spaces;
  }
}
