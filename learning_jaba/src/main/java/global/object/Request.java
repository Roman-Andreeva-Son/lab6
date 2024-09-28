package global.object;


import java.io.Serializable;

public class Request implements Serializable {
    private String commandMassage;
    private MusicBand musicBand;

    public String getCommandMassage(){
        return commandMassage;
    }
    public MusicBand getMusicBand(){
        return musicBand;
    }

    @Override
    public String toString(){
        return commandMassage;
    }

    public Request(String commandMassage, MusicBand musicBand){
        this.commandMassage=commandMassage;
        this.musicBand = musicBand;
    }


}
