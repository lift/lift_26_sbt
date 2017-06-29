name := "Lift 2.6 starter template"

version := "0.0.4"

organization := "net.liftweb"

scalaVersion := "2.11.7"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                "releases"        at "https://oss.sonatype.org/content/repositories/releases"
                )

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "net.liftweb"       %% "lift-json"          % liftVersion       % "compile",
    "ch.qos.logback"    % "logback-classic"     % "1.2.3",
    "org.specs2"        %% "specs2-core"        % "3.6.4"           % "test"
  )
}

scalacOptions in Test ++= Seq("-Yrangepos")
