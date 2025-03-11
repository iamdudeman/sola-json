# todo list

## Bugs

*

## Improvements

* Use charAt instead of toCharacterArray for tokenizer
* Line numbers in errors (and tokens)
* Consider renaming "SolaJsonXYZ" classes to just "XYZ"
* Consider switching tokenizer getNextToken() to getTokens()
* Consider keeping track of all syntax errors instead of just the first

## Performance

* Consider adding performance testing
    * https://github.com/clarkware/junitperf
    * compare vs GSON
* Research possible performance improvements
    * Tokenizer#tokenString method
    * Tokenizer#tokenNumber method
    * General parser performance
    * Alternative ways of getting initial character array for Tokenizer (maybe not using String#toCharArray())
