import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    TodoList todoList = new TodoList();
    Task task1 = new TodoTask("Купить продукты");
    Task task2 = new TodoTask("Написать отчет");
    Task task3 = new TodoTask("Иди в спортзал");

   /* todoList.addTask(task1);
    todoList.addTask(task2);
    todoList.addTask(task3);

    // Пометить первое дело как выполненное
    task1.markAsDone();

    */


    while (true) {
      MenuCommand command = MenuCommand.readCommand(scanner);
      switch (command) {
        case UNEXPECTED:
          System.out.println("Некорректная команда");
          break;
        case LIST:
          //Pizza pizza = Pizza.readInteractive(scanner);
          //pizzas.add(pizza);
          // TODO запись заказа (пиццы) в файл с заказами
          break;
        case ADD:
          todoList.addTask(task1);
          todoList.addTask(task2);
          todoList.addTask(task3);
          break;
        case REMOVE:
          task1.markAsDone();
          System.out.println();
          break;
        case EXIT:
          System.out.println("Заказанные за сегодня пиццы:");
          // Collections.sort(pizzas);
          List<Task> tasks = todoList.getTasks();
          for (Task task : tasks) {
            String status = task.isDone() ? "[Выполнено]" : "[Не выполнено]";
            System.out.println(status + " " + task.getDescription());
            return; // завершение работы метода main()
          }
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + command);
      }
    }
  }
}
