package commands;

import managers.CommandManager;
import utility.StandardConsole;

/**
 * Команда 'help'. Выводит справку по доступным командам
 * @author Roman
 */
public class Help extends Command {
    private final StandardConsole console;
    private final CommandManager commandManager;

    public Help(StandardConsole console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды
     */
    public boolean apply(String[] args) {
        if (!args[1].isEmpty()) {
            console.println("Неправильное количество аргументов");
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        commandManager.getCommands().values().forEach(command -> {
            console.printTable(command.getName(), command.getDescription());
        });
        return true;
    }
}
