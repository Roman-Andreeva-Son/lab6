package server.commands;

import global.object.Response;
import server.managers.CollectionManager;
import global.object.MusicBand;
import client.utility.Ask;
import global.utility.StandardConsole;

/**
 * Команда 'insert_at'. Добавить новый элемент в заданную позицию.
 * @author Roman
 */

public class InsertAt  extends Command{

    private final CollectionManager collectionManager;

    public InsertAt(CollectionManager collectionManager) {
        super("insert_at {element}", "добавить новый элемент в заданную позицию");
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
            int index;
            try {
                index = Integer.parseInt(args[1].trim());
                if (index < 0 || index > collectionManager.getCollection().size()) {
                    return new Response("Некорректный индекс. Индекс должен быть в пределах от 0 до "+(collectionManager.getCollection().size() - 1));
                }
                MusicBand musicBand = mb;
                if (musicBand != null && musicBand.validate()) {
                    collectionManager.getCollection().add(index, musicBand);
                    return new Response("Банда успешно добавлена в коллекцию на позицию "+ index);
                } else {
                    return new Response("Поля банды не валидны, банда не создана");
                }
            } catch (NumberFormatException e){
                return new Response("index не распознан");
            }

            } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
