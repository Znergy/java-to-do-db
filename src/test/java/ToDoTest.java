import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;


public class ToDoTest {

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(sql).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test2", null, null);
  }

  // overriding equals
  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    ToDo firstTask = new ToDo("Mow the lawn", 1);
    ToDo secondTask = new ToDo("Mow the lawn", 2);
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    ToDo myTask = new ToDo("Mow the lawn", 1);
    myTask.save();
    assertTrue(ToDo.all().get(0).equals(myTask));
  }

  @Test
  public void all_returnsAllInstancesOfTask_true() {
    ToDo firstTask = new ToDo("Mow the lawn", 1);
    firstTask.save();
    ToDo secondTask = new ToDo("Buy groceries", 2);
    secondTask.save();
    assertEquals(true, ToDo.all().get(0).equals(firstTask));
    assertEquals(true, ToDo.all().get(1).equals(secondTask));
  }

  @Test
  public void save_assignsIdToObject() {
    ToDo myTask = new ToDo("Mow the lawn", 1);
    myTask.save();
    ToDo savedTask = ToDo.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void getId_tasksInstantiateWithAnID() {
    ToDo myTask = new ToDo("Mow the lawn", 1);
    myTask.save();
    assertTrue(myTask.getId() > 0);
  }

  @Test
  public void find_returnsTaskWithSameId_secondTask() {
    ToDo firstTask = new ToDo("Mow the lawn", 1);
    firstTask.save();
    ToDo secondTask = new ToDo("Buy groceries", 2);
    secondTask.save();
    assertEquals(ToDo.find(secondTask.getId()), secondTask);
  }

  @Test
  public void save_savesCategoryIdIntoDB_true(){
    Category testCategory = new Category("Epicodus");
    testCategory.save();
    ToDo testTask = new ToDo("Eat lunch", testCategory.getId());
    testTask.save();
    ToDo retrievedTask = ToDo.find(testTask.getId());
    assertEquals(retrievedTask.getCategoryId(), testCategory.getId());

  }



  // @Test
  // public void ToDoItem_instantiatesCorrectly_true() {
  //   ToDo myTask = new ToDo("Mow the lawn");
  //   assertEquals(true, myTask instanceof ToDo);
  // }
  // @Test
  // public void ToDoItem_createsDescription_String() {
  //   ToDo myTask = new ToDo("Mow the lawn");
  //   assertEquals("Mow the lawn", myTask.getDescription());
  // }
  // @Test
  // public void isCompleted_isFalseAfterInstantiation_false() {
  //   ToDo myTask = new ToDo("Mow the lawn");
  //   assertEquals(false, myTask.isCompleted());
  // }
  // // test for date and timestamp
  // @Test
  // public void getCreatedAt_instantiatesWithCurrentTime_today() {
  //   ToDo myTask = new ToDo("Mow the lawn");
  //   assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  // }
  //
  // @Test
  // public void all_returnsAllInstancesOfTask_true() {
  //   ToDo firstTask = new ToDo("Mow the lawn");
  //   ToDo secondTask = new ToDo("Buy groceries");
  //   assertEquals(true, ToDo.all().contains(firstTask));
  //   assertEquals(true, ToDo.all().contains(secondTask));
  // }
  // // clear arraylist
  // @Test
  // public void clear_emptiesAllTasksFromArrayList_0() {
  //   ToDo myTask = new ToDo("Mow the lawn");
  //   assertEquals(ToDo.all().size(), 0);
  // }
  // // test if assigning id is working
  // @Test
  // public void getId_tasksInstantiateWithAnID_1() {
  //   ToDo myTask = new ToDo("Mow the lawn");
  //   assertEquals(1, myTask.getId());
  // }
  // //
  // @Test
  // public void find_returnsTaskWithSameId_secondTask() {
  //   ToDo firstTask = new ToDo("Mow the lawn");
  //   ToDo secondTask = new ToDo("Buy groceries");
  //   assertEquals(ToDo.find(secondTask.getId()), secondTask);
  // }


}
