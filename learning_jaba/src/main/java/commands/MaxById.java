package commands;

import managers.CollectionManager;
import object.MusicBand;
import object.MusicGenre;
import utility.StandardConsole;

/**
 * Команда 'max_by_id'. Вывести любой объект из коллекции, значение поля id которого является максимальным.
 * @author Roman
 */
public class MaxById extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public MaxById(StandardConsole console, CollectionManager collectionManager) {
        super("max_by_id", "вывести любой объект из коллекции, значение поля id которого является максимальным");
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
        int max = 0;
        for (var e : collectionManager.getCollection()) {
            if (e.getId() > max) max = e.getId();
        }
        console.println(collectionManager.byId(max).toString());
        return true;
    }
}
