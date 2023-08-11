import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("TaskAndTodoTask is works:")
class TaskAndTodoTaskTest {

  @Test
  public void testGetCompletionStatus_NotDone() {
    TodoTask task = new TodoTask("Test Task");
    assertEquals(0, task.getCompletionStatus());
  }

  @Test
  public void testGetCompletionStatus_Done() {
    TodoTask task = new TodoTask("Test Task");
    task.markAsDone();
    assertEquals(1, task.getCompletionStatus());
  }

  @Test
  public void testTodoTaskCreation_DefaultTime() {
    String description = "Test Task";
    TodoTask task = new TodoTask(description);
    assertEquals(description, task.getDescription());
    assertEquals(false, task.isDone());
    assertEquals(LocalDateTime.now().withNano(0), task.getTime().withNano(0));
  }

  @Test
  public void testMarkAsDone_NotDone() {
    TodoTask task = new TodoTask("Test Task");
    task.markAsDone();
    assertTrue(task.isDone());
    assertEquals(1, task.getCompletionStatus());
    assertNotNull(task.getTime());
  }

  @Test
  public void testMarkAsDone_AlreadyDone_StatusAndTimeUnchanged() {//Проверка, что статус и время не меняются, если задача уже выполнена.
    TodoTask task = new TodoTask("Test Task");
    task.markAsDone(); // Отметить как выполненное один раз
    LocalDateTime initialTime = task.getTime();
    task.markAsDone(); // Отметить как выполненное еще раз
    assertTrue(task.isDone());
    assertEquals(1, task.getCompletionStatus());
    assertEquals(initialTime, task.getTime()); // Время не должно меняться при повторной маркировке
  }

  @Test
  public void testMarkAsDone_NotDone_StatusAndTimeChanged() {//Проверка, что статус и время меняются, если задача не была выполнена ранее
    TodoTask task = new TodoTask("Test Task");
    LocalDateTime initialTime = task.getTime();
    task.markAsDone(); // Отметить как выполненное
    assertTrue(task.isDone());
    assertEquals(1, task.getCompletionStatus());
    assertTrue(
        initialTime.isEqual(task.getTime())); // Проверка на равенство времен без учета миллисекунд
  }

  @Test
  public void testTodoTaskMarkAsDone() {
    TodoTask task = new TodoTask("Test Task");
    task.markAsDone();
    assertEquals(true, task.isDone());
    assertEquals(LocalDateTime.now().withNano(0), task.getTime().withNano(0));
  }

  @DisplayName("TaskCreation() is works:")
  @Nested
  class testTodoTaskCreation {

    @Test
    public void withTime_Description_Matches() {//Проверка, что описание задачи совпадает.
      String description = "Test Task";
      LocalDateTime time = LocalDateTime.now();
      TodoTask task = new TodoTask(description, time);
      assertEquals(description, task.getDescription());
    }

    @Test
    public void withTime_Not_Done() {//Проверка, что задача не выполнена.
      String description = "Test Task";
      LocalDateTime time = LocalDateTime.now();
      TodoTask task = new TodoTask(description, time);
      assertFalse(task.isDone());
    }

    @Test
    public void withTime_Time_Matches() {//Проверка, что указанное время совпадает.
      String description = "Test Task";
      LocalDateTime time = LocalDateTime.now();
      TodoTask task = new TodoTask(description, time);
      assertEquals(time, task.getTime());
    }
  }
}
