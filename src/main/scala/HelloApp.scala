import scalafx.Includes._
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

object MyApp extends JFXApp {
  val resource = getClass.getResource("fxml/hello.fxml")
  val root = FXMLView(resource, NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "Say Hello"
    scene = new Scene(root)
  }
}
