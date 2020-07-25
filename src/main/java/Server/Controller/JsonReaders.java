package Server.Controller;

import Server.Model.Player;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JsonReaders {
    private static ObjectMapper objectMapper = new ObjectMapper();


    static Player PlayerJsonReader(String username) {
        String path = String.format("resources/players/%s.json", username);
        Player player = null;
        try {
            FileReader fileReader = new FileReader(path);
            player = objectMapper.readValue(fileReader, Player.class);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return player;
    }

    static HashMap<String, Player> loadPlayers() {
        HashMap<String, Player> map = new HashMap<>();
        try {
            File file = new File("resources/players");
            for (File file1 : file.listFiles()) {
                FileReader fileReader = new FileReader(file1);
                Player player = objectMapper.readValue(fileReader , Player.class);
                map.put(player.getUsername() , player);
            }
        }catch (IOException e){
        e.printStackTrace();
        }
        return map;
    }
}
