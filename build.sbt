name := "HazelcaseTest"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
   "com.hazelcast" % "hazelcast" % "3.8",
   "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)