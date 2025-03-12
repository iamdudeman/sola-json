package technology.sola.json;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

// todo improve this

/**
 * This test verifies parser functionality using test files from [JSON.org](https://www.json.org/JSON_checker/).
 */
public class JsonCheckerTest {
  @Test
  void pass() throws IOException {
    var url = getClass().getResource("/validity/pass");
    var passDir = new File(url.getFile());
    var files = passDir.listFiles();

    for (var file : files) {
      var blah = Files.readString(file.toPath());

      try {
        new SolaJson().parse(blah);
      } catch (Exception e) {
        System.err.println(file.getName());

        e.printStackTrace();
      }
    }
  }

  @Test
  void fail() throws IOException {
    var url = getClass().getResource("/validity/fail");
    var passDir = new File(url.getFile());
    var files = passDir.listFiles();

    for (var file : files) {
      var blah = Files.readString(file.toPath());

      try {
        new SolaJson().parse(blah);
        System.err.println(file.getName());
      } catch (Exception e) {
        // todo verify exceptions maybe?
//        e.printStackTrace();
      }
    }
  }
}
