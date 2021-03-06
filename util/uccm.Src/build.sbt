
import com.github.retronym.SbtOneJar._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(Seq(
      version := "1.0.0",
      organization := "com.github.uccm",
      scalaVersion := "2.12.2",
      licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php")),
      scalacOptions := Seq("-unchecked", "-deprecation", "-feature")
    )),
    name := "uccm",
    oneJarSettings,
    libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0",
    libraryDependencies += "commons-lang" % "commons-lang" % "2.6",
    libraryDependencies += "commons-io" % "commons-io" % "2.5",
    libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"
  )
