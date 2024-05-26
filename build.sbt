ThisBuild / scalaVersion := "3.3.1"

name := "smithy4s-http4s-example"
organization := "io.github.todokr"
version := "1.0"

val http4sVersion = "0.23.26"
val smithy4sVersion = "0.18.20"

val server = project
  .in(file("./server"))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-ember-client" % http4sVersion,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.http4s" %% "http4s-circe" % http4sVersion,
      "io.circe" %% "circe-generic" % "0.14.6",
      "io.circe" %% "circe-literal" % "0.14.6",
      "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion
    )
  )
