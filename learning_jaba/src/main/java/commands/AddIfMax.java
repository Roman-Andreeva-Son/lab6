package commands;

import managers.CollectionManager;
import object.*;
import utility.Ask;
import utility.StandardConsole;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции.
 * @author Roman
 */
public class AddIfMax extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public AddIfMax(StandardConsole console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public boolean apply(String[] args) {
        try {
            if (!args[1].isEmpty()) {
                console.println("Неправильное количество аргументов");
                console.println("Использование: '" + getName() + "'");
                return false;
            }

            console.println("* Создание новой банды:");
            MusicBand musicBand = Ask.askMusicBand(console, collectionManager.getFreeId());
            long maxValue = collectionManager.getMaxNOP();

            if (musicBand != null && musicBand.validate() && musicBand.getNumberOfParticipants() > maxValue) {
                collectionManager.add(musicBand);
                collectionManager.addLog("add_if_max " + musicBand.getId(), true);
                console.println("Банда добавлена");
                return true;
            } else if (musicBand == null || !musicBand.validate()) {
                console.printError("Поля банды не валидны. Банда не создана.");
                return false;
            } else if (musicBand != null && musicBand.validate() && musicBand.getNumberOfParticipants() < maxValue) {
                console.printError("Банда не добавлена, кол-во участников не максимальное (" + musicBand.getNumberOfParticipants() + " < " + maxValue + ")");
                return false;
            }
        } catch (Ask.AskBreak e) {
            console.println("Отмена");
            return false;
        }
        return true;
    }
}
