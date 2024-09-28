package client.utility;
import global.utility.StandardConsole;
import server.managers.CollectionManager;
import global.object.*;

import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Класс, запрашивающий данные с консоли для создания объекта.
 * @return MusicBand
 * @author Roman
 */
public class Ask {
    public static class AskBreak extends Exception{}
     static Set<String> uniqueFields = CollectionManager.getUniquePassportID();
    public static MusicBand askMusicBand(StandardConsole console) throws AskBreak{

        try{
            String name;
            while(true){
                console.println("Введите имя группы: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                if (!name.isEmpty()) break;
            }
            Coordinates coordinates = askCoordinates(console);

            long numberOfParticipants;
            while(true){
                console.print("Количество участников: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()){
                    try{
                        numberOfParticipants = Long.parseLong(line);
                        if (numberOfParticipants <= 0) {
                            console.printError("Количество участников должно быть больше 0");
                            continue;
                        }

                        break;
                    } catch (NumberFormatException e){
                        console.printError("Неверный формат ввода, плиз репид");
                    }
                }
            }

            Long albumsCount;
            while(true){
                console.print("Введите количество альбомов: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()){
                    try{
                        albumsCount = Long.parseLong(line);
                        break;
                    }
                    catch (NumberFormatException e){
                        console.printError("Неверный формат ввода. попробуйте заново");
                    }
                }
            }

            MusicGenre genre = askGenre(console);
            Person frontMan = askFrontMan(console);
            return new MusicBand(name, coordinates, numberOfParticipants, albumsCount, genre, frontMan);
        }catch(NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Coordinates askCoordinates(StandardConsole console) throws AskBreak{
        console.println("Введите координаты местоположения группы");
        try{
            Float x;
            while(true){
                console.print("Координата x: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()){
                    try {
                        x = Float.parseFloat(line);
                        if (x > -764) break;
                        else console.printError("x должно быть больше -764, попробуйте заново");
                    } catch (NumberFormatException e){
                        console.printError("Неверный формат ввода, попробуйте заново");
                    }
                }
            }
            int y;
            while (true){
                console.print("Координата y: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if(!line.isEmpty()){
                    try {
                        y = Integer.parseInt(line);
                        if (y > -766) break;
                        else console.printError("y должно быть больше -766, попробуйте заново");
                    } catch (NumberFormatException e){
                        console.printError("Неверный формат ввода, попробуйте заново");
                    }
                }
            }
            return new Coordinates(x, y);
        } catch (IllegalStateException | NoSuchElementException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static MusicGenre askGenre(StandardConsole console) throws AskBreak{
        console.print("Введите жанр: ");
        try{
            MusicGenre mg;
            while(true){
                console.print("(Типы жанра: " + MusicGenre.typesGenre() + ") Ваш выбор: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()){
                    try{
                        mg = MusicGenre.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException e){
                        console.printError("Неверный формат ввода, попробуй еще раз");
                    }
                }
                else return null;
            }
            return mg;
        } catch (NoSuchElementException | IllegalStateException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }
    public static Person askFrontMan(StandardConsole console) throws AskBreak{
        console.println("Введите данные ведущего певца: ");
        try{
            String name;
            do {
                console.print("Имя: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
                } while(name.isEmpty());
            String passportID;
            do {
                console.print("Паспортные данные: ");
                passportID = console.readln().trim();
                if (passportID.equals("exit")) throw new AskBreak();
                if (passportID.isEmpty()) {
                    console.printError("паспортные данные не могут быть пустыми, попробуйте снова");
                    continue;
                }
                if (!uniqueFields.contains(passportID)){
                    uniqueFields.add(passportID);
                    break;
                } else{
                    console.printError("Паспорт " + passportID + " уже существует, попробуйте снова:");
                }
            } while(true);
            Color eyeColor;
            while (true){
                console.print("Цвет глаз: (варианты цвета: " + Color.typesColor() + ") Ваш выбор: " );
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()){
                    eyeColor = null;
                    break;
                }
                if (!line.isEmpty()){
                    try {
                        eyeColor = Color.valueOf(line);
                        break;
                    }catch (NullPointerException | IllegalArgumentException e){
                        console.printError("Неверный формат ввода, попробуй еще раз");
                    }
                }
                else return null;
            }
            Color hairColor;
            while (true){
                console.print("Цвет волос: (варианты цвета: " + Color.typesColor() + ") Ваш выбор: ");
                String line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()){
                    console.printError("Ты что? Глава музыкальной банды, и не крашенный? Бред полный, давай переделывай");
                    continue;
                }
                if (!line.isEmpty()){
                    try {
                        hairColor = Color.valueOf(line);
                        break;
                    }catch (NullPointerException | IllegalArgumentException e){
                        console.printError("Неверный формат ввода, давай заново");
                    }
                }
                else return null;
            }

            return new Person(name, passportID, eyeColor, hairColor);
            }catch (IllegalStateException | NoSuchElementException e){
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
