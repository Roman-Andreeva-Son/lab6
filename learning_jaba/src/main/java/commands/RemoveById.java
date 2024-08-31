package commands;

import managers.CollectionManager;
import utility.StandardConsole;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 * @author Roman
 */
public class RemoveById extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public RemoveById(StandardConsole console, CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по id");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public boolean apply(String[] args) {
        if (args[1].isEmpty()) {
            console.println("Неправильное количество аргументов");
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        int id = -1;
        try { id = Integer.parseInt(args[1].trim()); } catch (NumberFormatException e) { console.println("id не распознан"); return false; }

        if (collectionManager.byId(id) == null || !collectionManager.getCollection().contains(collectionManager.byId(id))) {
            console.println("не существующий id");
            return false;
        }
        collectionManager.remove(id);
        collectionManager.addLog("remove " + id, true);
        collectionManager.update();
        console.println("Банда успешно удалёна");
        return true;
    }
}
