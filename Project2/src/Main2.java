import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


public class Main2 {


  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    TodoList todoList = TodoList.readFromFile("res/todoList.txt");

    while (true) {
      MenuCommand command = MenuCommand.readCommand(scanner);
      switch (command) {
        case LIST:
          System.out.println("Список дел:");
          List<Task> tasks = todoList.getTasks();
          for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isDone() ? "[Выполнено]" : "[Не выполнено]";
            System.out.println((i + 1) + ". " + status + " " + task.getDescription() +
                " (Время: " + task.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
                + ")");
          }
          break;
        case ADD:
          System.out.print("Введите описание задачи: ");
          String description = scanner.nextLine();
          System.out.print("Введите время задачи (dd.MM.yyyy HH:mm): ");
          String timeInput = scanner.nextLine();
          LocalDateTime taskTime = LocalDateTime.parse(timeInput,
              DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
          Task newTask = new TodoTask(description, taskTime);
          todoList.addTask(newTask);
          break;
        case MARK_DONE:
          System.out.print("Введите номер задачи, которую хотите пометить как выполненную: ");
          int taskNumber = scanner.nextInt();
          if (taskNumber > 0 && taskNumber <= todoList.getTasks().size()) {
            Task taskToMarkDone = todoList.getTasks().get(taskNumber - 1);
            taskToMarkDone.markAsDone();
          } else {
            System.out.println("Некорректный номер задачи.");
          }
          break;
        case EXIT:
          String filename = "res/todoList.txt";
          todoList.saveToFile(filename);
          todoList.printCompletedTasks();
          return;
        default:
          System.out.println("Некорректная команда");
      }
    }
  }
}

