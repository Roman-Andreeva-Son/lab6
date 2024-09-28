package server.commands;

import global.object.Response;
import server.managers.CollectionManager;
import global.object.MusicBand;
import global.object.MusicGenre;
import global.utility.StandardConsole;

/**
 * Команда 'max_by_id'. Вывести любой объект из коллекции, значение поля id которого является максимальным.
 * @author Roman
 */
public class MaxById extends Command {

    private final CollectionManager collectionManager;

    public MaxById(CollectionManager collectionManager) {
        super("max_by_id", "вывести любой объект из коллекции, значение поля id которого является максимальным");
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
        int max = 0;
        for (var e : collectionManager.getCollection()) {
            if (e.getId() > max) max = e.getId();
        }
        return new Response(collectionManager.byId(max).toString());
    }
}
