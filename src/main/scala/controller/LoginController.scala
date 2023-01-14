package controller

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, ChoiceBox, TextField}
import scalafxml.core.macros.sfxml

@sfxml
class LoginController (
                      private val acc_type : ChoiceBox[String],
                      private val username_fld: TextField,
                      private val password_fld: TextField,
                      private val login_btn: Button
                      ) {
acc_type.items = ObservableBuffer[String]("ADMIN","STUDENT")
}
