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


//  this.connection.close()
}