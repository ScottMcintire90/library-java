import java.util.*;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());

    get("/books", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("books", Book.all());
      model.put("template", "templates/books.vtl");
      return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());

    get("/users", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("users", User.all());
      model.put("template", "templates/users.vtl");
      return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());

    get("/users/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":id")));
      model.put("user", user);
      model.put("books", Book.all());
      model.put("template", "templates/user.vtl");
      return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());

    post("/users/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int bookId = Integer.parseInt(request.queryParams("book_id"));
      User user = User.find(Integer.parseInt(request.params(":id")));
      Book newBook = Book.find(bookId);
      newBook.checkout(user);
      model.put("user", user);
      model.put("checks", user.checkedOutBooks();
      response.redirect("/users/" + user.getId());
      System.out.println(user.checkedOutBooks());
      return null;
      });

    get("/books/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      model.put("book", book);
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, "templates/layout.vtl");
    }, new VelocityTemplateEngine());

    get("/books/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      book.delete();
      response.redirect("/books");
      return null;
      });

    get("/users/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      User user = User.find(Integer.parseInt(request.params(":id")));
      user.delete();
      response.redirect("/users");
      return null;
      });

    post("/books", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String bookTitle = request.queryParams("bookTitle");
      String bookAuthor = request.queryParams("bookAuthor");
      Book newBook = new Book(bookTitle, bookAuthor, 2);
      newBook.save();
      response.redirect("/books");
      return null;
    });

    post("/users", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String userName = request.queryParams("userName");
      String userPhone = request.queryParams("userPhone");
      User newUser = new User(userName, userPhone);
      newUser.save();
      response.redirect("/users");
      return null;
    });
  }
}
