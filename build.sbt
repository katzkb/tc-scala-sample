ThisBuild / scalaVersion := "3.4.0"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "example"
ThisBuild / organizationName := "example"

val doobieVersion = "1.0.0-RC4"
val catsEffectVersion = "3.4.8"

lazy val root = (project in file("."))
  .settings(
    name := "tc-scala-sample",
    libraryDependencies ++= Seq(
      "mysql" % "mysql-connector-java" % "8.0.33",
      "org.tpolecat" %% "doobie-core" % doobieVersion,
      "org.tpolecat" %% "doobie-hikari" % doobieVersion,
      "org.tpolecat" %% "doobie-specs2" % doobieVersion % Test,
      "org.tpolecat" %% "doobie-scalatest" % doobieVersion % Test,
      "org.typelevel" %% "cats-effect" % catsEffectVersion,
      "org.scalactic" %% "scalactic" % "3.2.18" % Test,
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "ch.qos.logback" % "logback-classic" % "1.4.14" % Runtime,
      "com.dimafeng" %% "testcontainers-scala-mysql" % "0.41.3" % Test,
    ),
    Test / fork := true,
  )
