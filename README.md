# sola-json

SolaJson is a lightweight JSON parsing library for Java.
It started as a project to continue learning about building parsers based on language grammars.

[![Java CI](https://github.com/iamdudeman/sola-json/actions/workflows/gradle.yml/badge.svg)](https://github.com/iamdudeman/sola-json/actions/workflows/gradle.yml)

Parser definition based on [json.org](https://www.json.org/json-en.html)

## Example Usages

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
    System.out.println(solaJson.serialize(root));
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
    System.out.println(solaJson.serialize(pojo, JSON_MAPPER));
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

## Terminals

```
COLON      : :
COMMA      : ,
FALSE      : false
L_BRACKET  : [
L_CURLY    : {
NULL       : null
NUMBER     : ((-[1-9])|0)[0-9]+(.[0-9]+)?([eE][-+]?[0-9]+)
R_BRACKET  : ]
R_CURLY    : }
STRING     : " (Any codepoint except " or \ or control characters) "
TRUE       : true
```

## Rules

```
root    : object|array
object  : L_CURLY (pair (COMMA pair)*? R_BRACKET
array   : L_BRACKET (value (COMMA value)*)? R_BRACKET
pair    : STRING COLON value
value   : STRING|NUMBER|object|array|TRUE|FALSE|NULL
```

## TODO List

* Performance
  * Continue to improve Tokenizer#tokenString method
  * Research improvements to Tokenizer#tokenNumber method
  * Improve Parser performance
  * Research alternative ways of getting initial character array for Tokenizer (maybe not using String#toCharArray())
* API
  * Pretty serializing of JsonElement, JsonObject and JsonArray
* JavaDoc all the things
* Bugs
  * JsonElement#toString (and SolaJson#serialize) doesn't support unicode characters
