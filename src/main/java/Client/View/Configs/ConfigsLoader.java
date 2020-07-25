package Client.View.Configs;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;

public class ConfigsLoader {

    private InfoConfig infoConfig;
    private MenuConfig menuConfig;
    private LoginConfig loginConfig;


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
            fileReader = new FileReader(path + "login.json");
            loginConfig = objectMapper.readValue(fileReader, LoginConfig.class);
            fileReader = new FileReader(path + "info.json");
            infoConfig = objectMapper.readValue(fileReader, InfoConfig.class);
            fileReader = new FileReader(path + "menu.json");
            menuConfig = objectMapper.readValue(fileReader, MenuConfig.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public InfoConfig getInfoConfig() {
        return infoConfig;
    }

    public void setInfoConfig(InfoConfig infoConfig) {
        this.infoConfig = infoConfig;
    }

    public MenuConfig getMenuConfig() {
        return menuConfig;
    }

    public void setMenuConfig(MenuConfig menuConfig) {
        this.menuConfig = menuConfig;
    }

    public LoginConfig getLoginConfig() {
        return loginConfig;
    }

    public void setLoginConfig(LoginConfig loginConfig) {
        this.loginConfig = loginConfig;
    }

    public static ConfigsLoader getConfigsLoader() {
        return configsLoader;
    }

    public static void setConfigsLoader(ConfigsLoader configsLoader) {
        ConfigsLoader.configsLoader = configsLoader;
    }
}
