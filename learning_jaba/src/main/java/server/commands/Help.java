package server.commands;

import global.object.MusicBand;
import global.object.Response;
import server.managers.CommandManager;
import global.utility.StandardConsole;

/**
 * Команда 'help'. Выводит справку по доступным командам
 * @author Roman
 */
public class Help extends Command {

    private final CommandManager commandManager;

    public Help(CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды
     */
    public Response apply(String[] args, MusicBand mb) {
        if (!args[1].isEmpty()) {
            return new Response("Неправильное количество аргументов \n" + "Использование: '" + getName() + "'");
        }

        StringBuilder result = new StringBuilder();
        commandManager.getCommands().values().forEach(command -> {
           result.append(command.getName()+" : "+ command.getDescription() + "\n\n");
        });
        return new Response(result.toString());
    }
}
