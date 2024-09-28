package global.object;
import global.utility.Validatable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.Serializable;

public class Person implements Validatable, Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 46, Поле не может быть null
    private Color eyeColor; //Поле может быть null
    private Color hairColor; //Поле не может быть null
    public Person (String name, LocalDateTime birthday, String passportID, Color eyeColor, Color hairColor){
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }
    public Person (String name, String passportID, Color eyeColor, Color hairColor){
        this(name,LocalDateTime.now(), passportID, eyeColor, hairColor);
    }

    public String getPassportID(){
        return passportID;
    }

        public Person (String s){
            this.name = s.split("; ")[0];
//            try{this.birthday = LocalDateTime.parse((s.split("; ")[1]),DateTimeFormatter.ISO_DATE_TIME);} catch (DateTimeParseException e) {e.getMessage();}
            try{this.birthday = LocalDateTime.parse((s.split("; ")[1]),DateTimeFormatter.ISO_DATE_TIME);} catch (DateTimeParseException e) {birthday = null;}
            this.passportID = s.split("; ")[2].trim();
        try{this.eyeColor = s.split("; ")[3].equals("null")? null : Color.valueOf(s.split("; ")[3]);} catch(NullPointerException | IllegalArgumentException e){eyeColor = null;}
        try{this.hairColor = Color.valueOf(s.split("; ")[4]);} catch(NullPointerException | IllegalArgumentException e){eyeColor = null;}
    }
//public Person (String s){
//    this.name = s.split("; ")[0];
////            try{this.birthday = LocalDateTime.parse((s.split(";")[1]),DateTimeFormatter.ISO_DATE_TIME);} catch (DateTimeParseException e) {e.getMessage();}
//    try{this.birthday = LocalDateTime.now();} catch (DateTimeParseException e) {birthday = null;}
//    this.passportID = s.split(";")[2].trim();
//    try{this.eyeColor = null;} catch(NullPointerException | IllegalArgumentException e){eyeColor = null;}
//    try{this.hairColor = Color.RED;} catch(NullPointerException | IllegalArgumentException e){eyeColor = null;}
//}


    @Override
    public String toString() {
        return String.format("%s; %s; %s; %s; %s", name, birthday, passportID, eyeColor, hairColor);
    }
    @Override
    public boolean validate(){
        if (name == null || name.isEmpty()) return false;
        if (birthday == null) return false;
        if (passportID == null || passportID.length() > 46) return false;
        if (hairColor == null) return false;
        return true;
    }
}

