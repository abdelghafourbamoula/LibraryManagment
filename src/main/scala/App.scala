import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

object App extends JFXApp{
  println(getClass.getResource("fxml/login.fxml"))
  val root = FXMLView(getClass.getResource("fxml/login.fxml"), NoDependencyResolver)

  stage = new PrimaryStage() {
    title = "Login"
    scene = new Scene(root)
  }
}
