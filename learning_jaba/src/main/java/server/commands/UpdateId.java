package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.managers.CollectionManager;
import global.utility.StandardConsole;

/**
 * Команда 'update_id'. Обновляет элемент коллекции.
 * @author Roman
 */
public class UpdateId extends Command {
    private final CollectionManager collectionManager;

    public UpdateId(CollectionManager collectionManager) {
        super("update_id {element}", "обновить значение элемента по id");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public Response apply(String[] args, MusicBand mb) {
        try {
            if (args[1].isEmpty()) {
                return new Response("Неправильное количество аргументов \n" + "Использование: '" + getName() + "'");
            }
            int id;
            try { id = Integer.parseInt(args[1].trim()); } catch (NumberFormatException e) {
                return new Response("id не распознан");
            }

            if (collectionManager.byId(id) == null || !collectionManager.getCollection().contains(collectionManager.byId(id))) {
                return new Response("не существующий id");
            }


            var musicBand = mb;
            if (musicBand != null && musicBand.validate()) {
                collectionManager.add(musicBand);

                collectionManager.update();

                var old = collectionManager.byId(id);

                collectionManager.update();

                collectionManager.remove(old.getId());
                collectionManager.update();
                return new Response("Обновлено!");
            } else {
                return new Response("Поля Банды не валидны. Банда не создана.");
            }
        } catch (Exception e) {
            return new Response("Продукта с таким айди в коллекции нет");
        }
    }
}
