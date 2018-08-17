package ch.brello.DynamicLogicStatement.Compiler

sealed trait LogicCompilationError

case class LogicLexerError(location: Location, msg: String) extends LogicCompilationError
case class LogicParserError(location: Location, msg: String) extends LogicCompilationError
case class LogicRuntimeError(msg: String) extends LogicCompilationError

case class Location(line: Int, column: Int) {
  override def toString = s"$line:$column"
}
