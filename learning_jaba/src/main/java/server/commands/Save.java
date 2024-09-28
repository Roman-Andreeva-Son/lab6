package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.managers.CollectionManager;
import global.utility.StandardConsole;
/**
 * Команда 'save'. Сохраняет все элементы в файл.
 * @author Roman
 */
public class Save extends Command {

    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public Response apply(String[] args, MusicBand mb) {
        if (!args[1].isEmpty()) {
            console.println("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
            return new Response("");
        }

        collectionManager.saveCollection();
        console.println("Выполнение сохранения прошло успешно");
        return new Response("");
    }
}
