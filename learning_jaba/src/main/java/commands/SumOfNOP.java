package commands;

import managers.CollectionManager;
import utility.StandardConsole;

/**
 * Команда 'sum_of_number_of_participants'. выводит сумму значений поля numberOfParticipants для всех элементов коллекции.
 * @author Roman
 */
public class SumOfNOP extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public SumOfNOP(StandardConsole console, CollectionManager collectionManager) {
        super("sum_of_number_of_participants", "вывести сумму значений поля numberOfParticipants для всех элементов коллекции");
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

        var collection = collectionManager.getCollection();
        long sumNumberOfParticipants = 0;
        for (var musicBand : collection) {
            sumNumberOfParticipants += musicBand.getNumberOfParticipants();
        }
        console.println(sumNumberOfParticipants);
        return true;
    }
}
