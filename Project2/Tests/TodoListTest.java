import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
  void saveToFile() {
  }

  @Test
  void readFromFile() {
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

    // IOException exception = assertThrows(IOException.class,
    //     () -> TodoList.readFromFile(testFilename));
    //  assertEquals("Ожидается ввод команды", exception.getMessage());

  }

 /* @Test
  public void testSaveAndReadFromFile() {
    TodoList originalList = new TodoList();
    originalList.addTask(new TodoTask("Task 1", LocalDateTime.now()));
    originalList.addTask(new TodoTask("Task 2", LocalDateTime.now()));
    originalList.addTask(new TodoTask("Task 3", LocalDateTime.now()));
    String filename = "test_todo_list.txt";
    // Save to file
    originalList.saveToFile(filename);
    // Read from file
    TodoList loadedList = TodoList.readFromFile(filename);
    // Compare
    assertEquals(originalList.getTasks().size(), loadedList.getTasks().size());
    for (int i = 0; i < originalList.getTasks().size(); i++) {
      Task originalTask = originalList.getTasks().get(i);
      Task loadedTask = loadedList.getTasks().get(i);
      assertEquals(originalTask.getDescription(), loadedTask.getDescription());
      assertEquals(originalTask.getCompletionStatus(), loadedTask.getCompletionStatus());
      assertEquals(originalTask.getTime(), loadedTask.getTime());
    }
  }

  */

  // @DisplayName("compareTo() is works:")
  // @Nested
  // class TestsForCompareTo {

  /*  @Test
    public void testCompareTo() {
      // Создание задач
     // Task task1 = new Task("Task 1", true, LocalDateTime.parse("2023-08-11T10:00:00"));
     // Task task2 = new Task("Task 2", false, LocalDateTime.parse("2023-08-12T14:30:00"));
      //Task task3 = new Task("Task 2",);
      // Создание списков дел
      TodoList todoList1 = new TodoList(Collections.singletonList(task1));
      TodoList todoList2 = new TodoList(Collections.singletonList(task2));
      // Проверка сравнения
      int result = todoList1.compareTo(todoList2);
      // Убедитесь, что результат соответствует ожидаемому значению
      assertTrue(result < 0, "Сравнение должно вернуть отрицательное число");
    }

   */
  // }
}
