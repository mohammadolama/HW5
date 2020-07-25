package Client.Controller;

import Client.Model.AuthToken;

import java.net.Socket;

public interface Request {
    public <T> T excute(AuthToken authToken, String value1 , String value2);
}
