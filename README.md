# sola-json

SolaJson is a lightweight JSON parsing library for Java 17+ that uses no additional libraries internally.
It started as a project to continue learning about building parsers based on language grammars.

[![Java CI](https://github.com/iamdudeman/sola-json/actions/workflows/ci_build.yml/badge.svg)](https://github.com/iamdudeman/sola-json/actions/workflows/ci_build.yml)
[![Javadocs Link](https://img.shields.io/badge/Javadocs-blue.svg)](https://iamdudeman.github.io/sola-json/)
[![](https://jitpack.io/v/iamdudeman/sola-json.svg)](https://jitpack.io/#iamdudeman/sola-json)

Parser definition based on [json.org](https://www.json.org/json-en.html)

## Download

### Gradle + Jitpack:

```kotlin
repositories {
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("com.github.iamdudeman:sola-json:2.1.3")
}
```

[sola-json jar downloads](https://github.com/iamdudeman/sola-json/releases) hosted on GitHub releases.

## Example usages

### Basic Usage

```java
public class BasicUsage {
    public static void main(String[] args) {
        String jsonString = """
            {
              "key": "value"
            }
            """;
        SolaJson solaJson = new SolaJson();
        JsonObject root = solaJson.parse(jsonString).asObject();
        System.out.println(root.getString("key"));

        root.put("key2", 10);
        System.out.println(solaJson.stringify(root));
    }
}
```

```shell
# Output
value
{"key":"value","key2":10}
```

### Usage with JsonMapper

```java
public class JsonMapperUsage {
    public static void main(String[] args) {
        String input = """
            {
              "value": 1
            }
            """;
        SolaJson solaJson = new SolaJson();
        Pojo result = solaJson.parse(input, JSON_MAPPER);
        System.out.println(result.value());

        Pojo pojo = new Pojo(10);
        System.out.println(solaJson.stringify(pojo, JSON_MAPPER));
    }

    public record Pojo(int value) {
    }

    static JsonMapper<Pojo> JSON_MAPPER = new JsonMapper<>() {
        @Override
        public JsonObject toJson(Pojo object) {
            JsonObject jsonObject = new JsonObject();

            jsonObject.put("value", object.value());

            return jsonObject;
        }

        @Override
        public TestPojo toObject(JsonObject jsonObject) {
            return new Pojo(jsonObject.getInt("value"));
        }
    };
}
```

```shell
# Output
1
{"value":1}
```

## Grammar

### Terminals

```
COLON      := :
COMMA      := ,
FALSE      := false
L_BRACKET  := [
L_CURLY    := {
NULL       := null
NUMBER     := ((-[1-9])|0)[0-9]+(.[0-9]+)?([eE][-+]?[0-9]+)
R_BRACKET  := ]
R_CURLY    := }
STRING     := " (Any codepoint except " or \ or control characters) "
TRUE       := true
```

### Rules

```
<root>    := <object> | <array>
<object>  := L_CURLY (<pair> (COMMA <pair>)*)? R_BRACKET
<array>   := L_BRACKET (<value> (COMMA <value>)*)? R_BRACKET
<pair>    := STRING COLON <value>
<value>   := STRING | NUMBER | <object> | <array> | TRUE | FALSE | NULL
```

## Testing

### Validity

Used test files from [JSON.org](https://www.json.org/JSON_checker/) to verify parsers functionality.

### Performance

Used data from [JSON placeholder](https://jsonplaceholder.typicode.com/) (small files) and San Francisco data (big file)
for performance testing.

Benchmarked against [Google gson](https://github.com/google/gson) and [Jackson](https://github.com/FasterXML/jackson).

[jmh](https://github.com/openjdk/jmh) benchmark file can be
viewed [here](src/test/java/technology/sola/json/jmh/SolaJsonBenchmark.java)

Execute benchmark view gradle task `jmhBenchmark` in verification category.

Results:
```
SolaJsonBenchmark.gsonBig          ss   50  20.953 ± 1.065  ms/op
SolaJsonBenchmark.gsonSmall        ss   50   3.271 ± 0.242  ms/op
SolaJsonBenchmark.jacksonBig       ss   50  17.882 ± 1.405  ms/op
SolaJsonBenchmark.jacksonSmall     ss   50   3.789 ± 0.449  ms/op
SolaJsonBenchmark.solaJsonBig      ss   50  36.022 ± 2.219  ms/op
SolaJsonBenchmark.solaJsonSmall    ss   50   5.628 ± 0.646  ms/op
```
