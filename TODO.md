# todo list

## Bugs

* fix bugs found by new `JsonStandardValidity > validity > expectedFailures` test

## Improvements

* Line numbers in errors (and tokens)
* Consider switching tokenizer getNextToken() to getTokens()
* Consider keeping track of all syntax errors instead of just the first

## Performance

* Research possible performance improvements
    * Tokenizer#tokenString method
    * Tokenizer#tokenNumber method
    * General parser performance
    * Alternative ways of getting initial character array for Tokenizer (maybe not using String#toCharArray())
        * Maybe use charAt instead of toCharacterArray for tokenizer
