package controller

import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class Maner (
              private val admin_lbl: Label,
              private val modifyad_btn: Button,
              private val logout_btn: Button,
              private val dash_btn: Button,
              private val book_btn: Button,
              private val res_btn: Button,
              private val std_btn: Button,
            ) {

  def modifyProfile(e: ActionEvent): Unit = {
    val root = FXMLView(getClass.getResource("../fxml/ModifyAdmin.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Modify Admin", scene)
  }

  def logout(e: ActionEvent): Unit ={
    val root = FXMLView(getClass.getResource("../fxml/login.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Login",scene)
  }

  def toBooks(e: ActionEvent): Unit = {}

  def toReservations(e: ActionEvent): Unit = {}

  def toStudents(e: ActionEvent): Unit = {}

  def toDashboard(e: ActionEvent): Unit = {}


}

