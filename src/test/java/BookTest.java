import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class BookTest{

  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/library_test", null, null);
  }

  @After
  public void teardown(){
    try(Connection con = DB.sql2o.open()){
      String sql = "DELETE FROM books *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Book_BookInstantiates_true(){
    Book newBook = new Book("Bills Book", "Bob", 1);
    assertTrue(newBook instanceof Book);
  }

  @Test
  public void Book_BookInstantiatesWithName_Bill(){
    Book newBook = new Book("Bills Book", "Bob", 1);
    assertEquals("Bills Book", newBook.getTitle());
  }

  @Test
  public void Book_BookisSavedtoDataBase_true(){
    Book newBook = new Book("Bills Book", "Bob", 1);
    newBook.save();
    assertTrue(Book.all().size() == 1);
  }

  @Test
  public void Book_BookObjectsEqual() {
    Book firstBook = new Book("Bills Book", "Bob", 1);
    Book secondBook = new Book("Bills Book", "Bob", 1);
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void Book_FindBookData() {
    Book newBook = new Book("Bills Book", "Bob", 1);
    newBook.save();
    assertTrue(newBook.equals(Book.find(newBook.getId())));
  }

  @Test
  public void Book_DeletesBookFromDataBase() {
    Book newBook = new Book("Bills Book", "Bob", 1);
    newBook.save();
    newBook.delete();
    assertEquals(null, Book.find(newBook.getId()));
  }

  @Test
  public void Book_BookIsCheckedOut() {
    Book newBook = new Book("Bills Book", "Bob", 1);
    newBook.save();
    User newUser = new User("Bill", "122-133-1311");
    newUser.save();
    Book savedBook = Book.find(newBook.getId());
    savedBook.checkout(newUser);
    assertEquals(true, savedBook.isCheckedOut());
  }
}
