import org.sql2o.*;
import java.util.List;

public class Book {
  private String title;
  private String author;
  private int copies;
  private int id;

  public Book(String title, String author, int copies){
    this.title = title;
    this.author = author;
    this.copies = copies;
  }

  public String getTitle(){
    return title;
  }

  public String getAuthor() {
    return author;
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
      String sql = "SELECT title, author, copies FROM books;";
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

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql= "SELECT id, title, author, copies FROM books WHERE id = :id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Book.class);
    }
  }

  public static void delete(Book user){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM books WHERE id = :id;";
      con.createQuery(sql).addParameter("id", user.getId()).executeUpdate();
    }
  }
}
