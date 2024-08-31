package object;
import utility.Validatable;

public class Coordinates implements Validatable {
    private Float x; //Значение поля должно быть больше -764, Поле не может быть null
    private int y; //Значение поля должно быть больше -766
    public Coordinates (Float x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(String s){
        try{
            try { this.x = Float.parseFloat(s.split(",")[0]);} catch (NumberFormatException ignored) {}
            try { this.y = Integer.parseInt(s.split(",")[1]);} catch (NumberFormatException ignored) {}
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }
        public Float getX(){return x;}
    public int getY(){return y;}

    @Override
    public String toString(){
        return String.format("%s, %s", x, y);
    }
    @Override
    public boolean validate(){
        if (x == null || x <= -764) return false;
        if (y <= - 766) return false;
        return true;
    }
}
