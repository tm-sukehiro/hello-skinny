lazy val scalajs = (project in file("src/main/webapp/WEB-INF/assets")).settings(
  name := "application", // JavaScript file name
  scalaVersion := "2.11.7",
  unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "scala"),
  fullResolvers ~= { _.filterNot(_.name == "jcenter") },
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom"    % "0.8.2",
    "be.doeraene"  %%% "scalajs-jquery" % "0.8.1",
    "org.monifu"   %%  "minitest"       % "0.14" % "test"
  ),
  crossTarget in Compile <<= baseDirectory(_ / ".." / ".." / "assets" / "js")
).enablePlugins(ScalaJSPlugin)
