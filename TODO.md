* Line numbers in errors
* Keep track of all syntax errors instead of just the first
* Consider adding performance testing
    * https://github.com/clarkware/junitperf
    * compare vs GSON
* Research possible performance improvements
    * Tokenizer#tokenString method
    * Tokenizer#tokenNumber method
    * General parser performance
    * Alternative ways of getting initial character array for Tokenizer (maybe not using String#toCharArray())
