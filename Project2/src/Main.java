
import java.time.format.DateTimeFormatter;
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
        case REMOVE:
          removeTask(scanner, todoList);
          break;
        case EXIT:
          saveAndPrintCompletedTasks(todoList);
          return;
        default:
          System.out.println("Некорректная команда");
      }
    }
  }

  static void listTasks(TodoList todoList) {
    System.out.println("Список дел:");
    List<Task> tasks = todoList.getTasks();
    for (int i = 0; i < tasks.size(); i++) {
      Task task = tasks.get(i);
      String status = task.isDone() ? "[Выполнено]" : "[Не выполнено]";
      System.out.printf("%d. %s %s (Время: %s)%n",
          i + 1,
          status,
          task.getDescription(),
          task.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
      );
    }
  }

  static void addTask(Scanner scanner, TodoList todoList) {
    System.out.print("Введите описание задачи: ");
    String description = scanner.nextLine();

    if (containsDigits(description)) {
      System.out.println(
          "Описание задачи не должно содержать числа. Пожалуйста, введите корректное описание.");
      return;
    }

    if (taskDescriptionExists(todoList, description)) {
      System.out.println(
          "Задача с таким описанием уже существует. Пожалуйста, введите уникальное описание.");
      return;
    }

    Task newTask = createNewTask(description);
    todoList.addTask(newTask);
    System.out.println("Задача добавлена.");
  }

  static void removeTask(Scanner scanner, TodoList todoList) {
    System.out.print("Введите номер задачи, которую хотите удалить: ");
    try {
      int taskNumber = scanner.nextInt();
      scanner.nextLine(); // Чтение символа новой строки
      if (taskNumber > 0 && taskNumber <= todoList.getTasks().size()) {
        Task taskToRemove = todoList.getTasks().get(taskNumber - 1);
        performTaskRemoval(todoList, taskToRemove);
      } else {
        System.out.println("Некорректный номер задачи.");
      }
    } catch (InputMismatchException e) {
      System.out.println("Некорректный номер задачи.");
      scanner.nextLine();
    }
  }

  static void performTaskRemoval(TodoList todoList, Task taskToRemove) {
    todoList.removeTask(taskToRemove);
    System.out.println("Задача удалена.");
  }

  static boolean containsDigits(String description) {
    for (char c : description.toCharArray()) {
      if (Character.isDigit(c)) {
        return true;
      }
    }
    return false;
  }

  static boolean taskDescriptionExists(TodoList todoList, String description) {
    for (Task task : todoList.getTasks()) {
      if (task.getDescription().equals(description)) {
        return true;
      }
    }
    return false;
  }

  static Task createNewTask(String description) {
    return new TodoTask(description);
  }

  static void markTaskAsDone(Scanner scanner, TodoList todoList) {
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

  static void saveAndPrintCompletedTasks(TodoList todoList) {
    String filename = "res/todoList.txt";
    todoList.saveToFile(filename);
    todoList.printCompletedTasks();
  }

  static void sortTasksByTime(TodoList todoList) {
    todoList.getTasks().sort(Comparator.comparing(Task::getTime));
  }
}
