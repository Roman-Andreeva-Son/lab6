package commands;

import managers.CollectionManager;
import object.MusicBand;
import utility.Ask;
import utility.StandardConsole;

/**
 * Команда 'insert_at'. Добавить новый элемент в заданную позицию.
 * @author Roman
 */

public class InsertAt  extends Command{
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public InsertAt(StandardConsole console, CollectionManager collectionManager) {
        super("insert_at_index {element}", "добавить новый элемент в заданную позицию");
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
            int index;
            try {
                index = Integer.parseInt(args[1].trim());
                if (index < 0 || index > collectionManager.getCollection().size()) {
                    console.println("Некорректный индекс. Индекс должен быть в пределах от 0 до " +
                            (collectionManager.getCollection().size()-1));
                    return false;
                }
                MusicBand musicBand = Ask.askMusicBand(console, collectionManager.getFreeId());
                if (musicBand != null && musicBand.validate()) {
                    collectionManager.getCollection().add(index, musicBand);
                    console.println("Банда успешно добавлена в коллекцию на позицию " + index);
                    return true;
                } else {
                    console.println("Поля банды не валидны, банда не создана");
                    return false;
                }
            } catch (NumberFormatException e){console.println("index не распознан");
                return false;
            }

            } catch (Ask.AskBreak e){
            console.println("Отмена");
            return false;
        }
    }
}
