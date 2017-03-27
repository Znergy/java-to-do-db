import java.time.LocalDateTime;
import java.util.*;
import org.sql2o.*;

public class ToDo {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;
  private int categoryId;

  public ToDo(String description, int categoryId) {
    this.description = description;
    this.completed = false;
    this.createdAt = LocalDateTime.now();
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object otherTask){
    if (!(otherTask instanceof ToDo)) {
      return false;
    } else {
      ToDo newTask = (ToDo) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
             this.getId() == newTask.getId();
    }
  }

  public int getCategoryId(){
    return categoryId;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public int getId() {
    return id;
  }

  public static List<ToDo> all() {
    String sql = "SELECT id, description FROM tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(ToDo.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO tasks(description) VALUES (:description)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .executeUpdate()
        .getKey();
    }
  }

  public static ToDo find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM tasks where id=:id";
      ToDo task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(ToDo.class);
      return task;
    }
  }
}
