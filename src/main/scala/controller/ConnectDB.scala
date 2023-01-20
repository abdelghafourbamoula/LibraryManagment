package controller

import model.{Book, Reservation, Student}
import scalafx.Includes._
import scalafx.collections.ObservableBuffer

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
      val rs = statement.executeQuery(s"SELECT * FROM Admin WHERE username='$username' AND password='$password';")
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
    val rs = statement.executeQuery(s"SELECT * FROM Book WHERE LOWER(title) LIKE '%$title%' OR LOWER(author) LIKE '%$title%';")
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

  def getStudents(searsh:String): ObservableBuffer[Student] = {
    var students = ObservableBuffer[Student]()
    val statement = connection.createStatement()
    var rs = statement.executeQuery(s"SELECT * FROM Student;")

    if(searsh != "")
      rs = statement.executeQuery(s"SELECT * FROM Student WHERE cne='$searsh' OR email='$searsh' OR LOWER(fullName) LIKE '%${searsh.toLowerCase()}%';")

    while (rs.next) {
      val cne = rs.getString("cne")
      val fname = rs.getString("fullName")
      val email = rs.getString("email")
      val password = rs.getString("password")
      students += Student(cne, fname, email, password)
    }
    return students
  }

  def insertStudent(student: Student): Int = {
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"INSERT INTO Student VALUES ('${student.cne}', '${student.fname}', '${student.email}', '${student.password}');")
    return rs
  }

  def updateStudent(student: Student): Int = {
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"UPDATE Student SET fullName='${student.fname}', email='${student.email}', password='${student.password}' WHERE cne='${student.cne}';")
    return rs
  }

  def deleteStudent(cne: String): Int = {
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"DELETE FROM Student WHERE cne='$cne';")
    return rs
  }

  def getReservations(returned:Boolean=false): ObservableBuffer[Reservation] ={
    var reservations = ObservableBuffer[Reservation]()
    val statement = connection.createStatement()
    var rs = statement.executeQuery(s"SELECT * FROM Reservation R JOIN Book B ON B.idbook=R.idBook WHERE R.return = '';")

    if(returned)
      rs = statement.executeQuery(s"SELECT * FROM Reservation R JOIN Book B ON B.idbook=R.idBook WHERE R.return  != '';")

    while (rs.next) {
      val idbook = rs.getString("idBook")
      val cne = rs.getString("cne")
      val book = rs.getString("title")
      val res = rs.getString("reservation")
      val ret = rs.getString("return")
      reservations += Reservation(idbook,cne,book,res,ret)
    }
    return reservations
  }

  def searshReservation(searsh:String, returned:Boolean=false): ObservableBuffer[Reservation] ={
    var reservations = ObservableBuffer[Reservation]()
    val statement = connection.createStatement()
    var op = " R.return = '' "
    if (returned)
      op =  "R.return  != '' "

    val rs = statement.executeQuery(s"SELECT * FROM Reservation R JOIN Book B ON B.idBook=R.idBook JOIN Student S ON S.cne=R.cne WHERE $op" +
                                      s"AND ( LOWER(B.title) LIKE '%${searsh.toLowerCase()}%' OR LOWER(S.fullName) LIKE '%${searsh.toLowerCase()}%' " +
                                      s" OR R.reservation LIKE '%$searsh%' OR R.cne='$searsh' ) ;")

    while (rs.next) {
      val idbook = rs.getString("idBook")
      val cne = rs.getString("cne")
      val book = rs.getString("title")
      val res = rs.getString("reservation")
      val ret = rs.getString("return")
      reservations += Reservation(idbook, cne, book, res, ret)
    }
    return reservations
  }

  def updateReservation(reserv:Reservation): Int = {
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"UPDATE Reservation SET reservation='${reserv.res}', `return`='${reserv.ret}' WHERE cne='${reserv.cne}' AND idBook IN (SELECT idBook from Book WHERE title='${reserv.book}');")

    return rs
  }

  def deleteReservation(cne:String,book:String):Int ={
    val st = connection.createStatement()
    val rs = st.executeUpdate(s"DELETE FROM Reservation WHERE cne='$cne' AND idBook IN (SELECT idBook FROM Book WHERE title='$book');")
    return rs
  }

  def getNum(tab:String):String={
    val statement = connection.createStatement()
    var rs = statement.executeQuery(s"SELECT COUNT(*) as c FROM $tab;")

    if (rs.next)
      return rs.getString("c")
    else
      return "0"
  }



  //  this.connection.close()
}