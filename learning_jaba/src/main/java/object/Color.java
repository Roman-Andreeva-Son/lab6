package object;

public enum Color {
    RED,
    BLACK,
    BLUE,
    GRAY_BLUE,
    WHITE,
    BROWN,
    GREEN,
    GLAMUR_BLOND,
    GLAMUR_IBRUNETKI;
    public static String typesColor(){
        StringBuilder nameList = new StringBuilder();
        for (Color colorType : values()){
            nameList.append(colorType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
