import org.sql2o.*;
import java.util.List;

public class User {
  private String name;
  private String phone;
  private int id;


  public User(String name, String phone){
    this.name = name;
    this.phone = phone;
  }

  public String getName(){
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public int getId() {
    return id;
  }

  public List<Book> checkedOutBooks(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT book_id FROM checkouts WHERE user_id = :id";
      return con.createQuery(sql).addParameter("id", this.id).executeAndFetch(Book.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO users (name, phone) VALUES (:name, :phone)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("phone", this.phone)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<User> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM users;";
      return con.createQuery(sql).executeAndFetch(User.class);
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof User)){
      return false;
    } else {
      User newUser = (User) obj;
      return newUser.getName().equals(this.getName()) &&
      newUser.getPhone().equals(this.getPhone());
    }
  }

  public static User find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql= "SELECT * FROM users WHERE id = :id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(User.class);
    }
  }

  public void delete(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM users WHERE id = :id;";
      con.createQuery(sql).addParameter("id", this.getId()).executeUpdate();
    }
  }

  // public void checkout(Book book){
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "UPDATE checkouts SET user_id = :user_id && book_id = :book_id;";
  //     con.createQuery(sql)
  //     .addParameter("user_id", this.id)
  //     .addParameter("book_id", book.getId())
  //     .executeUpdate();
  //   }
  // }

  // public static List<User> allCheckouts(){
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "SELECT book_id, user_id FROM checkouts;";
  //     return con.createQuery(sql).executeAndFetch(User.class);
  //   }
  // }


//   public Book getBook(int bookId){
//     try(Connection con = DB.sql2o.open()){
//       String sql = "SELECT * FROM checkouts WHERE user_id = :user_id AND WHERE id = :id;";
//       return con.createQuery(sql)
//       .addParameter("user_id", this.id)
//       .addParameter("id", bookId)
//       .executeAndFetchFirst(Book.class);
//     }
//   }
}
