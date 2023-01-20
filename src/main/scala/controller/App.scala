package controller

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafxml.core.{FXMLView, NoDependencyResolver}

object App extends JFXApp3 {
  var cne:String = _
  def show(t:String, sc:Scene ): Unit = {
    this.stage = new JFXApp3.PrimaryStage{
      title = t
      scene = sc
    }
    this.stage.show()
  }

  override def start(): Unit = {
    //println(getClass.getResource("../fxml/login.fxml"))
    val root = FXMLView(getClass.getResource("../fxml/Login.fxml"), NoDependencyResolver)
    val sc = new Scene(root)
    this.show("Login", sc)
  }


}