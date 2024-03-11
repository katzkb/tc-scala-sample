
ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "tc-scala-sample",
    libraryDependencies ++= Seq(
      "mysql" % "mysql-connector-java" % "8.0.31",
      "org.scalactic" %% "scalactic" % "3.2.18" % Test,
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "com.dimafeng" %% "testcontainers-scala-mysql" % "0.41.3" % Test,
      "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
