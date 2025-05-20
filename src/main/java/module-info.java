/**
 * Defines the sola-json API.
 */
module technology.sola.json {
  requires org.jspecify;

  exports technology.sola.json;
  exports technology.sola.json.exception;
  exports technology.sola.json.mapper;
  exports technology.sola.json.parser;
  exports technology.sola.json.parser.exception;
  exports technology.sola.json.serializer;
  exports technology.sola.json.tokenizer;
  exports technology.sola.json.tokenizer.exception;
}
