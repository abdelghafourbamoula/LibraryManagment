package controller

import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.chart.{AreaChart, BarChart, PieChart, StackedAreaChart, XYChart}
import scalafx.scene.control.{Button, Label}
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class Dashboard (
                  private val books_num:Label,
                  private val std_num:Label,
                  private val res_num:Label,
                  private val back_btn:Button,
                  private val books_bar:BarChart[String,Int],
                  private val author_bar:BarChart[String,Int],
                  private val books_pie:PieChart,
                  private val res_line:StackedAreaChart[Int,Int]
                ){

  books_num.setText(ConnectDB.getNum("Book"))
  std_num.setText(ConnectDB.getNum("Student"))
  res_num.setText(ConnectDB.getNum("Reservation"))

  plotBooksBar()
  plotAuthorBar()
  plotPieChart()
  plotArea()

  def plotBooksBar(): Unit ={
    val statement = ConnectDB.connection.createStatement()
    val rs = statement.executeQuery(s"SELECT title , count(*) as c FROM Book B JOIN Reservation R ON B.idBook=R.idBook GROUP BY title;")

    while (rs.next) {
      val title = rs.getString("title")
      val count = rs.getInt("c")
      val series: XYChart.Series[String, Int] = new XYChart.Series()
      val data: XYChart.Data[String, Int] = XYChart.Data(title,count)
      series.getData().add(data)
      series.setName(title)
      books_bar.getData().addAll(series)
      books_bar.setTitle("Reservations for each book")
      books_bar.getXAxis().setLabel("Book")
      books_bar.getYAxis().setLabel("Reservations Count")
      books_bar.getYAxis.setTickLabelsVisible(false)
      books_bar.getYAxis.setOpacity(0)
    }

  }

  def plotAuthorBar(): Unit = {
    val statement = ConnectDB.connection.createStatement()
    val rs = statement.executeQuery(s"SELECT author , count(*) as c FROM Book B JOIN Reservation R ON B.idBook=R.idBook GROUP BY author;")

    while (rs.next) {
      val title = rs.getString("author")
      val count = rs.getInt("c")
      val series: XYChart.Series[String, Int] = new XYChart.Series()
      val data: XYChart.Data[String, Int] = XYChart.Data(title, count)
      series.getData().addAll(data)
      series.setName(title)
      author_bar.getData().addAll(series)
      }
      author_bar.setTitle("Reservations for each book")
      author_bar.getXAxis().setLabel("Book")
      author_bar.getYAxis().setLabel("Reservations Count")
      author_bar.getYAxis.setTickLabelsVisible(false)
      author_bar.getYAxis.setOpacity(0)


  }

  def plotPieChart(): Unit = {
    val data = ObservableBuffer(PieChart.Data("",0))
    data.remove(0)
    val statement = ConnectDB.connection.createStatement()
    val rs = statement.executeQuery(s"SELECT title , count(*) as c FROM Book GROUP BY title;")

    while (rs.next) {
      val title = rs.getString("title")
      val count = rs.getInt("c")
      data.addAll(PieChart.Data(title, count))

      books_pie.setData(data)
    }
  }

  def plotArea(): Unit ={
    val statement = ConnectDB.connection.createStatement()
    val rs = statement.executeQuery(s"SELECT SUBSTRING(reservation,4 ,2) AS res, SUBSTRING(`return`,4,2) AS ret , count(SUBSTRING(reservation,4,2) ) as cres, count(SUBSTRING(`return`,4,2) ) as cret FROM  Reservation GROUP BY res,ret;")
    while (rs.next) {
      val res = rs.getInt("res")
      val ret = rs.getInt("ret")
      val cres = rs.getInt("cres")
      val cret = rs.getInt("cret")
      println(res+" "+ret)
      val series1: XYChart.Series[Int, Int] = new XYChart.Series()
      val data1: XYChart.Data[Int, Int] = XYChart.Data(res, cres)
      series1.getData().addAll(data1)
      series1.setName(res.toString())
      val series2: XYChart.Series[Int, Int] = new XYChart.Series()
      val data2: XYChart.Data[Int, Int] = XYChart.Data(ret, cret)
      series2.getData().addAll(data2)
      series2.setName(ret.toString())

      res_line.getData().addAll(series1,series2)
    }

    res_line.setTitle("Reservations By Year")
    res_line.getXAxis().setLabel("Year")
    res_line.getYAxis().setLabel("Count")
    res_line.getYAxis.setTickLabelsVisible(false)
    res_line.getYAxis.setOpacity(0)

  }

  def toManer(e: ActionEvent): Unit = {
    val root = FXMLView(getClass.getResource("../fxml/Maner.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Amin Maner", scene)
  }

}
