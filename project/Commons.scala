import sbt.Keys._
import sbt._

object Commons {

  val settings: Seq[Def.Setting[_]] = Seq(

    // -- project settings
    scalaVersion := "2.11.8",
    scalaVersion in ThisBuild := "2.11.8",
    organization := "ch.brello",
    version := "0.1-SNAPSHOT",
    parallelExecution in Test := false,

    // -- custom resolvers
    resolvers ++= Seq (
      Resolver.file("localIvy", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)
    )
  )

}