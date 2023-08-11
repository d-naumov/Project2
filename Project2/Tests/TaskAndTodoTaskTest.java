import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

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
  public void testMarkAsDone_AlreadyDone() {
    TodoTask task = new TodoTask("Test Task");
    task.markAsDone(); // Mark as done once
    LocalDateTime initialTime = task.getTime();
    task.markAsDone(); // Mark as done again
    assertTrue(task.isDone());
    assertEquals(1, task.getCompletionStatus());
    assertEquals(initialTime, task.getTime()); // Time should not change when marking done again
  }

  @Test
  public void testTodoTaskCreation_WithTime() {
    String description = "Test Task";
    LocalDateTime time = LocalDateTime.now();
    TodoTask task = new TodoTask(description, time);
    assertEquals(description, task.getDescription());
    assertEquals(false, task.isDone());
    assertEquals(time, task.getTime());
  }

  @Test
  public void testTodoTaskMarkAsDone() {
    TodoTask task = new TodoTask("Test Task");
    task.markAsDone();
    assertEquals(true, task.isDone());
    assertEquals(LocalDateTime.now().withNano(0), task.getTime().withNano(0));
  }
}
