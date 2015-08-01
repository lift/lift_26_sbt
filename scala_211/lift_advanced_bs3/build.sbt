name := "Lift v2.6 Template Application with Bootstrap v3"

version := "0.0.5"

organization := "net.liftweb"

scalaVersion := "2.11.6"

resolvers ++= Seq("snapshots"     at "https://oss.sonatype.org/content/repositories/snapshots",
                  "staging"       at "https://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "https://oss.sonatype.org/content/repositories/releases"
                 )

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "net.liftmodules"   %% "fobo_2.6"           % "1.3"              % "compile",
    "ch.qos.logback"    % "logback-classic"     % "1.0.6",
    "org.specs2"        %% "specs2"             % "2.3.12"           % "test",
    "com.h2database"    % "h2"                  % "1.3.167",
    "javax.servlet"     % "javax.servlet-api"   % "3.0.1"            % "provided"
  )
}

enablePlugins(JettyPlugin)
