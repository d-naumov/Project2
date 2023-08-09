import java.util.Scanner;
import java.util.Scanner;

public enum MenuCommand {
  UNEXPECTED(""), // служебное значение, которое не должен видеть пользователь
  LIST("Список дел"),
  ADD("Добавить дело"),
  REMOVE("Завершить дело"),
  EXIT("Выйти");

  private final String message;

  MenuCommand(String message) {
    this.message = message;
  }
  public String getMessage() {
    return message;
  }

  // фабричный метод - статический метод, который порождает (производит) экземпляр этого класса
  public static MenuCommand readCommand(Scanner scanner) {
    printMenu();
    System.out.print("Введите команду: ");
    if (!scanner.hasNext()) {
      // Мы должны выбрасывать исключение, когда что-то пошло настолько не так,
      // что мы не можем это исправить.
      throw new RuntimeException("Ожидается ввод команды");
    }
    String input = scanner.next();
    scanner.nextLine();
    switch (input.toLowerCase()) {
      case "1":
        return LIST;
      case "2":
        return ADD;
      case "3":
        return REMOVE;
      case "4":
        return EXIT;
      default:
        return UNEXPECTED;
    }
  }
  public static void printMenu() {
    for (MenuCommand command : values()) {
      if (!command.message.isEmpty()) { // message пустое для всех служебных значений
        System.out.println(command.ordinal() + ". " + command.message);
      }
    }
  }
}

