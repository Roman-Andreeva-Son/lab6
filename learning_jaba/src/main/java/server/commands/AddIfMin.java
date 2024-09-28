package server.commands;

import server.managers.CollectionManager;
import global.object.*;


/**
 * Команда 'add_if_min'. Добавляет новый элемент в коллекцию, если его значение меньше значения наименьшего элемента этой коллекции.
 * @author Roman
 */
public class AddIfMin extends Command {
    private final CollectionManager collectionManager;

    public AddIfMin(CollectionManager collectionManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше значения наименьшего элемента этой коллекции");
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
            long minValue = collectionManager.getMinNOP();

            if (musicBand != null && musicBand.validate() && musicBand.getNumberOfParticipants() < minValue) {
                collectionManager.add(musicBand);
                return new Response("Банда добавлена");
            } else if (musicBand == null || !musicBand.validate()) {
                return new Response("Поля банды не валидны. Банда не создана.");
            } else if (musicBand != null && musicBand.validate() && musicBand.getNumberOfParticipants() > minValue) {
                return new Response("Банда не добавлен, кол-во участников не минимальное (" + musicBand.getNumberOfParticipants() + " > " + minValue + ")");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Response("yep");
    }
}
