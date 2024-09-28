package global.utility;

import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Класс для ввода команд и вывода результата
 * @see Console
 */
public class StandardConsole {
    private static final String P = "$ ";
    private static Scanner fileScanner = null;
    private static Scanner systemScanner = new Scanner(System.in);
    /**
     * @return obj.toString() в консоль
     *
     * @param obj Объект для печати
     */
    public void print(Object obj){
        System.out.print(obj);
    }
    /**
     * @return obj.toString() + \n в консоль
     *
     * @param obj Объект для печати
     */
    public void println(Object obj){
        System.out.println(obj);
    }
    /**
     * @return ошибка: obj.toString() в консоль
     *
     * @param obj Ошибка для печати
     */
    public void printError(Object obj){
        System.err.println("Error:" + obj);
        try {
            Thread.sleep(20);
        } catch (InterruptedException ignored) {
        }
    }
    /**
     * Считывает информацию с консоли
     */
    public String readln() throws NoSuchElementException, IllegalStateException{
        return(fileScanner!=null? fileScanner : systemScanner).nextLine();
    }
    /**
     * Проверят возможность считать
     */
    public boolean isCanReadln() throws IllegalStateException{
        return (fileScanner!=null? fileScanner:systemScanner).hasNextLine();
    }
    /**
     * Выводит 2 колонки
     *
     * @param obj1  Левый элемент колонки.
     * @param obj2 Правый элемент колонки.
     */
    public void printTable(Object obj1, Object obj2){
        System.out.printf("%-35s%-1s%n", obj1, obj2);
    }

    public void prompt(){
        print(P);
    }
    public String getPrompt(){
        return P;
    }
    /**
     * Выбор считывать с файла
     */
    public void selectFileScanner(Scanner scanner){
        this.fileScanner = scanner;
    }
    /**
     * Выбор считывать с консоли
     */
    public void selectConsoleScanner(){
        this.fileScanner = null;
    }
}
