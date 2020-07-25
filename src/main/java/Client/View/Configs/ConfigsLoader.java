package Client.View.Configs;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigsLoader {

    private Config config;

    private static ConfigsLoader configsLoader = new ConfigsLoader();

    private ConfigsLoader() {
        LoadConfigs();
    }

    public static ConfigsLoader getInstance() {
        return configsLoader;
    }

    private void LoadConfigs() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String path = "resources/Properties/";
            FileReader fileReader;
            fileReader = new FileReader(path + "config.json");
            config = objectMapper.readValue(fileReader, Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Config getConfig() {
        return config;
    }


}
