import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
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
    //  создать тестовый TodoList и сохранить его в файл
    TodoList testTodoList = new TodoList();
    testTodoList.addTask(new TodoTask("Test Task 1"));
    testTodoList.addTask(new TodoTask("Test Task 2"));
    testTodoList.saveToFile(testTodoListFile);
  }

  @AfterEach
  public void restoreStreams() {
    System.setIn(originalSystemIn);
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
    // Создаем объект TodoList и добавляем задачи с разными временами
    TodoList todoList = new TodoList();
    todoList.addTask(new TodoTask("Task 1", LocalDateTime.of(2023, 8, 1, 10, 0)));
    todoList.addTask(new TodoTask("Task 2", LocalDateTime.of(2023, 8, 1, 8, 0)));
    todoList.addTask(new TodoTask("Task 3", LocalDateTime.of(2023, 8, 1, 12, 0)));

    // Вызываем метод sortTasksByTime
    Main.sortTasksByTime(todoList);

    // Получаем список задач и проверяем, что он отсортирован по времени
    List<Task> tasks = todoList.getTasks();
    for (int i = 0; i < ((List<?>) tasks).size() - 1; i++) {
      assertTrue(tasks.get(i).getTime().isBefore(tasks.get(i + 1).getTime()));
    }
  }
}
