import java.util.*;
import org.sql2o.*;



public class Category {
  private String name;
  private int id;

  public Category(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object secondCategory){
    if(!(secondCategory instanceof Category)){
      return false;
    }
    Category newCategory = (Category) secondCategory;
    return (this.getName().equals( newCategory.getName()) &&
            this.getId() == newCategory.getId()
            );
  }

  public static List<Category> all(){
    String sql = "SELECT * FROM categories;";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Category.class);
    }
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sqlCommand = "INSERT INTO categories (name) VALUES (:name);";
      this.id = (int) con.createQuery(sqlCommand, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  // public List<Task> getTasks(){
  //
  // }
  //
  public static Category find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sqlCommand = "SELECT * FROM categories WHERE id=:id";
      Category returnCategory = con.createQuery(sqlCommand)
        .addParameter("id", id)
        .executeAndFetchFirst(Category.class);
      return returnCategory;
    }
  }

}
