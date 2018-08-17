package ch.brello.DynamicLogicStatement.Parser

import scala.util.parsing.input.Positional

sealed trait LogicAST extends Positional
case class LogicStatement(logicASTs: List[LogicAST]) extends LogicAST

case class Identifier(id: String) extends LogicAST
case class IdentifierAnd(baseId: String, right: LogicAST) extends LogicAST
case class IdentifierOr(baseId: String, right: LogicAST) extends LogicAST

case class GroupedCondition(condition: LogicAST) extends LogicAST
case class GroupedConditionAnd(baseCondition: LogicAST, right: LogicAST) extends LogicAST
case class GroupedConditionOr(baseCondition: LogicAST, right: LogicAST) extends LogicAST
