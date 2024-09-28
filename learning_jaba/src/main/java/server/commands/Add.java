package server.commands;

import client.utility.Ask;
import global.object.Response;
import global.utility.StandardConsole;
import server.managers.CollectionManager;
import global.object.MusicBand;


/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 * @author Roman
 */
public class Add extends Command {
    private final CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");

        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
     */

    public Response apply(String[] args, MusicBand mb) {
        try {
            if (!args[1].isEmpty()) {
                return new Response("Неправильное количество аргументов \n" + "Использование: '" + getName() + "'");
            }

            MusicBand musicBand = mb;
            if (musicBand != null && musicBand.validate()) {
                collectionManager.add(musicBand);
                return new Response("Банда добавлена");
            } else {
                return new Response("Поля банды не валидны. Банда не создана.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
