package global.utility;

import java.util.Scanner;
/**
 * Консоль для ввода команд и вывода результата
 * @author Roman
 */

public interface Console {
    void print(Object obj);
    void println(Object obj);
    void printError(Object obj);
    String readln();
    boolean isCanReadln();
    void printTable(Object obj1, Object obj2);
    void prompt();
    String getPrompt();
    void selectFileScanner(Scanner obj);
    void selectConsoleScanner();
}
