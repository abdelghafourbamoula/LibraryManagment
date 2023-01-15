import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafxml.core.{FXMLView, NoDependencyResolver}
//import scalafx.event.{ActionEvent, EventHandler}
import javafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.layout.VBox
import scalafx.stage.Stage

object Switch extends JFXApp3 {
  var scene1: Scene = _
  var scene2: Scene = _

  override def start(): Unit = {

    stage = new PrimaryStage {
      title = "My First JavaFX GUI"
    }

    // Scene 1
    val label1 = new Label("This is the first scene")
    val button1 = new Button("Go to scene 2") {
      onAction = (e: ActionEvent) => {
        println(getClass.getResource("fxml/login.fxml").toString())
        stage.setScene(new Scene(FXMLView(getClass.getResource("fxml/login.fxml"), NoDependencyResolver )))
      }
    }
    val layout1 = new VBox(20)
    layout1.children ++= Seq(label1, button1)

    scene1 = new Scene(layout1, 300, 250)

    // Scene 2
    val label2 = new Label("This is the second scene")
    val button2 = new Button("Go to scene 1") {
      onAction = (e: ActionEvent) => {
        stage.setScene(scene1)
      }
    }
    val layout2 = new VBox(20)
    layout2.children ++= Seq(label2, button2)
    scene2 = new Scene(layout2, 300, 250)

    stage.setScene(scene1)
  }
}