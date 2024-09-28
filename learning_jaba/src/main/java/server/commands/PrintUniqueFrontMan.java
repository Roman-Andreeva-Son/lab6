package server.commands;

import global.object.Response;
import server.managers.CollectionManager;
import global.utility.StandardConsole;
import java.util.TreeSet;
import global.object.MusicBand;


/**
 * Команда 'print_unique_front_man'. Вывести уникальные значения поля passport_id всех элементов в коллекции.
 * @author Roman
 */
public class PrintUniqueFrontMan extends Command {
    private final CollectionManager collectionManager;

    public PrintUniqueFrontMan(CollectionManager collectionManager) {
        super("print_unique_front_man", "вывести уникальные значения поля front man всех элементов в коллекции");
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
                s.append(e + " ");
            s.trimToSize();
            return new Response(s.toString());
    }
}