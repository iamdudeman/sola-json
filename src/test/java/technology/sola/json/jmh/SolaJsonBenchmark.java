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
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 2, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 3, timeUnit = TimeUnit.SECONDS)
public class SolaJsonBenchmark {
  @Benchmark
  public void solaJsonSmall(BenchmarkState benchmarkState, Blackhole blackhole) {
    blackhole.consume(
      new SolaJson().parse(benchmarkState.commentsJsonString)
    );

    blackhole.consume(
      new SolaJson().parse(benchmarkState.photosJsonString)
    );

    blackhole.consume(
      new SolaJson().parse(benchmarkState.usersJsonString)
    );
  }

  @Benchmark
  public void solaJsonBig(BenchmarkState benchmarkState, Blackhole blackhole) {
    blackhole.consume(
      new SolaJson().parse(benchmarkState.bigJsonString)
    );
  }

  @Benchmark
  public void gsonSmall(BenchmarkState benchmarkState, Blackhole blackhole) {
    blackhole.consume(
      JsonParser.parseString(benchmarkState.commentsJsonString)
    );

    blackhole.consume(
      JsonParser.parseString(benchmarkState.photosJsonString)
    );

    blackhole.consume(
      JsonParser.parseString(benchmarkState.usersJsonString)
    );
  }

  @Benchmark
  public void gsonBig(BenchmarkState benchmarkState, Blackhole blackhole) {
    blackhole.consume(
      JsonParser.parseString(benchmarkState.bigJsonString)
    );
  }

  @Benchmark
  public void jacksonSmall(BenchmarkState benchmarkState, Blackhole blackhole) throws JsonProcessingException {
    blackhole.consume(
      new ObjectMapper().readTree(benchmarkState.commentsJsonString)
    );

    blackhole.consume(
      new ObjectMapper().readTree(benchmarkState.photosJsonString)
    );

    blackhole.consume(
      new ObjectMapper().readTree(benchmarkState.usersJsonString)
    );
  }

  @Benchmark
  public void jacksonBig(BenchmarkState benchmarkState, Blackhole blackhole) throws JsonProcessingException {
    blackhole.consume(
      new ObjectMapper().readTree(benchmarkState.bigJsonString)
    );
  }

  @State(Scope.Thread)
  public static class BenchmarkState {
    private String commentsJsonString;
    private String photosJsonString;
    private String usersJsonString;
    private String bigJsonString;

    @Setup
    public void prepare() throws IOException {
      System.out.println("\nLoading files for benchmark...");
      commentsJsonString = readFileToString("/performance/comments.json");
      photosJsonString = readFileToString("/performance/photos.json");
      usersJsonString = readFileToString("/performance/users.json");
      bigJsonString = readFileToString("/performance/big-file.json");
    }

    private String readFileToString(String resourcePath) throws IOException {
      return Files.readString(new File(getClass().getResource(resourcePath).getFile()).toPath());
    }
  }
}
