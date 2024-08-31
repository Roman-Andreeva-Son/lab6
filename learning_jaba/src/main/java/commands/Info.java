package commands;

import managers.CollectionManager;
import utility.StandardConsole;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author Roman
 */
public class Info extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public Info(StandardConsole console, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
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
            console.println("использование: '" + getName() + "'");
            return false;
        }

        LocalDateTime lastInitTime = collectionManager.getLastInitTime();
        String lastInitTimeString = (lastInitTime == null) ? "в данной сессии инициализации ещё не происходило" :
                lastInitTime.toLocalDate().toString() + " " + lastInitTime.toLocalTime().toString();

        LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения ещё не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        console.println("Сведения о коллекции:");
        console.println(" Тип: " + collectionManager.getCollection().getClass());
        console.println(" Количество элементов: " + collectionManager.getCollection().size());
        console.println(" Дата последнего сохранения: " + lastSaveTimeString);
        console.println(" Дата последней инициализации: " + lastInitTimeString);
        return true;
    }
}
