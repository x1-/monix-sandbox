organization := "com.inkenkun.x1"

name := "monix-sandbox"

version := "0.1"

scalaVersion := "2.12.3"

scalacOptions ++= Seq( "-deprecation", "-encoding", "UTF-8", "-target:jvm-1.8" )

val monixVersion    = "2.3.0"

libraryDependencies ++= Seq(
  "ch.qos.logback"          % "logback-classic"      % "1.2.3",
  "io.monix"               %% "monix"                % monixVersion,
  "io.monix"               %% "monix-cats"           % monixVersion,
  "org.typelevel"          %% "cats"                 % "0.9.0",
  "org.scalatest"          %% "scalatest"            % "3.0.1"  % "test",
  "org.scalacheck"         %% "scalacheck"           % "1.13.4" % "test"
)
