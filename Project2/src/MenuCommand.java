import java.util.Scanner;

public enum MenuCommand {
  UNEXPECTED(""), // служебное значение, которое не должен видеть пользователь
  LIST("Список дел"),
  ADD("Добавить дело"),
  MARK_DONE("Пометить как выполненное"),

  EXIT("Выйти");

  private final String message;

  MenuCommand(String message) {
    this.message = message;
  }
  public String getMessage() {
    return message;
  }

  public static MenuCommand readCommand(Scanner scanner) {
    printMenu();
    System.out.print("Введите команду: ");
    if (!scanner.hasNext()) {
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
        return MARK_DONE;
      case "4":
        return EXIT;
      default:
        return UNEXPECTED;
    }
  }
  public static void printMenu() {
    for (MenuCommand command : values()) {
      if (!command.message.isEmpty()) {
        System.out.println(command.ordinal() + ". " + command.message);
      }
    }
  }
}

