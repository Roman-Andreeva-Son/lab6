package managers;

import object.MusicBand;

import java.time.LocalDateTime;
import java.util.*;
/**
 * Класс для работы с коллекцией.
 * @author Roman
 */
public class CollectionManager {
    private int currentId = 1;
    private Map<Integer, MusicBand> bands = new HashMap<>();
    private Vector<MusicBand> collection = new Vector<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final CSVParser csvParser;
    private ArrayDeque<String> logStack = new ArrayDeque<>();
    private static Set<String> uniquePassportID = new HashSet<>();

    public CollectionManager(CSVParser csvParser){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.csvParser = csvParser;
    }
    /**
     * @return Последнее время инициализации
     */
    public LocalDateTime getLastInitTime(){ return lastInitTime;}
    /**
     * @return Последнее время сохранения
     */
    public LocalDateTime getLastSaveTime(){ return lastSaveTime;}
    /**
     * @return коллекция
     */
    public Vector<MusicBand> getCollection(){ return collection;}
    public static HashSet <String> getUniquePassportID(){return (HashSet<String>) uniquePassportID;}
    /**
     * @param id id Route
     * @return Route по id
     */
    public MusicBand byId(int id){
        return bands.get(id);
    }
    /**
     * @param musicBand MusicBand
     * @return содержит ли коллекция банду
     */
    public boolean isContain(MusicBand musicBand){ return musicBand == null || byId(musicBand.getId()) != null;}
    /**
     * @return свободный id
     */
    public int getFreeId(){
        while (byId(++currentId) != null);
        return currentId;
    }
    /**
     * Фиксирует изменения коллекции
     */
    public void update() {
        Collections.sort(collection);
    }
    /**
     * Добавляет Route
     */
    public boolean add(MusicBand musicBand) {
        if (isContain(musicBand)) return false;
        bands.put(musicBand.getId(), musicBand);
        collection.add(musicBand);
            update();
        return true;
    }
    /**
     * Удаляет Route по id
     */
    public boolean remove(int id) {
        var musicBand = byId(id);
        if (musicBand == null) return false;
        bands.remove(musicBand.getId());
        collection.remove(musicBand);
        update();
        return true;
    }

    /**
     * @return загрузка(инициализация) коллекции
     */
    public boolean loadCollection() {

        collection.clear();
        bands.clear();
        csvParser.readCollection(collection);
        lastInitTime = LocalDateTime.now();
        for (var e : collection) {
            if (byId(e.getId()) != null) {
                collection.clear();
                bands.clear();
                return false;
            } else {
                if (e.getId() > currentId) currentId = e.getId();
                bands.put(e.getId(), e);
            }
            if (e.getFrontMan().getPassportID() != null) {
                uniquePassportID.add(e.getFrontMan().getPassportID());
            }
        }
        update();
        return true;
    }
    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        csvParser.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }
    /**
     * Создаёт транзакцию или добавляет операцию в транзакцию
     */
    public void addLog(String cmd, boolean isFirst) {
        if (isFirst) logStack.push("+");
        if (!cmd.isEmpty()) logStack.push(cmd);
    }
    /**
     * @return true в случае успеха.
     */
    public boolean swap(int id, int repId) {
        var e = byId(id);
        var re = byId(repId);
        if (e == null) return false;
        if (re == null) return false;
        var ind = collection.indexOf(e);
        var rind = collection.indexOf(re);
        if (ind < 0) return false;
        if (rind < 0) return false;
        e.setId(repId);
        re.setId(id);
        bands.put(e.getId(), e);
        bands.put(re.getId(), re);
        update();
        return true;
    }
    /**
     * @return максимальное кол-во участников коллекции
     */
    public long getMaxNOP() {
        long max = 0L;
        for (var e : collection) {
            if (e.getNumberOfParticipants() > max) max = e.getNumberOfParticipants();
        }
        return max;
    }
    /**
     * @return минимальное кол-во участников коллекции
     */
    public long getMinNOP() {
        long min = Long.MAX_VALUE;
        for (var e : collection) {
            if (e.getNumberOfParticipants() < min) min = e.getNumberOfParticipants();
        }
        return min;
    }


    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста";
        StringBuilder info = new StringBuilder();
        for (var musicBand : collection) {
            info.append(musicBand).append("\n\n");
        }
        return info.toString().trim();
    }
}




