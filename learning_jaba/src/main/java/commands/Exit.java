package commands;

import utility.StandardConsole;

/**
 * Команда 'exit'. Завершает выполнение.
 * @author Roman
 */
public class Exit extends Command {
    private final StandardConsole console;

    public Exit(StandardConsole console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
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

        console.println("Завершение выполнения");
        return true;
    }
}
