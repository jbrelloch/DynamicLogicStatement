package ch.brello.DynamicLogicStatement.Compiler

import ch.brello.DynamicLogicStatement.Lexer.LogicLexer
import ch.brello.DynamicLogicStatement.Parser.{LogicAST, LogicParser}

object LogicCompiler {
  def apply(statement: String): Either[LogicCompilationError, LogicAST] = {
    for {
      tokens <- LogicLexer(statement).right
      ast <- LogicParser(tokens).right
    } yield ast
  }

  def execute[A](logicAST: LogicAST, identifierMap: Map[String, (A) => Boolean], args: A): Boolean = {
    evaluateAST(logicAST, identifierMap, args)
  }

  private def evaluateAST[A](logicAST: LogicAST, identifierMap: Map[String, (A) => Boolean], args: A): Boolean = ???

  def validate(logicAST: LogicAST, keys: Set[String]): Boolean = ???
}
