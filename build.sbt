ThisBuild / organization := "com.mchange"
ThisBuild / version      := "0.0.1"

ThisBuild / resolvers += Resolver.sonatypeRepo("releases")
ThisBuild / resolvers += Resolver.sonatypeRepo("snapshots")
ThisBuild / resolvers += Resolver.jcenterRepo

ThisBuild / publishTo := {
  if (isSnapshot.value) Some(Resolver.sonatypeRepo("snapshots")) else Some(Resolver.url("sonatype-staging", url("https://oss.sonatype.org/service/local/staging/deploy/maven2")))
}

lazy val root = (project in file(".")).settings (
  name := "sbt-orchid",
  sbtPlugin := true,
  libraryDependencies ++= Seq(
    "io.github.javaeden.orchid" % "OrchidCore" % "0.17.6"
  ),
  pomExtra := pomExtraForProjectName_LGPLv3( name.value )
)

// publication, pom extra stuff, note this is single-licensed under LGPL v3

def pomExtraForProjectName_LGPLv3( projectName : String ) = {
    <url>https://github.com/swaldman/{projectName}</url>
    <licenses>
      <license>
        <name>GNU Lesser General Public License, Version 3</name>
        <url>https://www.gnu.org/licenses/lgpl-3.0.en.html</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:swaldman/{projectName}.git</url>
      <connection>scm:git:git@github.com:swaldman/{projectName}</connection>
    </scm>
    <developers>
      <developer>
        <id>swaldman</id>
        <name>Steve Waldman</name>
        <email>swaldman@mchange.com</email>
      </developer>
    </developers>
}



