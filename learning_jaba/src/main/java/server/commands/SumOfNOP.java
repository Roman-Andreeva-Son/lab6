package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.managers.CollectionManager;
import global.utility.StandardConsole;

/**
 * Команда 'sum_of_number_of_participants'. выводит сумму значений поля numberOfParticipants для всех элементов коллекции.
 * @author Roman
 */
public class SumOfNOP extends Command {
    private final CollectionManager collectionManager;

    public SumOfNOP(CollectionManager collectionManager) {
        super("sum_of_number_of_participants", "вывести сумму значений поля numberOfParticipants для всех элементов коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public Response apply(String[] args, MusicBand mb) {
        if (!args[1].isEmpty()) {
            return new Response("Неправильное количество аргументов \n" + "Использование: '" + getName() + "'");
        }

        var collection = collectionManager.getCollection();
        Long sumNumberOfParticipants = 0L;
        for (var musicBand : collection) {
            sumNumberOfParticipants += musicBand.getNumberOfParticipants();
        }
        return new Response(sumNumberOfParticipants.toString());
    }
}
