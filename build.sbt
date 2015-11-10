name := "rapidstring"

organization := "com.vpon"

organizationHomepage := None

scalaVersion := "2.11.7"

scalacOptions += "-feature"

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions ++= Seq("-Xelide-below", "FINEST")

crossScalaVersions := Seq("2.11.0")

version := "0.1.2"

publishTo <<= (isSnapshot) { isSnapshot: Boolean =>
  if (isSnapshot)
    Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
     Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

libraryDependencies +=
  "org.scalatest" %% "scalatest" % "2.2.4" % "test->default"

libraryDependencies <+= scalaVersion { sv =>
  "org.scala-lang" % "scala-reflect" % sv
}

licenses := Seq(
  "Apache License, Version 2.0" ->
  url("http://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://github.com/vpon/rapidstring"))

scmInfo := Some(ScmInfo(
  url("https://github.com/vpon/rapidstring"),
  "scm:git:git://github.com/vpon/rapidstring.git",
  Some("scm:git:git@github.com:Vpon/rapidstring.git")))

pomExtra :=
  <developers>
    <developer>
      <id>vpon</id>
      <name>Vpon</name>
      <timezone>+8</timezone>
      <email>dev@vpon.com</email>
    </developer>
  </developers>
