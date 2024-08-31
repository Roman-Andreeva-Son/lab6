import commands.*;

import managers.CollectionManager;
import managers.CommandManager;
import managers.CSVParser;

import object.*;

import utility.StandardConsole;
import utility.Runner;

public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();

        if (args.length == 0) {
            console.println(
                    "Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var csvParser = new CSVParser(args[0], console);
        var collectionManager = new CollectionManager(csvParser);
        if (!collectionManager.loadCollection()) {
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update_id", new UpdateId(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("max_by_id", new MaxById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("insert_at", new InsertAt(console, collectionManager));
            register("add_if_max", new AddIfMax(console, collectionManager));
            register("add_if_min", new AddIfMin(console, collectionManager));
            register("sum_of_number_of_participants", new SumOfNOP(console, collectionManager));
            register("print_unique_front_man", new PrintUniqueFrontMan(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();
    }
} 
