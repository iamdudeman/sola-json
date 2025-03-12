package technology.sola.json.jmh;

import com.google.gson.Gson;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import technology.sola.json.JsonElement;
import technology.sola.json.SolaJson;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 1, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class SolaJsonBenchmark {
  @Setup(Level.Trial)
  public void loadFiles() {
    // todo load test files in memory
  }

  @Benchmark
  public void solaJson(Blackhole blackhole) {
    blackhole.consume(
      new SolaJson().parse("{}")
    );
  }

  @Benchmark
  public void gson(Blackhole blackhole) {
    blackhole.consume(
      new Gson().fromJson("{}", JsonElement.class)
    );
  }
}
