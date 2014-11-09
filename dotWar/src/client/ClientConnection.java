package client;

import java.net.Socket;

/**
 * Created by Zane on 2014-11-06.
 * Handles creating connection to server
 */
public class ClientConnection {
    private Socket toServer;
    private Socket fromServer;

    public Socket getToServer() {
        return toServer;
    }

    public void setToServer(Socket toServer) {
        this.toServer = toServer;
    }

    public Socket getFromServer() {
        return fromServer;
    }

    public void setFromServer(Socket fromServer) {
        this.fromServer = fromServer;
    }

    public ClientConnection() {

    }
}
