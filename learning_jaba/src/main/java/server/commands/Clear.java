package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.Main;
import server.managers.CollectionManager;
import global.utility.StandardConsole;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author Roman
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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

        var isFirst = true;
        while (!collectionManager.getCollection().isEmpty()) {
            var route = collectionManager.getCollection().remove(collectionManager.getCollection().size()-1);
            collectionManager.remove(route.getId());
            isFirst = false;
        }

        collectionManager.update();

        return new Response("Коллекция очищена");
    }
}
