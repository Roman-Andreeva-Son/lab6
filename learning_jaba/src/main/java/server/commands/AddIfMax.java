package server.commands;

import server.managers.CollectionManager;
import global.object.*;


/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 * @author Roman
 */
public class AddIfMax extends Command {

    private final CollectionManager collectionManager;

    public AddIfMax(CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");

        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public Response apply(String[] args, MusicBand mb) {
        try {
            if (!args[1].isEmpty()) {
                return new Response("Неправильное количество аргументов \n" + "Использование: '" + getName() + "'");
            }

            MusicBand musicBand = mb;
            long maxValue = collectionManager.getMaxNOP();

            if (musicBand != null && musicBand.validate() && musicBand.getNumberOfParticipants() > maxValue) {
                collectionManager.add(musicBand);
                return new Response("Банда добавлена");
            } else if (musicBand == null || !musicBand.validate()) {
                return new Response("Поля банды не валидны, банда не создана");
            } else if (musicBand != null && musicBand.validate() && musicBand.getNumberOfParticipants() < maxValue) {
                return new Response("Банда не добавлена, кол-во участников не максимальное (" + musicBand.getNumberOfParticipants() + " < " + maxValue + ")");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response("да");
    }
}
