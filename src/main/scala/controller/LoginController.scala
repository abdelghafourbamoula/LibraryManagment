package controller

import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, ChoiceBox, Label, TextField}
import scalafxml.core.macros.sfxml

@sfxml
class LoginController (
                      private val acc_type : ChoiceBox[String],
                      private val username_fld: TextField,
                      private val password_fld: TextField,
                      private val login_btn: Button,
                      private val error_lbl: Label
                      ) {
  acc_type.items = ObservableBuffer[String]("ADMIN","STUDENT")

  def login(event: ActionEvent): Unit = {
    // TODO: pass to maner page after authentification
  }
}
