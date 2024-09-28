package server.commands;

import global.object.MusicBand;
import global.object.Response;
import global.utility.StandardConsole;

/**
 * Команда 'exit'. Завершает выполнение.
 * @author Roman
 */
public class Exit extends Command {

    public Exit() {
        super("exit", "завершить программу (без сохранения в файл)");
    }
    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    public Response apply(String[] args, MusicBand mb) {
        if (!args[1].isEmpty()) {
            console.println("Неправильное количество аргументов");
            console.println("Использование: '" + getName() + "'");
            return new Response("");
        }

        console.println("Завершение выполнения");
        System.exit(0);
        return new Response("");
    }

}
