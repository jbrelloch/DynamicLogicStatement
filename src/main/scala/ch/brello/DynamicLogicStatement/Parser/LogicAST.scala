package ch.brello.DynamicLogicStatement.Parser

import scala.util.parsing.input.Positional

sealed trait LogicAST extends Positional

case class Identifier(id: String) extends LogicAST
case class IdentifierAnd(id: String, next: LogicAST) extends LogicAST
case class IdentifierOr(id: String, next: LogicAST) extends LogicAST

case class GroupedCondition(condition: LogicAST) extends LogicAST
case class GroupedConditionAnd(condition: LogicAST, next: LogicAST) extends LogicAST
case class GroupedConditionOr(condition: LogicAST, next: LogicAST) extends LogicAST
