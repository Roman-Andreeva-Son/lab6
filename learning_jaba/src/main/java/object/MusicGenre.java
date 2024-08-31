package object;

public enum MusicGenre {
    PSYCHEDELIC_CLOUD_RAP,
    POST_ROCK,
    PUNK_ROCK,
    POST_PUNK;

    public static String typesGenre() {
        StringBuilder nameList = new StringBuilder();
        for (MusicGenre genreType : values()) {
            nameList.append(genreType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }

}
