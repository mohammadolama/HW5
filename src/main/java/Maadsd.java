import Client.Model.AuthToken;
import Client.Model.Player;
import Server.Controller.JsonReaders;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.io.IOException;
import java.security.SecureRandom;

public class Maadsd {

    public static void main(String[] args) throws IOException, InterruptedException {
//        ObjectMapper objectMapper= new ObjectMapper();
//        Player player=new Player("Mojtaba" , new AuthToken("Mojtaba" , 12131) , 212121561 , 21 , 65 , 54 , 85);
//
//        String t = objectMapper.writeValueAsString(player);
//
//        System.out.println(t);
//
//        Thread.sleep(2000);
//
//        Player player1=objectMapper.readValue(t , Player.class);
//
//        System.out.println(player1.toString());
//
//        print(player1);
        
//        int[][] value = new int[8][8];
//        for (int i = 0; i < 8; i++) {
//            value[i][0]=i;
//        }
//
//        String s = objectMapper.writeValueAsString(value);
//        System.out.println(s);
//
//        int [][] v = objectMapper.readValue(s , int[][].class);
//
//        System.out.println(v.toString());
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                System.out.print(value[i][j]);
//            }
//            System.out.println();
//        }

//        SecureRandom secureRandom = new SecureRandom();
//        Base64.Encoder base64encoder = Base64.getUrlEncoder();
//
//        for (int i = 0; i < 100; i++) {
//            byte[] random = new byte[24];
//            secureRandom.nextBytes(random);
//            System.out.println(base64encoder.encodeToString(random));
//        }

        JsonReaders.load();
    }
    
    
    public static void print(Object object){
        Player player = (Player) object;
        System.out.println(player);
    }
}
