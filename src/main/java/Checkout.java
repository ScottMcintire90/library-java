// import org.sql2o.*;
// import java.util.List;
//
// public class Checkout {
//   private Object book;
//   private Object user;
//   private String due_date = "Two weeks";
//   private int id;
//   private int userId;
//   private int bookId;
//
//   public Checkout(User user, Book book){
//     this.book = book;
//     this.user = user;
//     this.due_date = due_date;
//   }
//
//   public int getUserId() {
//     userId = user.getId();
//     return userId;
//   }
//
//   public int getBookId() {
//     bookId = book.getId();
//     return bookId;
//   }
//
//   public static List<Checkout> all(){
//     try(Connection con = DB.sql2o.open()){
//       String sql = "SELECT id FROM checkouts;";
//       return con.createQuery(sql).executeAndFetch(Checkout.class);
//     }
//   }
//
//   public void save(){
//     try(Connection con = DB.sql2o.open()){
//       String sql = "UPDATE checkouts user_id = :user AND book_id = :book";
//       this.id = (int) con.createQuery(sql, true)
//       .addParameter("user", user.getId())
//       .addParameter("book", book.getId())
//       .executeUpdate();
//     }
//   }
// }
