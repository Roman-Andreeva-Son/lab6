package commands;

import managers.CollectionManager;
import object.MusicBand;
import utility.*;



/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 * @author Roman
 */
public class Add extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public Add(StandardConsole console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды и сообщение об успешности.
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

            if (musicBand != null && musicBand.validate()) {
                collectionManager.add(musicBand);
                collectionManager.addLog("add " + musicBand.getId(), true);
                console.println("Банда добавлена");
                return true;
            } else {
                console.printError("Поля банды не валидны. Банда не создана.");
                return false;
            }
        } catch (Ask.AskBreak e) {
            console.println("Отмена");
            return false;
        }
    }
}
