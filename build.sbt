ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "LibraryManagment"
  )


resourceDirectory in Compile := (scalaSource in Compile).value
libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "15.0.1-R21",
  "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-Ymacro-annotations")

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "15.0.1" classifier osName)
libraryDependencies += "de.jensd" % "fontawesomefx-fontawesome" % "4.7.0-9.1.2"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.31"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true


