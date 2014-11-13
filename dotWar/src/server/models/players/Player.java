package server.models.players;

import server.models.Entity;

/**
 * Created by Zane on 2014-11-08.
 */
public class Player extends Entity implements Runnable{
    private String IPAddress;
    private int port;
    private String name;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Player(String name){
        this.setName(name);
    }
    public Player(String IPAddress, int port, String name){
        this.setIPAddress(IPAddress);
        this.setName(name);
        this.setPort(port);
    }
    public void run(){

    }
}
