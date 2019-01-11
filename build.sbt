name:= "guest-book"

organization := "io.q2"

scalaVersion := "2.12.7"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= {
  object V {
    val http4s = "0.19.0"
    val circe = "0.10.0"
    val specs2 = "4.3.4"
    val catsEffect = "1.1.0"
    val logback = "1.2.3"
    val scalalogging = "3.9.0"
    val config = "1.3.2"
  }
  Seq(
  "org.specs2" %% "specs2-core" % V.specs2 % "test",
  "org.http4s" %% "http4s-dsl" % V.http4s,
  "org.http4s" %% "http4s-blaze-server" % V.http4s, 
  "org.http4s" %% "http4s-circe" % V.http4s,
  "io.circe" %% "circe-core" % V.circe, 
  "io.circe" %% "circe-generic" % V.circe,
  "io.circe" %% "circe-parser" %  V.circe,
    "com.typesafe" %  "config" % V.config,
  "com.typesafe.scala-logging" %% "scala-logging" % V.scalalogging,
  "ch.qos.logback" % "logback-classic" % V.logback

)}

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
  "-language:higherKinds",
  "-Ypartial-unification"
)

enablePlugins(JavaServerAppPackaging, BuildInfoPlugin)
