package technology.sola.json.jmh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import technology.sola.json.SolaJson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class SolaJsonBenchmark {
  private String commentsJsonString;
  private String photosJsonString;
  private String usersJsonString;

  @Setup(Level.Trial)
  public void loadFiles() throws IOException {
    commentsJsonString = readFileToString("/performance/comments.json");
    photosJsonString = readFileToString("/performance/photos.json");
    usersJsonString = readFileToString("/performance/users.json");
  }

  @Benchmark
  public void solaJson(Blackhole blackhole) {
    blackhole.consume(
      new SolaJson().parse(commentsJsonString)
    );

    blackhole.consume(
      new SolaJson().parse(photosJsonString)
    );

    blackhole.consume(
      new SolaJson().parse(usersJsonString)
    );
  }

  @Benchmark
  public void gson(Blackhole blackhole) {
    blackhole.consume(
      JsonParser.parseString(commentsJsonString)
    );

    blackhole.consume(
      JsonParser.parseString(photosJsonString)
    );

    blackhole.consume(
      JsonParser.parseString(usersJsonString)
    );
  }

  @Benchmark
  public void jackson(Blackhole blackhole) throws JsonProcessingException {
    blackhole.consume(
      new ObjectMapper().readTree(commentsJsonString)
    );

    blackhole.consume(
      new ObjectMapper().readTree(photosJsonString)
    );

    blackhole.consume(
      new ObjectMapper().readTree(usersJsonString)
    );
  }

  private String readFileToString(String resourcePath) throws IOException {
    return Files.readString(new File(getClass().getResource(resourcePath).getFile()).toPath());
  }
}
