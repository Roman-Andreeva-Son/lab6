package server.commands;


import global.object.MusicBand;
import global.object.Response;
import global.utility.StandardConsole;

/**
 * Абстрактная команда с именем и описанием
 * * @author Roman
 */
public abstract class Command {
    private final String name;
    private final String description;
    public StandardConsole console = new StandardConsole();

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Название и использование команды.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Описание команды.
     */
    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
    public abstract Response apply(String[] args, MusicBand musicBand);
}
