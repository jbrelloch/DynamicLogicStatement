package ch.brello.DynamicLogicStatement.Parser

import ch.brello.DynamicLogicStatement.Compiler.{Location, LogicParserError}
import ch.brello.DynamicLogicStatement.Lexer.LogicToken
import ch.brello.DynamicLogicStatement.Lexer._

import scala.util.parsing.combinator.Parsers
import scala.util.parsing.input.{NoPosition, Position, Reader}

object LogicParser extends Parsers {
  override type Elem = LogicToken

  class LogicTokenReader(tokens: Seq[LogicToken]) extends Reader[LogicToken] {
    override def first: LogicToken = tokens.head
    override def atEnd: Boolean = tokens.isEmpty
    override def pos: Position = tokens.headOption.map(_.pos).getOrElse(NoPosition)
    override def rest: Reader[LogicToken] = new LogicTokenReader(tokens.tail)
  }


  def apply(tokens: Seq[LogicToken]): Either[LogicParserError, LogicAST] = {
    val reader = new LogicTokenReader(tokens)
    logicStatement(reader) match {
      case NoSuccess(msg, next) => Left(LogicParserError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, _) => Right(result)
    }
  }

  def logicStatement: Parser[LogicAST] = positioned {
    phrase(condition)
  }

  def condition: Parser[LogicAST] = positioned {
    val normalCondition = identifier ~ opt(AND() ~ condition | OR() ~ condition) ^^ {
      case ident ~ None => Identifier(ident.str)
      case ident ~ Some(AND() ~ cond) => IdentifierAnd(ident.str, cond)
      case ident ~ Some(OR() ~ cond) => IdentifierOr(ident.str, cond)
    }
    val groupedCondition = OPEN() ~ condition ~ CLOSE() ~ opt(AND() ~ condition | OR() ~ condition) ^^ {
      case _ ~ cond ~ _ ~ None => GroupedCondition(cond)
      case _ ~ cond1 ~ _ ~ Some(AND() ~ cond2) => GroupedConditionAnd(cond1, cond2)
      case _ ~ cond1 ~ _ ~ Some(OR() ~ cond2) => GroupedConditionOr(cond1, cond2)
    }
    normalCondition | groupedCondition
  }

  private def identifier: Parser[IDENTIFIER] = positioned {
    accept("identifier", { case id @ IDENTIFIER(_) => id })
  }
}
