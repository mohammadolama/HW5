package Server.Controller;

import Server.Model.Player;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonBuilders {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
    }

    public static void PlayerJsonBuilder(String username, Player player) {
        try {
            String path = String.format("resources/players/%s", username);
            path = path + ".json";
            File file = new File(path);
            file.getParentFile().mkdir();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(path);
            objectMapper.writeValue(fileWriter, player);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
