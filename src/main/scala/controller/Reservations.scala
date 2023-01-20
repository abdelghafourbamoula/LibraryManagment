package controller

import model.Reservation
import scalafx.Includes._
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, DatePicker, Label, TableColumn, TableView, TextField}
import scalafx.scene.input.MouseEvent
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml


@sfxml
class Reservations (
                     private val cne_lbl:Label,
                     private val book_lbl:Label,
                     private val error_lbl:Label,
                     private val res_date:DatePicker,
                     private val return_date:DatePicker,
                     private val searsh_fld:TextField,
                     private val delete_btn:Button,
                     private val update_btn:Button,
                     private val back_btn:Button,
                     private val reservations_table:TableView[Reservation],
                     private val cne_res:TableColumn[Reservation,String],
                     private val book_res:TableColumn[Reservation,String],
                     private val resdate_res:TableColumn[Reservation,String],
                     private val hist_table:TableView[Reservation],
                     private val cne_hist:TableColumn[Reservation,String],
                     private val book_hist:TableColumn[Reservation,String],
                     private val res_hist:TableColumn[Reservation,String],
                     private val ret_hist:TableColumn[Reservation,String]
                   ){

  cne_res.cellValueFactory = cr => StringProperty(cr.value.cne)
  book_res.cellValueFactory = br => StringProperty(br.value.book)
  resdate_res.cellValueFactory = dr => StringProperty(dr.value.res)
  cne_hist.cellValueFactory = ch => StringProperty(ch.value.cne)
  book_hist.cellValueFactory = bh => StringProperty(bh.value.book)
  res_hist.cellValueFactory = rh => StringProperty(rh.value.res)
  ret_hist.cellValueFactory = rhs => StringProperty(rhs.value.ret)

  showReservations()

  def showReservations(): Unit = {
    reservations_table.items = ConnectDB.getReservations()
    hist_table.items = ConnectDB.getReservations(true)
  }

  def searshRes(e:ActionEvent): Unit ={
    reservations_table.items = ConnectDB.searshReservation(searsh_fld.getText())
    hist_table.items = ConnectDB.searshReservation(searsh_fld.getText(), true)
  }

  def toManer(e:ActionEvent): Unit ={
    val root = FXMLView(getClass.getResource("../fxml/Maner.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Amin Maner", scene)
  }

  def deleteRes(e:ActionEvent): Unit ={

    val r = ConnectDB.deleteReservation(cne_lbl.getText(), book_lbl.getText())
    if (r != 0) {
      error_lbl.setText("Reservation Inserted Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #f2fffd;")
    } else {
      error_lbl.setText("Failed to Update Reservation !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

    showReservations()
  }

  def updateRes(e:ActionEvent): Unit ={
    val cne = cne_lbl.getText()
    val book = book_lbl.getText()
    val reservation = res_date.getEditor().getText()
    val retur = return_date.getEditor().getText()
    println(reservation, retur)

    val r = ConnectDB.updateReservation(Reservation("",cne,book,reservation,retur))
    if (r != 0) {
      error_lbl.setText("Reservation Inserted Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #f2fffd;")
    } else {
      error_lbl.setText("Failed to Update Reservation !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

    showReservations()
  }

  def getSelectedRes(e:MouseEvent): Unit ={
    val res = reservations_table.getSelectionModel().getSelectedItem()
    cne_lbl.setText(res.cne)
    book_lbl.setText(res.book)
    res_date.getEditor().setText(res.res)
    return_date.getEditor().setText(res.ret)
  }

  def getSelectedHist(e:MouseEvent): Unit ={
    val res = hist_table.getSelectionModel().getSelectedItem()
    cne_lbl.setText(res.cne)
    book_lbl.setText(res.book)
    res_date.getEditor().setText(res.res)
    return_date.getEditor().setText(res.ret)
  }

}
