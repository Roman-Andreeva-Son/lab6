package commands;

import managers.CollectionManager;
import utility.Ask;
import utility.StandardConsole;
import java.util.TreeSet;
import object.MusicBand;


/**
 * Команда 'print_unique_front_man'. Вывести уникальные значения поля age всех элементов в коллекции.
 * @author Roman
 */
public class PrintUniqueFrontMan extends Command {
    private final StandardConsole console;
    private final CollectionManager collectionManager;

    public PrintUniqueFrontMan(StandardConsole console, CollectionManager collectionManager) {
        super("print_unique_front_man", "вывести уникальные значения поля front man всех элементов в коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public boolean apply(String[] args) {
            if (!args[1].isEmpty()) {
                console.println("Неправильное количество аргументов");
                console.println("Использование: '" + getName() + "'");
                return false;
            }
            var beNull = false;
            var ts = new TreeSet<String>();
            for (var e : collectionManager.getCollection()) {
                if (e.getFrontMan().getPassportID() == null)
                    beNull = true;
                else
                    ts.add(e.getFrontMan().getPassportID());
            }
            StringBuilder s = new StringBuilder();
            if (beNull)
                s = new StringBuilder(" null");
            for (var e : ts)
                s.append(e);
            console.println(s);
            return true;
    }
}