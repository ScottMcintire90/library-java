// import org.sql2o.*;
// import org.junit.*;
// import static org.junit.Assert.*;
//
// public class CheckoutTest{
//
//   @Before
//   public void setUp(){
//     DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
//   }
//
//   @After
//   public void teardown(){
//     try(Connection con = DB.sql2o.open()){
//       String sql = "DELETE FROM checkouts *";
//       con.createQuery(sql).executeUpdate();
//     }
//   }
//
//   @Test
//   public void Checkout_CheckoutisSavedtoDataBase_true(){
//     Book newBook = new Book("Bills book", "Bob", 12);
//     newBook.save();
//     User newUser = new User("Bill", "912-141-1411");
//     newUser.save();
//     Checkout newCheckout = new Checkout(newUser, newBook);
//     newCheckout.save();
//     assertTrue(Checkout.all().size() == 1);
//   }
// }
