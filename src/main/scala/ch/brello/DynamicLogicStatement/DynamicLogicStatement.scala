package ch.brello.DynamicLogicStatement

import ch.brello.DynamicLogicStatement.Compiler.{LogicCompiler, LogicLexerError, LogicParserError}

case class DynamicLogicStatement[A](
    statement: String,
    dynamicFields: Map[String, (A) => Boolean]
) {
  private val compiledStatementResult = LogicCompiler(statement)

  require(compiledStatementResult.isRight, s"Statement is invalid.  Reason: ${
    compiledStatementResult.left.get match {
      case lexerError: LogicLexerError => s"LEXER ERROR = ${lexerError.msg} Location = ${lexerError.location}"
      case parserError: LogicParserError => s"PARSER ERROR = ${parserError.msg} Location = ${parserError.location}"
    }
  }")

  //require(compiledStatementResult.right.get.)

  def evaluate(args: A): Boolean = {
    LogicCompiler.execute[A](compiledStatementResult.right.get, dynamicFields, args)
  }
}
