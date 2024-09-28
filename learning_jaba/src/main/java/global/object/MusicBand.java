package global.object;

import global.utility.Validatable;
import global.utility.IDgenerator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.io.Serializable;


public class MusicBand implements Serializable, Validatable, Comparable<MusicBand>{
    private static final IDgenerator idGenerator = new IDgenerator();

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long numberOfParticipants; //Значение поля должно быть больше 0
    private Long albumsCount; //Поле может быть null, Значение поля должно быть больше 0
    private MusicGenre genre; //Поле может быть null
    private Person frontMan; //Поле не может быть null

    public MusicBand(int id, String name, Coordinates coordinates, LocalDate creationDate, long numberOfParticipants, Long albumsCount, MusicGenre genre, Person frontMan) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.albumsCount = albumsCount;
        this.genre = genre;
        this.frontMan = frontMan;
    }

    public MusicBand (String name, Coordinates coordinates, long numberOfParticipants, Long albumsCount, MusicGenre genre, Person frontMan){
        this (idGenerator.generateID(), name, coordinates, LocalDate.now(), numberOfParticipants, albumsCount, genre, frontMan);
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public Coordinates getCoordinates(){return coordinates;}
    public LocalDate getCreationDate(){return creationDate;}
    public long getNumberOfParticipants(){return numberOfParticipants;}
    public Long getAlbumsCount(){return albumsCount;}
    public MusicGenre getGenre(){return genre;}
    public Person getFrontMan(){return frontMan;}

    public void setId(int id) {this.id = id;}

    public static MusicBand fromArray(String[] a){
        int id;
        String name;
        Coordinates coordinates;
        LocalDate creationDate;
        long numberOfParticipants;
        Long albumsCount;
        MusicGenre genre;
        Person frontMan;
        try{
            try{
                id = Integer.parseInt(a[0]);
            } catch (NumberFormatException ignored){id = 0;}
            name = a[1];
            coordinates = new Coordinates(a[2]);
            try{
                creationDate = LocalDate.parse(a[3], DateTimeFormatter.ISO_LOCAL_DATE);
            } catch(DateTimeParseException e){creationDate = null;}
            try{
                numberOfParticipants = Long.parseLong(a[4]);
            } catch (NumberFormatException ignored){numberOfParticipants = 0;}
            try{
                albumsCount = a[5].equals("null")? null : Long.parseLong(a[5]);
            } catch (NumberFormatException ignored){albumsCount = null;}
            try{
                genre = a[6].equals("null")?null: MusicGenre.valueOf(a[6]);
            }catch (NullPointerException| IllegalArgumentException e){genre = null;}
            frontMan = new Person(a[7]);
            return new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, albumsCount, genre, frontMan);
        }catch (ArrayIndexOutOfBoundsException e){}
        return null;
    }
    public static String[] toArray(MusicBand musicBand){
        ArrayList<String> list = new ArrayList<String>();
        list.add(String.valueOf(musicBand.getId()));
        list.add(musicBand.getName());
        list.add(musicBand.getCoordinates().toString());
        list.add(musicBand.getCreationDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        list.add(String.valueOf(musicBand.getNumberOfParticipants()));
        list.add(musicBand.getAlbumsCount() == null? "null":musicBand.getAlbumsCount().toString());
        list.add(musicBand.getGenre() == null? "null":musicBand.getGenre().toString());
        list.add(musicBand.getFrontMan().toString());
        return list.toArray(new String[0]);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand that = (MusicBand) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, numberOfParticipants, albumsCount, genre, frontMan);
    }

    @Override
    public String toString() {
        return String.format("Music Band {id = %s, name = %s, coordinates = %s, creation date = %s, number of participants = %s, albums count = %s, genre = %s, front man = %s}",
                id, name, coordinates, creationDate, numberOfParticipants, albumsCount, genre, frontMan);
    }
    @Override
    public boolean validate(){
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (numberOfParticipants <= 0) return false;
        if (albumsCount <=0) return false;
        if (frontMan == null) return false;
        return true;
    }
    @Override
//    public int compareTo (MusicBand musicBand){
//        return (this.id - musicBand.getId());
//    }
    public int compareTo(MusicBand musicBand) {
        return this.name.compareTo(musicBand.getName());
    }
}

