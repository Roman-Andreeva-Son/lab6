package global.utility;

import java.nio.file.Files;
import java.nio.file.Paths;

public class IDgenerator {
    private int currentID = 1;
    private static final String ID_STORAGE_PATH = "id_storage.txt";

    public IDgenerator() {
        loadID();
    }

    public int generateID() {
        currentID++;
        saveID();
        return currentID;
    }

    private void saveID() {
        try {
            Files.write(Paths.get(ID_STORAGE_PATH), String.valueOf(currentID).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadID() {
        try {
            if (Files.exists(Paths.get(ID_STORAGE_PATH))) {
                String data = new String(Files.readAllBytes(Paths.get(ID_STORAGE_PATH)));
                currentID = Integer.parseInt(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}