package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.managers.CollectionManager;
import global.utility.StandardConsole;
/**
 * Команда 'show'. Выводит коллекцию (из себя).
 * @author Roman
 */
public class Show extends Command {

    private final CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все элементы коллекции в строковом представлении");

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
        return new Response(collectionManager.toString());

    }
}
