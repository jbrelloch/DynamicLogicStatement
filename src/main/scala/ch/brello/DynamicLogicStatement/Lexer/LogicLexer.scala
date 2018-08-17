package ch.brello.DynamicLogicStatement.Lexer

import ch.brello.DynamicLogicStatement.Compiler.{Location, LogicLexerError}

import scala.util.parsing.combinator.RegexParsers

object LogicLexer extends RegexParsers {

  def apply(code: String): Either[LogicLexerError, List[LogicToken]] = {
    parse(tokens, code) match {
      case NoSuccess(msg, next) => Left(LogicLexerError(Location(next.pos.line, next.pos.column), msg))
      case Success(result, next) => Right(result)
    }
  }

  def tokens: Parser[List[LogicToken]] = {
    phrase(rep1(open | close | and | or /*| literal | integer*/ | identifier)) ^^ { rawTokens => rawTokens.asInstanceOf[List[LogicToken]] }
  }

  def identifier: Parser[IDENTIFIER] = positioned {
    "[a-zA-Z_][a-zA-Z0-9_]*".r ^^ { str => IDENTIFIER(str) }
  }

//  def literal: Parser[LITERAL] = positioned {
//    """"[^"]*"""".r ^^ { str =>
//      val content = str.substring(1, str.length - 1)
//      LITERAL(content)
//    }
//  }
//
//  def integer: Parser[INTEGER] = positioned {
//    "[0-9]+".r ^^ { str =>
//      INTEGER(str.toInt)
//    }
//  }

  def open = positioned { "("          ^^ (_ => OPEN()) }
  def close = positioned { ")"    ^^ (_ => CLOSE()) }
  def and = positioned { "&&"  ^^ (_ => AND()) }
  def or = positioned { "||"        ^^ (_ => OR()) }
}
