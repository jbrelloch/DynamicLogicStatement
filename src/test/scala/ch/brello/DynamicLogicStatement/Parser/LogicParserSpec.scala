package ch.brello.DynamicLogicStatement.Parser

import ch.brello.DynamicLogicStatement.Lexer.LogicLexer
import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.mock.MockitoSugar

class LogicParserSpec extends WordSpec with MockitoSugar with MustMatchers {
  "Logic Parser" should {
    "work" in {
      val testLexer = LogicLexer("(TEST1 && TEST2) || TEST3")

      assert(testLexer.isRight)

      val testParser = LogicParser(testLexer.right.get)

      assert(testParser.isRight)
    }
  }
}