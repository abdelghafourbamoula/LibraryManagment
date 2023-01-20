package controller

import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class ModifyAdmin (
                  private val user_fld:TextField,
                  private val passwd_fld:TextField,
                  private val fname_fld:TextField,
                  private val lname_fld:TextField,
                  private val username_fld:TextField,
                  private val password_fld:TextField,
                  private val confirm_fld:TextField,
                  private val back_btn:Button,
                  private val searsh_btn:Button,
                  private val clear_btn:Button,
                  private val reset_btn:Button,
                  private val update_btn:Button,
                  private val error_lbl: Label
                  ) {

  var admin: Array[String] = _

  def back(e:ActionEvent): Unit ={
    val root = FXMLView(getClass.getResource("../fxml/Maner.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Admin Maner", scene)
  }

  def searshAdmin(e:ActionEvent): Unit ={
    admin = ConnectDB.loginAdmin(user_fld.getText(), passwd_fld.getText())
    if( admin.length !=  0) {
      fname_fld.setText(admin(1))
      lname_fld.setText(admin(2))
      username_fld.setText(admin(3))
      password_fld.setText(admin(4))
      confirm_fld.setText("")
    }
    else
      error_lbl.setText("Incorrect Username / Password")
  }

  def clearFields(e:ActionEvent): Unit ={
    fname_fld.setText("")
    lname_fld.setText("")
    username_fld.setText("")
    password_fld.setText("")
    confirm_fld.setText("")
  }

  def resetFields(e:ActionEvent): Unit ={
    fname_fld.setText(admin(1))
    lname_fld.setText(admin(2))
    username_fld.setText(admin(3))
    password_fld.setText(admin(4))
    confirm_fld.setText(admin(4))
  }

  def updateAdmin(e:ActionEvent): Unit ={
    if (password_fld.getText() != confirm_fld.getText()) {
      error_lbl.setText("Passord and Confirm Passord Unmatch !")
      confirm_fld.setStyle("-fx-border-color: #cb4800;")
    } else {
      admin(1) = fname_fld.getText()
      admin(2) = lname_fld.getText()
      admin(3) = username_fld.getText()
      admin(4) = password_fld.getText()
      val v = ConnectDB.updateAdmin(admin)

      if (v != 0){
        error_lbl.setText("Admin Updated Sucessfully !")
        error_lbl.setStyle("-fx-text-fill: #07b74b;")
      } else {
        error_lbl.setText("Admin Informations Not Updated !")
      }
    }
  }

}
