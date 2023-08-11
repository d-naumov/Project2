
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  @Test
  void testReadCommand_ValidInput1() {
    testInput = new ByteArrayInputStream("1\n".getBytes());
    System.setIn(testInput);
    MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
    assertEquals(MenuCommand.LIST, result);
  }

  @Test
  void testReadCommand_ValidInput2() {
    testInput = new ByteArrayInputStream("2\n".getBytes());
    System.setIn(testInput);
    MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
    assertEquals(MenuCommand.ADD, result);
  }

  @Test
  void testReadCommand_ValidInput3() {
    testInput = new ByteArrayInputStream("3\n".getBytes());
    System.setIn(testInput);
    MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
    assertEquals(MenuCommand.MARK_DONE, result);
  }

  @Test
  void testReadCommand_ValidInput4() {
    testInput = new ByteArrayInputStream("4\n".getBytes());
    System.setIn(testInput);
    MenuCommand result = MenuCommand.readCommand(new Scanner(System.in));
    assertEquals(MenuCommand.EXIT, result);
  }
 /* @Test
  void testReadCommand_InvalidInput() {
    testInput = new ByteArrayInputStream("invalid\n".getBytes());
    System.setIn(testInput);
    Assertions.assertThrows(RuntimeException.class, () -> {
      MenuCommand.readCommand(new Scanner(System.in));
    });
  }
  @Test
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
