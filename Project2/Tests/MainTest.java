import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainTest {

  private final InputStream originalSystemIn = System.in;
  private final String originalTodoListFile = "res/todoList.txt";
  private final String testTodoListFile = "res/test_todoList.txt";

  @BeforeEach
  public void setUp() throws IOException {

    copyFile(originalTodoListFile, testTodoListFile);
  }

  @BeforeEach
  public void setUpStreams() {
    TodoList testTodoList = new TodoList();
    testTodoList.addTask(new TodoTask("Test Task 1"));
    testTodoList.addTask(new TodoTask("Test Task 2"));
    testTodoList.addTask(new TodoTask("Test Task 3"));
    testTodoList.saveToFile(testTodoListFile);
  }

  private void copyFile(String sourcePath, String destinationPath) throws IOException {
    try (
        InputStream source = new FileInputStream(sourcePath);
        OutputStream destination = new FileOutputStream(destinationPath)
    ) {
      byte[] buffer = new byte[1024];
      int length;
      while ((length = source.read(buffer)) > 0) {
        destination.write(buffer, 0, length);
      }
    }
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
    assertFalse(Main.taskDescriptionExists(todoList, "Несуществующая задача"));
  }

  @Test
  public void testListTasks() {
    List<Task> tasks = new ArrayList<>();
    LocalDateTime taskTime = LocalDateTime.of(2023, Month.DECEMBER, 31, 12, 0);
    tasks.add(new TodoTask("Купить продукты", taskTime));
    tasks.add(new TodoTask("Приготовить завтрак", taskTime));
    tasks.add(new TodoTask("Выучить Java", taskTime));
    TodoList todoList = new TodoList(tasks);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream originalSystemOut = System.out;
    System.setOut(printStream);
    Main.listTasks(todoList);
    System.setOut(originalSystemOut);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    String formattedTime = taskTime.format(formatter);
    String expectedOutput = String.format("Список дел:%n" +
            "1. [Не выполнено] Купить продукты (Время: %s)%n" +
            "2. [Не выполнено] Приготовить завтрак (Время: %s)%n" +
            "3. [Не выполнено] Выучить Java (Время: %s)%n",
        formattedTime, formattedTime, formattedTime);
    assertEquals(expectedOutput, outputStream.toString());
  }

  @Test
  public void testAddTask() {
    String input = "Test Task 3\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    TodoList todoList = TodoList.readFromFile(testTodoListFile);
    Scanner scanner = new Scanner(System.in);

    Main.addTask(scanner, todoList);

    assertEquals(4, todoList.getTasks().size());
  }

  @Test
  public void testCreateNewTask() {
    String description = "Описание новой задачи";
    Task newTask = Main.createNewTask(description);

    assertEquals(description, newTask.getDescription());

    assertFalse(newTask.isDone());
  }

  @Test
  public void testSaveAndPrintCompletedTasks() {
    TodoList todoList = TodoList.readFromFile(testTodoListFile);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream, true));

    Main.saveAndPrintCompletedTasks(todoList);

    System.setOut(originalOut);

    String output = outputStream.toString();

    assertTrue(output.contains("1. [Выполнено] Приготовить завтрак"));

    TodoList originalTodoList = TodoList.readFromFile(originalTodoListFile);
    TodoList modifiedTodoList = TodoList.readFromFile(testTodoListFile);

    assertEquals(originalTodoList.getTasks().size(), modifiedTodoList.getTasks().size());

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
