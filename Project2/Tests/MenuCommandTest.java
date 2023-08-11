
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("MenuCommand is works:")
class MenuCommandTest {

  private InputStream originalSystemIn;
  private ByteArrayInputStream testInput;

  @BeforeEach
  void setUp() {
    originalSystemIn = System.in;
  }

  @AfterEach
  void tearDown() {
    System.setIn(originalSystemIn);
  }

  @Test
  void testGetMessage() {
    MenuCommand command = MenuCommand.LIST;
    assertEquals("Список дел", command.getMessage());
  }

  @DisplayName("readCommand() is works:")
  @Nested
  class testsForReadCommand {

    @Test
    void valid_Input_1() {
      testInput = new ByteArrayInputStream("1\n".getBytes());
      System.setIn(testInput);
      MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
      assertEquals(MenuCommand.LIST, result);
    }

    @Test
    void valid_Input_2() {
      testInput = new ByteArrayInputStream("2\n".getBytes());
      System.setIn(testInput);
      MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
      assertEquals(MenuCommand.ADD, result);
    }

    @Test
    void valid_Input_3() {
      testInput = new ByteArrayInputStream("3\n".getBytes());
      System.setIn(testInput);
      MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
      assertEquals(MenuCommand.MARK_DONE, result);
    }

    @Test
    void valid_Input_4() {
      testInput = new ByteArrayInputStream("4\n".getBytes());
      System.setIn(testInput);
      MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
      assertEquals(MenuCommand.EXIT, result);
    }
  }

  @Test
  void testReadCommand_EmptyInput() {
    testInput = new ByteArrayInputStream("".getBytes());
    System.setIn(testInput);

    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> MenuCommand.readCommand(new Scanner(System.in)));
    assertEquals("Ожидается ввод команды", exception.getMessage());
  }

/*  @Test
  void testPrintMenu() {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));

    MenuCommand.printMenu();
    String printedOutput = outputStream.toString();

    System.setOut(System.out);

    String expectedOutput = "1. Список дел\n2. Добавить дело\n3. Пометить как выполненное\n4. Выйти\n";
    assertEquals(expectedOutput, printedOutput);
  }

 */


}
