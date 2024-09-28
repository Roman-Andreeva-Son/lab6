package server;

import global.utility.Console;
import server.commands.*;
import global.utility.StandardConsole;

import server.UDP.DatagramServer;
import server.managers.CollectionManager;
import server.managers.CommandManager;
import server.managers.CSVParser;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length < 2) {
            System.err.println("Нужно использовать: java -jar server.jar <dataFileName> <port>");
            return;
        }

        String dataFileName = args[0]; // Имя файла из аргумента
        int port; // Порт из аргумента
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Порт должен быть в формате int");
            return;
        }

        StandardConsole console = new StandardConsole();
        CSVParser csvParser = new CSVParser(dataFileName, console);
        CollectionManager collectionManager = new CollectionManager(csvParser);
        CommandManager commandManager = new CommandManager();
        if (!collectionManager.loadCollection()) {
            System.err.println("Коллекция не загружается");
            return;
        }

        commandManager.register("add", new Add(collectionManager));
        commandManager.register("add_if_max", new AddIfMax(collectionManager));
        commandManager.register("add_if_min", new AddIfMin(collectionManager));
        commandManager.register("clear", new Clear(collectionManager));
        commandManager.register("save", new Save(collectionManager));
        commandManager.register("show", new Show(collectionManager));
        commandManager.register("help", new Help(commandManager));
        commandManager.register("insert_at", new InsertAt(collectionManager));
        commandManager.register("max_by_id", new MaxById(collectionManager));
        commandManager.register("exit", new Exit());
        commandManager.register("info", new Info(collectionManager));
        commandManager.register("print_unique_front_man", new PrintUniqueFrontMan(collectionManager));
        commandManager.register("remove_by_id", new RemoveById(collectionManager));
        commandManager.register("sum_of_number_of_participants", new SumOfNOP(collectionManager));
        commandManager.register("update_id", new UpdateId(collectionManager));


        new DatagramServer("localhost", port, commandManager).start(); // Использование заданного порта
    }
}
