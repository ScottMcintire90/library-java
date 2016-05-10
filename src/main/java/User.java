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

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO users (name, phone) VALUES (:name, :phone);";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .addParameter("phone", this.phone)
      .executeUpdate()
      .getKey();
    }
  }

  public static List<User> all(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT name, phone FROM users;";
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
      String sql= "SELECT id, name, phone FROM users WHERE id = :id;";
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(User.class);
    }
  }
}
