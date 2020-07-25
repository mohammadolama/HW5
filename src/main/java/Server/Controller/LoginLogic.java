package Server.Controller;

import Server.Model.Player;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class LoginLogic {

    private static HashMap<String, String> accounts;
    private static ObjectMapper objectMapper;
    private final Timer timer;

    private static final LoginLogic loginLogic = new LoginLogic();

    private LoginLogic() {
        objectMapper = new ObjectMapper();
        loadAccounts();
        timer = new Timer(10000, al);
    }

    public static LoginLogic getInstance() {
        return loginLogic;
    }


    public String Login(String username, String password) {
        if (accounts.containsKey(username)) {
            if (password.equals(accounts.get(username))) {
                return "ok";
            } else {
                return "wrong password";
            }
        } else {
            return "user not found";
        }
    }

    public  String SignUp(String username, String password) {
        if (accounts.containsKey(username)) {
            return "user already exist";
        } else {
            synchronized (accounts) {
                Player player = new Player(username);
                JsonBuilders.PlayerJsonBuilder(username, player);
                accounts.put(username, password);
                updateAccounts();
            }
            return "ok";
        }
    }


    private static synchronized void updateAccounts() {
        try {
            FileWriter fileWriter = new FileWriter(new File("resources/Data/accounts.json"));
            objectMapper.writeValue(fileWriter, accounts);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void loadAccounts() {
        try {
            FileReader fileReader = new FileReader(new File("resources/Data/accounts.json"));
            accounts = objectMapper.readValue(fileReader, new TypeReference<HashMap<String, String > >() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ActionListener al = e -> loadAccounts();
}
