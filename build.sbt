name := """reactive-lab3"""

version := "1.1"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.6",
  "com.typesafe.akka" % "akka-testkit" % "2.5.6",
  "org.scalatest" % "scalatest_2.11" % "3.0.1" % "test")
  

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
