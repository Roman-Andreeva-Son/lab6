package server.managers;

import server.commands.Command;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Класс для работы с командами.
 * @author Roman
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
//    private final List<String> commandHistory = new ArrayList<>();

    /**
     * Добавляет команду
     * @param commandName Название команды.
     * @param command Команда.
     */
    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    /**
     * @return Словарь команд.
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

}
