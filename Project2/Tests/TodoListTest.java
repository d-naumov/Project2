import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("TodoList is works:")
class TodoListTest {

  private TodoList todoList;
  private final String testFilename = "testTodoList.txt";

  @BeforeEach
  void setUp() {
    List<Task> tasks = new ArrayList<>();
    tasks.add(new TodoTask("Купить продукты"));
    tasks.add(new TodoTask("Приготовить завтрак"));
    todoList = new TodoList(tasks);
  }


  @Test
  public void testAddTask() {
    Task newTask = new TodoTask("Выучить Java");
    todoList.addTask(newTask);
    assertEquals(3, todoList.getTasks().size());
  }

  @Test
  public void testGetCompletedTasks() {
    todoList.getTasks().get(0).markAsDone();
    todoList.getTasks().get(1).markAsDone();
    List<Task> completedTasks = todoList.getCompletedTasks();
    assertEquals(2, completedTasks.size());
  }

  @Test
  public void testPrintCompletedTasks() {
    ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputContent));

    todoList.getTasks().get(0).markAsDone();
    LocalDateTime done = LocalDateTime.now();

    todoList.printCompletedTasks();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String expectedOutput = String.format(
        "Проделанные дела:%n1. [Выполнено] Купить продукты (Время: %s)%n", done.format(formatter));

    assertEquals(expectedOutput, outputContent.toString());
    System.setOut(System.out);

    String originalTime = done.format(formatter);
    String readTime = todoList.getTasks().get(0).getTime().format(formatter);
    assertEquals(originalTime, readTime);
  }

  @Test
  public void testSaveAndReadFromFile() throws IOException {
    todoList.saveToFile(testFilename);
    TodoList readTodoList = TodoList.readFromFile(testFilename);
    LocalDateTime done = LocalDateTime.now();

    assertEquals(todoList.getTasks().size(), readTodoList.getTasks().size());
    assertEquals(todoList.getTasks().get(0).getDescription(),
        readTodoList.getTasks().get(0).getDescription());
    assertEquals(todoList.getTasks().get(1).getDescription(),
        readTodoList.getTasks().get(1).getDescription());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    String originalTime = done.format(formatter);
    String readTime = todoList.getTasks().get(0).getTime().format(formatter);
    assertEquals(originalTime, readTime);

  }

  @DisplayName("compareTo() is works:")
  @Nested
  class TestsForCompareTo {

    @Test
    void same_Done_Status() {
      LocalDateTime now = LocalDateTime.now();
      TodoTask task1 = new TodoTask("Task 1", now);
      TodoTask task2 = new TodoTask("Task 2", now.plusHours(1));

      task1.markAsDone();
      task2.markAsDone();

      TodoList todoList1 = new TodoList(List.of(task1));
      TodoList todoList2 = new TodoList(List.of(task2));

      assertTrue(todoList1.compareTo(todoList2) < 0);
      assertTrue(todoList2.compareTo(todoList1) > 0);
    }

    @Test
    void different_Done_Status() {
      LocalDateTime now = LocalDateTime.now();
      TodoTask task1 = new TodoTask("Task 1", now);
      TodoTask task2 = new TodoTask("Task 2", now.plusHours(1));

      task1.markAsDone();

      TodoList todoList1 = new TodoList(List.of(task1));
      TodoList todoList2 = new TodoList(List.of(task2));

      assertTrue(todoList1.compareTo(todoList2) < 0);
      assertTrue(todoList2.compareTo(todoList1) > 0);
    }

    @Test
    void same_Done_Status_And_Time() {
      LocalDateTime now = LocalDateTime.now();
      TodoTask task1 = new TodoTask("Task 1", now);
      TodoTask task2 = new TodoTask("Task 2", now);

      task1.markAsDone();
      task2.markAsDone();

      TodoList todoList1 = new TodoList(List.of(task1));
      TodoList todoList2 = new TodoList(List.of(task2));

      assertEquals(0, todoList1.compareTo(todoList2));
      assertEquals(0, todoList2.compareTo(todoList1));
    }
  }
}
