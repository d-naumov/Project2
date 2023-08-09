import java.util.List;

public class Main {

  public static void main(String[] args) {
    TodoList todoList = new TodoList();

    Task task1 = new TodoTask("Buy groceries");
    Task task2 = new TodoTask("Write report");
    Task task3 = new TodoTask("Go to the gym");

    todoList.addTask(task1);
    todoList.addTask(task2);
    todoList.addTask(task3);

    // Пометить первое дело как выполненное
    task1.markAsDone();

    // Вывести список дел
    List<Task> tasks = todoList.getTasks();
    for (Task task : tasks) {
      String status = task.isDone() ? "[Done]" : "[Not Done]";
      System.out.println(status + " " + task.getDescription());
    }
  }
}
