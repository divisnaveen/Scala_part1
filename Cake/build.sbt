
name := "Cake"

organization := "com.persist"

version := "1.0"

scalaVersion := "2.12.1"

//viewSettings

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

scalacOptions ++= Seq("-Yrangepos")

testFrameworks += new TestFramework("com.fortysevendeg.lambdatest.sbtinterface.LambdaFramework")

libraryDependencies ++= Seq(
  "org.mapdb" % "mapdb" % "3.0.2",
  "com.fortysevendeg" % "lambda-test_2.11" % "1.1.2" % "test"
)

    