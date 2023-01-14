package controller

import javafx.fxml.FXML
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml

@sfxml
class HelloController(
                      private val label: Label,
                      private val btn: Button,
                      private val lbl2: Label,
                      private val btn2: Button
                    ) {


  def sayHello(event: ActionEvent): Unit = {
    label.setText("Hello Fucking ScalaFX ...")
  }

  def sayBjr(event: ActionEvent): Unit = {
    lbl2.setText("Bonjour !")
  }

}
