import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TodoList implements Comparable<TodoList> {


  private static final List<Task> taskList = readFromTxt("res/todoList.txt");
  private List<Task> tasks;

  public TodoList(List<Task> tasks) {
    this.tasks = tasks;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public TodoList() {
    tasks = new ArrayList<>();
  }

  public void addTask(Task task) {
    tasks.add(task);
  }

  private static List<Task> readFromTxt(String filename) {
    List<Task> tasks = new ArrayList<>();
    File todoListFile = new File(filename);
    try {
      Scanner scanner = new Scanner(todoListFile);
      while (scanner.hasNextLine()) {
        try {
          if (!tasks.contains(tasks)) {
            tasks.add(tasks.get(1));
          }
          //task1.markAsDone();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          System.out.println("Некорректная строка файла: " + "line");
        }
      }
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("Не найден файл: " + e);
    }
    return tasks;
  }

  @Override
  public int compareTo(TodoList o) {
    return 0;
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
}
// private List<Task> tasks;
//
//  public TodoList() {
//    tasks = new ArrayList<>();
//  }
//
//  public void addTask(Task task) {
//    tasks.add(task);
//  }
//
//  public List<Task> getTasks() {
//    return tasks;
//  }