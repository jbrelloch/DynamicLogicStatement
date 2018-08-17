import sbt._

object Dependencies {
  val parserCombinator          = "org.scala-lang.modules"  %% "scala-parser-combinators"       % "1.0.4"
  val scalaTest                 =  "org.scalatest"          %%  "scalatest"                     % "2.2.6"           % "test"
  val mockito                   =  "org.mockito"            %   "mockito-core"                  % "1.10.19"         % "test"

  val dependencies: Seq[ModuleID] = Seq (
    parserCombinator,
    scalaTest,
    mockito
  )
}
