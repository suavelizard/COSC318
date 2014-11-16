package server;

import server.models.players.Player;
import server.models.players.PlayerHandler;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Zane on 2014-11-06.
 */
public class Server {
    static int MAX_PLAYERS = 10;
    static boolean SERVER_RUNNING = false;
    static int SERVER_SOCKET = 9264;

    static ServerSocket serverSocket = null;
    static Socket clientSocket = null;
    //static ArrayList<Player> connectedPlayers;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private static ArrayList<Player> connectedPlayers = new ArrayList<Player>();
    private static ArrayList<PlayerHandler> playerHandlers= new ArrayList<PlayerHandler>();
    public static void main(String args[]) {
        //connectedPlayers = new ArrayList<Player>();
        try {
            new Server();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public Server() throws IOException {
        System.out.println("Starting Server..");
        serverSocket = new ServerSocket(SERVER_SOCKET);
        SERVER_RUNNING = true;
        System.out.println("Server started successfully on port: " + SERVER_SOCKET);
        while (SERVER_RUNNING) {
            try {
                new PlayerHandler(serverSocket.accept(),connectedPlayers).start();
            } finally {
                serverSocket.close();
            }
        }
    }


}
