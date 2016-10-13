name := """reactive-lab3"""

version := "1.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.11",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.11",
  "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test")
  

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
