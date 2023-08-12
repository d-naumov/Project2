import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

  private final InputStream originalSystemIn = System.in;
  private final String testTodoListFile = "test_todoList.txt";


  @BeforeEach
  public void setUpStreams() {
    TodoList testTodoList = new TodoList();
    testTodoList.addTask(new TodoTask("Test Task 1"));
    testTodoList.addTask(new TodoTask("Test Task 2"));
    testTodoList.addTask(new TodoTask("Test Task 3"));
    testTodoList.saveToFile(testTodoListFile);
  }

  @AfterEach
  public void restoreStreams() {
    System.setIn(originalSystemIn);
  }
  @Test
  public void testContainsDigits() {
    String stringWithDigits = "This has 123 digits.";
    assertTrue(Main.containsDigits(stringWithDigits));
    String stringWithoutDigits = "No digits here.";
    assertFalse(Main.containsDigits(stringWithoutDigits));
  }

  @Test
  public void testTaskDescriptionExists() {

    TodoList todoList = new TodoList();
    todoList.addTask(new TodoTask("Task 1"));

    assertTrue(Main.taskDescriptionExists(todoList, "Task 1"));
    assertFalse(Main.taskDescriptionExists(todoList, "Non-existent Task"));
  }

  @Test
  public void testAddTask() {
    String input = "Test Task 3\n";
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    TodoList todoList = TodoList.readFromFile(testTodoListFile);
    Scanner scanner = new Scanner(System.in);

    Main.addTask(scanner, todoList);

    assertEquals(3, todoList.getTasks().size());
  }

  @Test
  public void testMarkTaskAsDone() {
    TodoList todoList = new TodoList();
    TodoTask task = new TodoTask("Test Task");
    todoList.addTask(task);

    String input = "1\n";
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

    Scanner scanner = new Scanner(System.in);
    Main.markTaskAsDone(scanner, todoList);

    assertTrue(task.isDone());
  }

  @Test
  public void testSortTasksByTime() {

    TodoList todoList = new TodoList();
    todoList.addTask(new TodoTask("Task 1", LocalDateTime.of(2023, 8, 1, 10, 0)));
    todoList.addTask(new TodoTask("Task 2", LocalDateTime.of(2023, 8, 1, 8, 0)));
    todoList.addTask(new TodoTask("Task 3", LocalDateTime.of(2023, 8, 1, 12, 0)));

    Main.sortTasksByTime(todoList);
    List<Task> tasks = todoList.getTasks();

    IntStream.range(0, tasks.size() - 1)
        .forEach(i -> assertTrue(tasks.get(i).getTime().isBefore(tasks.get(i + 1).getTime())));
  }

  }

