package controller


import scalafx.Includes._
import scalafx.application.{JFXApp, JFXApp3}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, ChoiceBox, Label, PasswordField, TextField}
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml
@sfxml
class LoginController(
                       private val acc_type : ChoiceBox[String],
                       private val username_fld: TextField,
                       private val password_fld: PasswordField,
                       private val login_btn: Button,
                       private val error_lbl: Label
                      ) {

  acc_type.setItems(ObservableBuffer[String]("ADMIN", "STUDENT"))

  def login(event: ActionEvent): Unit = {
    val type_ = acc_type.value()

    if (type_ == "ADMIN") {
      val admin = ConnectDB.loginAdmin(username_fld.getText(), password_fld.getText())

      if (admin.length != 0){
        val root = FXMLView(getClass.getResource("../fxml/Maner.fxml"), NoDependencyResolver)
        val scene = new Scene(root)
        App.show("Admin Maner", scene)
      }
      else
        error_lbl.setText("Authentification Fieled: Incorrect username / password")
    }
    else if(type_ == "STUDENT"){
      // TODO: student authentification
    }
    else {
      error_lbl.setText("Please Select Your Account Type !")
    }



  }

}
