import Commons.settings
import Dependencies._
import sbt.Keys._
import sbt.project

lazy val DynamicLogicStatement = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  configs(IntegrationTest).
  settings(settings: _*).
  settings(Defaults.itSettings : _*).
  settings(
    name := "dynamic-logic-statement",
    libraryDependencies ++= dependencies
  )

// -- set the main class
mainClass in assembly := Some("ch.brello.DynamicLogicStatement")

// -- make run command include the provided dependencies
run in Compile <<= Defaults.runTask(fullClasspath in Compile, mainClass in (Compile, run), runner in (Compile, run))

// -- exclude Scala library from assembly
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

// -- set the assembly shade rules
assemblyShadeRules in assembly := Seq(
  ShadeRule.rename("com.fasterxml.jackson.**" -> "cicjackson.@1").inAll
)

test in assembly := {}