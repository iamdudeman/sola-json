# sola-json
Parser definition based on [json.org](https://www.json.org/json-en.html)

## Terminals
```
COLON      : :
COMMA      : ,
L_BRACKET  : [
L_CURLY    : {
R_BRACKET  : ]
R_CURLY    : }
```

## Rules
```
root   : object|array
array  : L_CURLY value (COMMA value)* R_CURLY
# todo value needs   string|number|object
value  : array|true|false|null
```
