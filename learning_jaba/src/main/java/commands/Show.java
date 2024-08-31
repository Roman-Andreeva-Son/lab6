package commands;

import managers.CollectionManager;
import utility.StandardConsole;
/**
 * Команда 'show'. Выводит коллекцию (из себя).
 * @author Roman
 */
public class Show extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public Show(StandardConsole console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public boolean apply(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Неправильное количество аргументов.");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        console.println(collectionManager);
        return true;
    }
}
