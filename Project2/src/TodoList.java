import java.util.ArrayList;
import java.util.List;

public class TodoList {

  private List<Task> tasks;

  public TodoList() {
    tasks = new ArrayList<>();
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public List<Task> getTasks() {
    return tasks;
  }
}
