import org.sql2o.*;
import java.util.List;

public class Book {
  private String title;
  private String author;
  private int copies;
  private int id;
  private Boolean checkedOut;
  private int userId;
  private String due_date = "2 weeks";

  public Book(String title, String author, int copies){
    this.title = title;
    this.author = author;
    this.copies = copies;
    checkedOut = false;
    this.userId = 0;
    this.due_date = due_date;
  }

  public String getTitle(){
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getDueDate() {
    return due_date;
  }

  public int getUserId() {
    return userId;
  }

  public int getId() {
    return id;
  }

  public int getCopies() {
    return copies;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO books (title, author, copies) VALUES (:title, :author, :copies);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .addParameter("author", this.author)
      .addParameter("copies", this.copies)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<Book> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM books;";
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Book)){
      return false;
    } else {
      Book newBook = (Book) obj;
      return newBook.getTitle().equals(this.getTitle()) &&
      newBook.getAuthor().equals(this.getAuthor()) && (newBook.getCopies() == (this.getCopies()));
    }
  }

  public void checkout(User user) {
    this.userId = user.getId();
    try(Connection con = DB.sql2o.open()) {
      String sql1 = "INSERT INTO checkouts (book_id, user_id) VALUES (:book_id, :user_id);";
      con.createQuery(sql1)
        .addParameter("book_id", id)
        .addParameter("user_id", user.getId())
        .executeUpdate();
    }
  }

  public boolean isCheckedOut() {
    if (userId == 0) {
      return false;
    } else {
      return true;
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql= "SELECT * FROM books WHERE id = :id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Book.class);
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id = :id;";
      con.createQuery(sql).addParameter("id", this.getId()).executeUpdate();
    }
  }
}
