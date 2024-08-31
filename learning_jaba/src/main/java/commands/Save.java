package commands;

import managers.CollectionManager;
import utility.StandardConsole;
/**
 * Команда 'save'. Сохраняет все элементы в файл.
 * @author Roman
 */
public class Save extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public Save(StandardConsole console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public boolean apply(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Неправильное количество аргументов");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        collectionManager.saveCollection();
        return true;
    }
}
