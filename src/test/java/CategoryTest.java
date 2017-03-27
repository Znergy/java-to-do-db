import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;


public class CategoryTest {

  @Before
  public void setUp(){
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/to_do_test2", null, null);
  }

  @After
  public void tearDown(){
    try(Connection con = DB.sql2o.open()){
      String deleteTaskQuery = "DELETE FROM tasks *;";
      String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteTaskQuery).executeUpdate();
      con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame(){
    Category testCategory = new Category("epicodus");
    // testCategory.save();
    Category testCategory2 = new Category("epicodus");

    assertTrue(testCategory.equals(testCategory2));
  }

  @Test
  public void save_savesIntoDatabase_true(){
    Category testCategory = new Category("chores");
    testCategory.save();
    Category retrievedCategory = Category.all().get(0);
    assertTrue(testCategory.equals(retrievedCategory));
  }



  // @Test
  // public void category_instantiatesCorrectly_true() {
  //   Category testCategory = new Category("Home");
  //   assertEquals(true, testCategory instanceof Category);
  // }
  //
  // @Test
  // public void getName_categoryInstantiatesWithName_Home() {
  //   Category testCategory = new Category("Home");
  //   assertEquals("Home", testCategory.getName());
  // }
  //
  @Test
  public void all_returnsAllInstancesOfCategory_true() {
    Category firstCategory = new Category("Home");
    firstCategory.save();
    Category secondCategory = new Category("Work");
    secondCategory.save();
    assertEquals(true, Category.all().get(0).equals(firstCategory));
    assertEquals(true, Category.all().get(1).equals(secondCategory));
  }

  @Test
  public void save_assignIdToObject(){
    Category testCategory = new Category("groceries");
    testCategory.save();
    Category retrievedCategory = Category.all().get(0);
    assertEquals(testCategory.getId(), retrievedCategory.getId());
  }

  //
  // @Test
  // public void clear_emptiesAllCategoriesFromList_0() {
  //   Category testCategory = new Category("Home");
  //   Category.clearList();
  //   assertEquals(Category.all().size(), 0);
  // }
  //
  @Test
  public void getId_categoriesInstantiateWithAnId_int() {
    Category testCategory = new Category("Home");
    testCategory.save();
    assertTrue(testCategory.getId() > 0);
  }
  //
  @Test
  public void find_returnsCategoryWithSameId_secondCategory() {
    Category firstCategory = new Category("Home");
    firstCategory.save();
    Category secondCategory = new Category("Work");
    secondCategory.save();
    assertEquals(Category.find(secondCategory.getId()), secondCategory);
  }

  @Test
  public void getTasks_retrievesAllTasksFromDatabase_taskList(){
    Category testCategory = new Category("Code school");
    testCategory.save();
    ToDo firstTask = new ToDo ("Grow plants", testCategory.getId());
    firstTask.save();
    ToDo secondTask = new ToDo ("Wash dishes", testCategory.getId());
    secondTask.save();
    ToDo[] tasks = new ToDo[] {firstTask, secondTask};
    assertTrue(testCategory.getTasks().containsAll(Arrays.asList(tasks)));
  }
  //
  // @Test
  // public void getTasks_initiallyReturnsEmptyList_ArrayList() {
  //   Category testCategory = new Category("Home");
  //   assertEquals(0, testCategory.getTasks().size());
  // }
  //
  // @Test
  // public void addTask_addsTaskToList_true() {
  //   Category testCategory = new Category("Home");
  //   ToDo testTask = new ToDo("Mow the lawn");
  //   testCategory.addTask(testTask);
  //   assertTrue(testCategory.getTasks().contains(testTask));
  // }
  //
  // @Test
  // public void find_returnsNullWhenNoTaskFound_null() {
  //   assertTrue(Category.find(999) == null);
  // }
}
