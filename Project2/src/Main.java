import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    TodoList todoList = TodoList.readFromFile("res/todoList.txt");

    while (true) {
      MenuCommand command = MenuCommand.readCommand(scanner);

      switch (command) {
        case LIST:
          sortTasksByTime(todoList);
          listTasks(todoList);
          break;
        case ADD:
          addTask(scanner, todoList);
          break;
        case MARK_DONE:
          markTaskAsDone(scanner, todoList);
          break;
        case EXIT:
          saveAndPrintCompletedTasks(todoList);
          return;
        default:
          System.out.println("Некорректная команда");
      }
    }
  }

  private static void listTasks(TodoList todoList) {
    System.out.println("Список дел:");
    List<Task> tasks = todoList.getTasks();
    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);
      String status = task.isDone() ? "[Выполнено]" : "[Не выполнено]";
      System.out.println((i + 1) + ". " + status + " " + task.getDescription() +
          " (Время: " + task.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
          + ")");
    }
  }

  private static void addTask(Scanner scanner, TodoList todoList) {
    System.out.print("Введите описание задачи: ");
    String description = scanner.nextLine();

    Task newTask = new TodoTask(description); // Не нужно указывать время
    todoList.addTask(newTask);

    System.out.println("Задача добавлена.");
  }

  /* private static void addTask(Scanner scanner, TodoList todoList) {
     System.out.print("Введите описание задачи: ");
     String description = scanner.nextLine();
     System.out.print("Введите время задачи (dd.MM.yyyy HH:mm): ");
     String timeInput = scanner.nextLine();
     LocalDateTime taskTime = LocalDateTime.parse(timeInput, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
     Task newTask = new TodoTask(description, taskTime);
     todoList.addTask(newTask);
   }

   */
  private static void markTaskAsDone(Scanner scanner, TodoList todoList) {
    System.out.print("Введите номер задачи, которую хотите пометить как выполненную: ");
    try {
      int taskNumber = scanner.nextInt();
      if (taskNumber > 0 && taskNumber <= todoList.getTasks().size()) {
        Task taskToMarkDone = todoList.getTasks().get(taskNumber - 1);
        taskToMarkDone.markAsDone();
      } else {
        System.out.println("Некорректный номер задачи.");
      }
    } catch (InputMismatchException e) {
      System.out.println("Некорректный номер задачи.");
      scanner.nextLine();
    }
  }

  private static void saveAndPrintCompletedTasks(TodoList todoList) {
    String filename = "res/todoList.txt";
    todoList.saveToFile(filename);
    todoList.printCompletedTasks();
  }

  private static void sortTasksByTime(TodoList todoList) {
    Collections.sort(todoList.getTasks(), Comparator.comparing(Task::getTime));
  }
}