import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "sample-ws"
  val appVersion      = "0.0.1-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "fr.canal.vod.sample" % "sample-api" % "0.0.1-SNAPSHOT",
    "com.typesafe.akka" % "akka-remote_2.10" % "2.2.1"
  )

  val main = play.Project( appName, appVersion, appDependencies)
  .settings(
    organization := "fr.canal.vod.sample",
    organizationName := "canal +"
  ).settings(
    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
  )
}
