package managers;

import com.opencsv.*;
import object.MusicBand;
import utility.Console;
import utility.StandardConsole;

import java.io.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;
/**
 * Класс, позволяющий сохранять и загружать коллекцию (в файл/из файла).
 * @author Roman
 */
public class CSVParser {
    private final String fileName;
    private final StandardConsole console;
    public CSVParser(String fileName, StandardConsole console){
        this.fileName = fileName;
        this.console = console;
    }
    /**
     * Преобразует коллекцию в CSV-строку.
     * @param collection коллекция
     * @return CSV-строка
     */
    private String collection2CSV(Collection<MusicBand> collection) {
        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw);
            for (MusicBand e : collection) {
                csvWriter.writeNext(MusicBand.toArray(e));
            }
            return sw.toString();
        } catch (Exception e) {
            console.printError("Ошибка сериализации");
            return null;
        }
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция
     * @return Считанная коллекция
     */
    public void writeCollection(Collection<MusicBand> collection) {
        BufferedWriter writer = null;
        try {
            String csv = collection2CSV(collection);
            if (csv == null) return;
            writer = new BufferedWriter(new FileWriter(fileName));
            try {
                writer.write(csv);
                writer.flush();
                console.println("Коллекция успешна сохранена в файл!");
            } catch (IOException e) {
                console.printError("Неожиданная ошибка сохранения");
            }
        } catch (IOException |NullPointerException e) {
            console.printError("Файл не найден");
        } finally {
            try {
                assert writer != null;
                writer.close();
            } catch(IOException | NullPointerException e) {
                console.printError("Ошибка закрытия файла");
            }
        }
    }

    /**
     * Преобразует CSV-строку в коллекцию.
     * @param s CSV-строка
     * @return коллекция
     */
    private Vector<MusicBand> CSV2collection(String s) {
        try {
            StringReader sr = new StringReader(s);
            CSVReader csvReader = new CSVReader(sr);
            Vector<MusicBand> ds = new Vector<>();
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                MusicBand musicBand = MusicBand.fromArray(record);
                if (musicBand.validate())
                    ds.add(musicBand);
                else
                    console.printError("Файл с колекцией содержит не действительные данные");
            }
            csvReader.close();
            return ds;
        } catch (Exception e) {
            console.printError("Ошибка десериализации");
            return null;
        }
    }

    /**
     * Считывает коллекцию из файла.
     * @param collection коллекция
     * @return Считанная коллекция
     */
    public void readCollection(Collection<MusicBand> collection) {
        if (fileName != null && !fileName.isEmpty()) {
            try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(fileName))) {
                StringBuilder s = new StringBuilder();
                int c;
                while ((c = fileReader.read()) != -1) {
                    s.append((char) c);
                }
                collection.clear();
                collection.addAll(Objects.requireNonNull(CSV2collection(s.toString())));
                if (!collection.isEmpty()) {
                    console.println("Коллекция успешно загружена!");
                    return;
                } else
                    console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (IOException exception) {
                console.printError("Непредвиденная ошибка при чтении файла!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        collection.clear();
    }
}