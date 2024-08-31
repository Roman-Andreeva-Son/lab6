package commands;

import managers.CollectionManager;
import utility.StandardConsole;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author Roman
 */
public class Clear extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public Clear(StandardConsole console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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

        var isFirst = true;
        while (!collectionManager.getCollection().isEmpty()) {
            var route = collectionManager.getCollection().remove(collectionManager.getCollection().size()-1);
            collectionManager.remove(route.getId());
            collectionManager.addLog("remove " + route.getId(), isFirst);
            isFirst = false;
        }

        collectionManager.update();
        console.println("Коллекция очищена");
        return true;
    }
}
