import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
  void compareTo() {
  }
}