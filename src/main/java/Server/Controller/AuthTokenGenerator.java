package Server.Controller;

import java.security.SecureRandom;
import java.util.Base64;

public class AuthTokenGenerator {
    private static SecureRandom secureRandom = new SecureRandom();
    private static Base64.Encoder base64encoder = Base64.getUrlEncoder();

    public static String generateToken() {
        byte[] random = new byte[16];
        secureRandom.nextBytes(random);
        return base64encoder.encodeToString(random);
    }
}
