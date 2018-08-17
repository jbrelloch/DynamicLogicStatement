package ch.brello.DynamicLogicStatement.Lexer

import scala.util.parsing.input.Positional

sealed trait LogicToken extends Positional

case class IDENTIFIER(str: String) extends LogicToken
//case class LITERAL(str: String) extends LogicToken
//case class INTEGER(num: Int) extends LogicToken
case class OPEN() extends LogicToken
case class CLOSE() extends LogicToken
case class AND() extends LogicToken
case class OR() extends LogicToken

