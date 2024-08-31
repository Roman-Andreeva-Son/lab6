package commands;

import managers.CollectionManager;
import utility.Ask;

import utility.StandardConsole;

/**
 * Команда 'update_id'. Обновляет элемент коллекции.
 * @author Roman
 */
public class UpdateId extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public UpdateId(StandardConsole console, CollectionManager collectionManager) {
        super("update_id {element}", "обновить значение элемента по id");
        this.console = console;
        this.collectionManager = collectionManager;
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public boolean apply(String[] args) {
        try {
            if (args[1].isEmpty()) {
                console.println("Неправильное количество аргументов");
                console.println("Использование: '" + getName() + "'");
                return false;
            }
            int id;
            try { id = Integer.parseInt(args[1].trim()); } catch (NumberFormatException e) { console.println("id не распознан"); return false; }

            if (collectionManager.byId(id) == null || !collectionManager.getCollection().contains(collectionManager.byId(id))) {
                console.println("не существующий id");
                return false;
            }

            console.println("* Создание новой Банды: ");
            var musicBand = Ask.askMusicBand(console, collectionManager.getFreeId());
            if (musicBand != null && musicBand.validate()) {
                collectionManager.add(musicBand);
                collectionManager.addLog("add " + musicBand.getId(), true);
                collectionManager.update();

                var old = collectionManager.byId(id);
                collectionManager.swap(musicBand.getId(), id);
                collectionManager.addLog("swap " + old.getId() + " " + id, false);
                collectionManager.update();

                collectionManager.remove(old.getId());
                collectionManager.addLog("remove " + old.getId(), false);
                collectionManager.update();
                return true;
            } else {
                console.println("Поля Банды не валидны. Банда не создан.");
                return false;
            }
        } catch (Ask.AskBreak e) {
            console.println("Отмена");
            return false;
        }
    }
}
