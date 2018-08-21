package ch.brello.DynamicLogicStatement.Compiler

import ch.brello.DynamicLogicStatement.Lexer.LogicLexer
import ch.brello.DynamicLogicStatement.Parser._

object LogicCompiler {
  def apply(statement: String): Either[LogicCompilationError, LogicAST] = {
    for {
      tokens <- LogicLexer(statement).right
      ast <- LogicParser(tokens).right
    } yield ast
  }

  def execute[A](logicStatement: LogicAST, identifierMap: Map[String, (A) => Boolean], args: A): Boolean = {
    evaluateAST(logicStatement, identifierMap, args)
  }

  private def evaluateAST[A](logicAST: LogicAST, identifierMap: Map[String, (A) => Boolean], args: A): Boolean = {
      logicAST match {
        case groupedCondition: GroupedCondition => evaluateAST(groupedCondition.condition, identifierMap, args)
        case groupedConditionAnd: GroupedConditionAnd => evaluateAST(groupedConditionAnd.condition, identifierMap, args) && evaluateAST(groupedConditionAnd.next, identifierMap, args)
        case groupedConditionOr: GroupedConditionAnd => evaluateAST(groupedConditionOr.condition, identifierMap, args) || evaluateAST(groupedConditionOr.next, identifierMap, args)
        case identifier: Identifier => processIdentifier(identifier.id, identifierMap, args)
        case identifierAnd: IdentifierAnd => processIdentifier(identifierAnd.id, identifierMap, args) && evaluateAST(identifierAnd.next, identifierMap, args)
        case identifierOr: IdentifierOr =>  processIdentifier(identifierOr.id, identifierMap, args) ||evaluateAST(identifierOr.next, identifierMap, args)
      }
  }

  private def processIdentifier[A](identifier: String, identifierMap: Map[String, (A) => Boolean], args: A): Boolean = identifierMap(identifier)(args)

  def validate(logicStatement: LogicAST, keys: Set[String]): Boolean = getIdentifierList(logicStatement).subsetOf(keys)

  private def getIdentifierList(logicAST: LogicAST): Set[String] = logicAST match {
    case groupedCondition: GroupedCondition => getIdentifierList(groupedCondition)
    case groupedConditionAnd: GroupedConditionAnd => getIdentifierList(groupedConditionAnd) ++ getIdentifierList(groupedConditionAnd)
    case groupedConditionOr: GroupedConditionAnd => getIdentifierList(groupedConditionOr) ++ getIdentifierList(groupedConditionOr)
    case identifier: Identifier => Set(identifier.id)
    case identifierAnd: IdentifierAnd => Set(identifierAnd.id) ++ getIdentifierList(identifierAnd.next)
    case identifierOr: IdentifierOr =>  Set(identifierOr.id) ++ getIdentifierList(identifierOr.next)
  }
}
