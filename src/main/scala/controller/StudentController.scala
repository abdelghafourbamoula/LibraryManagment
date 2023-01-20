package controller

import scalafx.Includes._
import model.{Book, Reservation, Student}
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, DatePicker, Label, TableColumn, TableView, TextField}
import scalafx.scene.input.MouseEvent
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

case class Books(var id:String, var title:String, var author:String, var pub:String){}

@sfxml
class StudentController (
                          private val name:Label,
                          private val book_lbl:Label,
                          private val idbook_lbl:Label,
                          private val error_lbl:Label,
                          private val res_date:DatePicker,
                          private val return_date:DatePicker,
                          private val delete_btn:Button,
                          private val update_btn:Button,
                          private val back_btn:Button,
                          private val searsh_btn:Button,
                          private val searsh_fld:TextField,
                          private val books_table:TableView[Books],
                          private val id_col:TableColumn[Books,String],
                          private val title_col:TableColumn[Books,String],
                          private val author_col:TableColumn[Books,String],
                          private val pub_col:TableColumn[Books,String]
                        ){

  id_col.cellValueFactory = idb => StringProperty(idb.value.id)
  title_col.cellValueFactory = br => StringProperty(br.value.title)
  author_col.cellValueFactory = dr => StringProperty(dr.value.author)
  pub_col.cellValueFactory = ch => StringProperty(ch.value.pub)

  name.setText(App.cne)
  showBooks()

  def showBooks(): Unit = {
    books_table.items = ConnectDB.getBooksForStudent()
  }

  def cancelStd(e:ActionEvent): Unit ={
    idbook_lbl.setText("")
    book_lbl.setText("")
    res_date.getEditor().setText("")
    return_date.getEditor().setText("")
  }

  def Reserve(e:ActionEvent): Unit ={
    val id = idbook_lbl.getText()
    val title = book_lbl.getText()
    val reservations = res_date.getEditor().getText()
    val ret = return_date.getEditor().getText()

    val r = ConnectDB.insertReservation(Reservation(id,App.cne,title,reservations,ret))
    if (r != 0) {
      error_lbl.setText("Reservation Inserted Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #f2fffd;")
    } else {
      error_lbl.setText("Failed to Update Reservation !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

    showBooks()
  }

  def searshBook(e:ActionEvent): Unit ={
      books_table.items = ConnectDB.searshBooksForStudent(searsh_fld.getText())
  }

  def getSelectedBook(e:MouseEvent): Unit ={
    val book = books_table.getSelectionModel().getSelectedItem()
    idbook_lbl.setText(book.id)
    book_lbl.setText(book.title)
  }

  def toManer(e: ActionEvent): Unit = {
    val root = FXMLView(getClass.getResource("../fxml/Login.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Amin Maner", scene)
  }

}
