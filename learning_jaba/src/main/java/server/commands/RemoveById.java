package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.managers.CollectionManager;
import global.utility.StandardConsole;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 * @author Roman
 */
public class RemoveById extends Command {
    private final CollectionManager collectionManager;

    public RemoveById(CollectionManager collectionManager) {
        super("remove_by_id", "удалить элемент из коллекции по id");
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public Response apply(String[] args, MusicBand mb) {
        if (args[1].isEmpty()) {
            return new Response("Неправильное количество аргументов \n" + "Использование: '" + getName() + "'");

        }
        int id = -1;
        try { id = Integer.parseInt(args[1].trim()); } catch (NumberFormatException e) {
            return new Response("id не распознан");
        }

        if (collectionManager.byId(id) == null || !collectionManager.getCollection().contains(collectionManager.byId(id))) {
            return new Response("не существующий id");
        }
        collectionManager.remove(id);
        collectionManager.update();
        return new Response("Банда успешно удалёна");
    }
}
