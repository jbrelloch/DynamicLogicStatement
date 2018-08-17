package ch.brello.DynamicLogicStatement.Lexer

import org.scalatest.{MustMatchers, WordSpec}
import org.scalatest.mock.MockitoSugar

class LogicLexerSpec extends WordSpec with MockitoSugar with MustMatchers {
  "Logic Lexer" should {
    "work" in {
      val test = LogicLexer("(TEST1 && TEST2) || TEST3")

      assert(test.isRight)
    }
  }
}
