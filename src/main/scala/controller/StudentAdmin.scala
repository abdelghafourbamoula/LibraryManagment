package controller


import scalafx.Includes._
import model.Student
import scalafx.beans.property.StringProperty
import scalafx.collections.{ObservableArray, ObservableBuffer}
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, ListView, TableColumn, TableView, TextField}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.AnchorPane
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class StudentAdmin(
                    private val cne_fld: TextField,
                    private val fname_fld: TextField,
                    private val email_fld: TextField,
                    private val password_fld: TextField,
                    private val searsh_fld: TextField,
                    private val delete_btn: Button,
                    private val update_btn: Button,
                    private val insert_btn: Button,
                    private val back_btn: Button,
                    private val searsh_btn: Button,
                    private val students_table: TableView[Student],
                    private val cne_col: TableColumn[Student, String],
                    private val fname_col: TableColumn[Student, String],
                    private val email_col: TableColumn[Student, String],
                    private val password_col: TableColumn[Student,String],
                    private val error_lbl: Label
                   ){

  cne_col.cellValueFactory = ids => StringProperty(ids.value.cne)
  fname_col.cellValueFactory = fs => StringProperty(fs.value.fname)
  email_col.cellValueFactory = ls => StringProperty(ls.value.email)
  password_col.cellValueFactory = ps => StringProperty(ps.value.password)

  showStudents()

  def showStudents(): Unit = {
    students_table.items = ConnectDB.getStudents("")
  }

  def toManer(e: ActionEvent): Unit = {
    val root = FXMLView(getClass.getResource("../fxml/Maner.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Amin Maner", scene)
  }

  def searshStudent(e:ActionEvent): Unit ={
      val str = searsh_fld.getText()
      val data = ConnectDB.getStudents(str)
      if (data.length != 0) {
        students_table.items = data
        students_table.refresh()
      }
      else
        error_lbl.setText(str+" not match any student case !")
  }

  def getSelectedStudent(e:MouseEvent): Unit ={
    val student = students_table.getSelectionModel().getSelectedItem()
    // println(student.toString())
    cne_fld.setText(student.cne)
    fname_fld.setText(student.fname)
    email_fld.setText(student.email)
    password_fld.setText(student.password)
  }

  def insertStudent(e:ActionEvent): Unit ={
    val cne = cne_fld.getText()
    val fname = fname_fld.getText()
    val email = email_fld.getText()
    val password = password_fld.getText()

    if (cne != "" || password != "") {
      val r = ConnectDB.insertStudent(Student(cne,fname, email, password))
      if (r != 0) {
        error_lbl.setText("Student Inserted Sucessfully !")
        error_lbl.setStyle("-fx-text-fill: #f2fffd;")
      } else {
        error_lbl.setText("Failed Student to Inset Book !")
        error_lbl.setStyle("-fx-text-fill: #d02c2c;")
      }
    }
    else{
      error_lbl.setText("You must insert cne and password")
      error_lbl.setStyle(" -fx-border-color: #ec2121;")
    }

    showStudents()
  }

  def updateStudent(e:ActionEvent): Unit ={
    val cne = cne_fld.getText()
    val fname = fname_fld.getText()
    val email = email_fld.getText()
    val password = password_fld.getText()

    val r = ConnectDB.updateStudent(Student(cne, fname, email, password))
    if (r != 0) {
      error_lbl.setText("Student Updated Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #f2fffd;")
    } else {
      error_lbl.setText("CNE is an Identifient for Student !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

    showStudents()
  }

  def deleteStudent(e:ActionEvent): Unit ={
    val cne = cne_fld.getText()
    val r = ConnectDB.deleteStudent(cne)
    if (r != 0) {
      error_lbl.setText("Student Deleted Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #f2fffd;")
    } else {
      error_lbl.setText("Failed to Delete Student !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

    showStudents()
  }

}
