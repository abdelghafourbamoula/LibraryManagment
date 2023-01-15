package controller

import sun.security.util.Password

import java.sql.{Connection, DriverManager}

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

  def close(): Unit ={
    this.connection.close()
  }

  def loginAdmin(username:String, password: String): Boolean = {

      val statement = connection.createStatement()
      val rs = statement.executeQuery("SELECT * FROM Admin WHERE username='"+username+"' AND password='"+password+"';")
      if (rs.next)
        return true
      else
        return false

//      this.close()
  }

}