package controller

import scalafx.Includes._
import model.Book
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TableColumn, TableView, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{AnchorPane, GridPane, HBox, VBox}
import scalafx.scene.text.Text
import scalafx.stage.FileChooser
import scalafxml.core.{FXMLView, NoDependencyResolver}
import scalafxml.core.macros.sfxml

@sfxml
class BooksAdmin (
                   private val title_fld: TextField,
                   private val author_fld: TextField,
                   private val isbn_fld: TextField,
                   private val publication_fld: TextField,
                   private val avatar_fld: TextField,
                   private val searsh_fld: TextField,
                   private val delete_btn: Button,
                   private val update_btn: Button,
                   private val insert_btn: Button,
                   private val back_btn: Button,
                   private val choose_btn: Button,
                   private val searsh_btn: Button,
                   private var books_pane1: HBox,
                   private var books_pane2: HBox,
                   private val error_lbl: Label
                  ){

  showBooks()

  def showBooks(): Unit ={

    books_pane1.getChildren.clear()
    books_pane2.getChildren.clear()

    val books = ConnectDB.getBooks()
    var i = 0
    for (book <- books){
      if (i<=4)
        addBook(book,books_pane1)
      else
        addBook(book,books_pane2
        )
      i+=1
    }
  }


  def addBook(book:Book, pane:HBox): Unit ={
    val image = new ImageView {
      image = new Image(book._avatar)
      fitWidth = 200
      preserveRatio = true
      smooth = true
    }
    image.setFitHeight(165)
    val root = new VBox()
    root.children += image
    val title = new Text(book._title)
    title.setStyle("-fx-font-size: 20px; -fx-text-fill:#227a5a; ")
    root.children += title
    val author = new Text(book._author)
    author.setStyle("-fx-font-size: 16px;")
    root.children += author
    val isbn = new Text(book._isbn.toString())
    isbn.setStyle("-fx-font-size: 14px;")
    root.children += isbn
    val pub = new Text("Date: "+book._publication)
    pub.setStyle("-fx-font-size: 14px;")
    root.children += pub
    pane.children.add(root)
  }

  def deleteBook(e:ActionEvent): Unit ={

    val title = title_fld.getText()
    val author = author_fld.getText()
    val isbn = isbn_fld.getText()
    val publication = publication_fld.getText()
    val avatar = avatar_fld.getText()
    val book = new Book(title,author,isbn,publication,avatar)

    val r = ConnectDB.deleteBook(book)

    if (r != 0) {
      error_lbl.setText("Book Deleted Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #07b74b;")
      showBooks()
    } else {
      error_lbl.setText("Fieled to Delete Book !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }


  }

  def updateBook(e:ActionEvent): Unit ={
    val title = title_fld.getText()
    val author = author_fld.getText()
    val isbn = isbn_fld.getText()
    val publication = publication_fld.getText()
    val avatar = avatar_fld.getText()
    val book = new Book(title, author, isbn, publication, avatar)

    val r = ConnectDB.updateBook(book)
    if (r != 0) {
      error_lbl.setText("Book Updated Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #07b74b;")
      showBooks()
    } else {
      error_lbl.setText("Fieled to update Book !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

  }

  def insertBook(e:ActionEvent): Unit ={
    val title = title_fld.getText()
    val author = author_fld.getText()
    val isbn = isbn_fld.getText()
    val publication = publication_fld.getText()
    val avatar = avatar_fld.getText()
    val book = new Book(title, author, isbn, publication, avatar)

    val r = ConnectDB.isertBook(book)
    if (r != 0) {
      error_lbl.setText("Book Inserted Sucessfully !")
      error_lbl.setStyle("-fx-text-fill: #07b74b;")
    } else {
      error_lbl.setText("Fieled to Inset Book !")
      error_lbl.setStyle("-fx-text-fill: #d02c2c;")
    }

    showBooks()
  }

  def toManer(e:ActionEvent): Unit ={
    val root = FXMLView(getClass.getResource("../fxml/Maner.fxml"), NoDependencyResolver)
    val scene = new Scene(root)
    App.show("Amin Maner", scene)
  }

  def searshBook(e:ActionEvent): Unit ={
    val book = ConnectDB.searhBook(searsh_fld.getText())
    title_fld.setText(book(0))
    author_fld.setText(book(1))
    isbn_fld.setText(book(2))
    publication_fld.setText(book(3))
    avatar_fld.setText(book(4))

  }

  def chooseImage(e:ActionEvent): Unit ={
      val fileChooser = new FileChooser()
      fileChooser.title = "Select Image"
      fileChooser.extensionFilters.addAll(
        new FileChooser.ExtensionFilter("Images", Seq("*.png", "*.jpg", "*.jpeg", "*.bmp"))
      )
      val selectedFile = fileChooser.showOpenDialog(App.stage)

      if (selectedFile != null) {
        avatar_fld.setText(selectedFile.toURI.toString())
      }
  }
}

