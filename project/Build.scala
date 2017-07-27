import sbt._
import Keys._

object ScalanumBuild extends Build {

  val ScalaTestVersion = "2.2.6"

  val CommonSettings = Seq(
    organization := "com.github.izeigerman",
    scalaVersion := "2.11.8",
    version := "0.1",

    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-language:higherKinds"),

    parallelExecution in Test := false,

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % ScalaTestVersion % "test->*"
    )
  )

  val NoPublishSettings = Seq(
    publishArtifact := false,
    publish := {}
  )

  lazy val root = Project(id = "root", base = file("."))
    .settings(NoPublishSettings: _*)
    .aggregate(scalanum)

  lazy val scalanum = Project(id = "scalanum", base = file("scalanum"))
    .settings(CommonSettings: _*)
}

