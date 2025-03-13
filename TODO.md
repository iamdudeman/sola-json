# todo list

## Bugs

* fix bugs found by new `JsonStandardValidity > validity > expectedFailures` test

## Performance

* Research possible performance improvements
    * Tokenizer#tokenString method
    * Tokenizer#tokenNumber method
    * General parser performance
    * Alternative ways of getting initial character array for Tokenizer (maybe not using String#toCharArray())
        * Maybe use charAt instead of toCharacterArray for tokenizer
