ThisBuild / organization := "com.mchange"
ThisBuild / version      := "0.0.1-SNAPSHOT"

ThisBuild / resolvers += Resolver.jcenterRepo


lazy val root = (project in file(".")).settings (
  name := "sbt-orchid",
  sbtPlugin := true,
  libraryDependencies ++= Seq(
    "io.github.javaeden.orchid" % "OrchidCore" % "0.17.6"
  )
)

