package controller

import model.Book
import scalafx.Includes._

import java.sql.{Connection, DriverManager}
import scala.collection.mutable.ListBuffer

object ConnectDB {

  // connect to the database named "mydb" on port 3307 of localhost
  val url = "jdbc:mysql://localhost:3307/mydb"
  val driver = "com.mysql.jdbc.Driver"
  val username = "root"
  val password = "root"
  var connection:Connection = _
  try {
    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    println("Connected ...")
  } catch {
    case e: Exception => e.printStackTrace
  }


  def loginAdmin(username:String, password: String): Array[String] = {
      val statement = connection.createStatement()
      val rs = statement.executeQuery("SELECT * FROM Admin WHERE username='"+username+"' AND password='"+password+"';")
      if (rs.next) {
        val id = rs.getString("idAdmin")
        val fname = rs.getString("firstName")
        val lname = rs.getString("lastName")
        val username = rs.getString("username")
        val password = rs.getString("password")
        return Array(id,fname,lname,username,password)
      }
      else
        return Array()
  }

  def updateAdmin(admin:Array[String]): Int ={
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"UPDATE Admin SET firstName='${admin(1)}', lastName='${admin(2)}', username='${admin(3)}'," +
                                s" password='${admin(4)}' WHERE idAdmin=${admin(0)};")
    return rs
  }

  def searhBook(title:String): Array[String] ={

    val statement = connection.createStatement()
    val rs = statement.executeQuery(s"SELECT * FROM Book WHERE title='$title';")
    if (rs.next) {
      val title = rs.getString("title")
      val author = rs.getString("author")
      val isbn = rs.getString("isbn")
      val publication = rs.getString("publication")
      val avatar = rs.getString("avatar")
      return  Array(title,author,isbn,publication,avatar)
    }
    else
      return Array()
  }

  def updateBook(book:Book): Int = {
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"UPDATE Book SET title='${book._title}', author='${book._author}', isbn=${book._isbn}," +
                              s" publication='${book._publication}', avatar='${book._avatar}' WHERE title='${book._title}';")
    return rs
  }

  def getBooks(): ListBuffer[Book] ={
    var book:ListBuffer[Book] = ListBuffer()
    val statement = connection.createStatement()
    val rs = statement.executeQuery(s"SELECT * FROM Book LIMIT 9;")
    while (rs.next) {
      val title = rs.getString("title")
      val author = rs.getString("author")
      val isbn = rs.getString("isbn")
      val pub = rs.getString("publication")
      val avatar = rs.getString("avatar")
      book += Book(title, author, isbn, pub, avatar)
    }
    return book
  }

  def isertBook(book:Book): Int ={
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"INSERT INTO Book (title,author,isbn,publication,avatar) " +
                              s"VALUES ('${book._title}', '${book._author}', '${book._isbn}', '${book._publication}', '${book._avatar}');")
    return rs
  }

  def deleteBook(book:Book): Int ={
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"DELETE FROM Book WHERE title='${book._title}';")
    return rs
  }

//  this.connection.close()
}