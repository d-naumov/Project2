import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TodoList implements Comparable<TodoList> {

  private final List<Task> tasks;

  public TodoList(List<Task> tasks) {
    this.tasks = tasks;
  }

  public TodoList() {
    tasks = new ArrayList<>();
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  public List<Task> getCompletedTasks() {
    List<Task> completedTasks = new ArrayList<>();
    for (Task task : tasks) {
      if (task.isDone()) {
        completedTasks.add(task);
      }
    }
    return completedTasks;
  }

  public void printCompletedTasks() {
    List<Task> completedTasks = getCompletedTasks();
    System.out.println("Проделанные дела:");
    for (int i = 0; i < completedTasks.size(); i++) {
      System.out.println((i + 1) + ". [Выполнено] " + completedTasks.get(i).getDescription() +
          " (Время: " + completedTasks.get(i).getTime()
          .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) + ")");
    }
  }

  public void saveToFile(String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      for (Task task : tasks) {
        writer.println(
            task.getDescription() + "," + task.getCompletionStatus() + "," +
                task.getTime()
                    .format(DateTimeFormatter.ofPattern("HH:mm")));
      }
    } catch (IOException e) {
      System.out.println("Ошибка при сохранении в файл: " + e.getMessage());
    }
  }

  public static TodoList readFromFile(String filename) {
    List<Task> tasks = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 3) {
          Task task = createTaskFromParts(parts);
          tasks.add(task);
        } else {
          System.out.println("Ошибка: неверный формат строки - " + line);
        }
      }
    } catch (IOException e) {
      System.out.println("Ошибка при чтении файла: " + e.getMessage());
    }

    return new TodoList(tasks);
  }

  private static Task createTaskFromParts(String[] parts) {
    String description = parts[0];
    int completionStatus = Integer.parseInt(parts[1]);
    LocalDateTime time = LocalDateTime.parse(parts[2]);

    Task task = new TodoTask(description, time);

    if (completionStatus == 1) {
      task.markAsDone();
    }

    return task;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TodoList todoList = (TodoList) o;

    return tasks.equals(todoList.tasks);
  }

  @Override
  public int hashCode() {
    return tasks.hashCode();
  }

  @Override
  public String toString() {
    return "TodoList{" +
        "tasks=" + tasks +
        '}';
  }

  @Override
  public int compareTo(TodoList other) {
    List<Task> thisTasks = this.getTasks();
    List<Task> otherTasks = other.getTasks();

    return Comparator.comparing(Task::isDone, Comparator.reverseOrder())
        .thenComparing(Task::getTime)
        .compare(thisTasks.get(0), otherTasks.get(0));
  }
}

