# sola-json
Parser definition based on [json.org](https://www.json.org/json-en.html)

## Terminals
```
COLON      : :
COMMA      : ,
FALSE      : false
L_BRACKET  : [
L_CURLY    : {
NULL       : null
R_BRACKET  : ]
R_CURLY    : }
STRING     :
TRUE       : true
```

## Rules
```
root    : object|array
object  : L_CURLY (pair (COMMA pair)*? R_BRACKET
array   : L_BRACKET (value (COMMA value)*)? R_BRACKET
pair    : STRING COLON value
# todo "value" needs "number"
value   : STRING|object|array|TRUE|FALSE|NULL
```
