# sola-json
Parser definition based on [json.org](https://www.json.org/json-en.html)

SolaJson is a lightweight JSON parsing library for Java that is reflection free.
It started as a project to continue learning more about grammars and parsing.

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
* Error handling
  * Line + character count for where bad characters found
  * Better messages for why parsing fails
* Performance
  * Continue to improve Tokenizer#tokenString method
  * Research improvements to Tokenizer#tokenNumber method
  * Improve Parser performance
  * Research alternative ways of getting initial character array for Tokenizer (maybe not using String#toCharArray())
* API
  * Pretty serializing of JsonElement, JsonObject and JsonArray
* JavaDoc all the things
* Pipeline
  * Add github actions for builds to main
