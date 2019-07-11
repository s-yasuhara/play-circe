import ReleaseTransformations._

organization := "com.dripower"

name := "play-circe"

scalaVersion := "2.13.0"

crossScalaVersions := Seq("2.12.8", "2.13.0")

libraryDependencies ++= {
  val playV = "2.7.3"
  val circeV = "0.12.0-M4"
  Seq(
    "io.circe" %% "circe-core" % circeV,
    "io.circe" %% "circe-parser" % circeV,
    "com.typesafe.play" %% "play" % playV % Provided,
    "io.circe" %% "circe-generic" % circeV % Test,
    "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
    "com.typesafe.play" %% "play-ws" % playV % Test
  )
}

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xlint",
)

// POM settings for Sonatype
homepage := Some(url("https://github.com/jilen/play-circe"))

scmInfo := Some(ScmInfo(url("https://github.com/jilen/play-circe"),
  "git@github.com:jilen/play-circe.git"))

developers += Developer("jilen",
  "jilen",
  "jilen.zhang@gmail.com",
  url("https://github.com/jilen"))

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

pomIncludeRepository := (_ => false)

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

// release plugin

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value // Use publishSigned in publishArtifacts step

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)
