val scalaVersionString = "2.11.7"

def globalSettings = Seq(
  scalaVersion := scalaVersionString,
  scalacOptions ++= Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-Xlint",
    "-Xfatal-warnings"
  ),
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersionString,
  libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersionString
)

val annos = project settings (globalSettings: _*)

lazy val plugin = project settings (globalSettings: _*) dependsOn(annos)

def pluginLocation = (file("plugin") / "target" / "scala-2.11" ** "*.jar").get.headOption.map(_.getAbsolutePath).orElse{
  println("No plugin")
  None
}

def withPubPlugin = pluginLocation.toList.map(path => scalacOptions += s"-Xplugin:$path")


lazy val testProj = project dependsOn plugin settings (globalSettings: _*) settings (withPubPlugin: _*)
