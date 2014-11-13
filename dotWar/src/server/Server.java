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
                while (true) {
                    new PlayerHandler(serverSocket.accept(),connectedPlayers).start();
                }
            } finally {
                serverSocket.close();
            }
            //clientSocket = serverSocket.accept(); //creates new sockets from the serverSocket connections this needs to be threaded
            //System.out.println("Client connection from: " + clientSocket.getInetAddress());
//            connectedPlayers.add(new Player(clientSocket.getInetAddress().toString(), SERVER_SOCKET, "Player"));
//            setUpStreams(clientSocket);
            //out.writeObject("[SERVER]: Welcome!");
//            String connectedPlayersString = "";
//            for (Player connectedPlayer : connectedPlayers) {
//                connectedPlayersString += connectedPlayer.getIPAddress() + ":\n";
//            }
//            out.writeObject("[SERVER]: " + connectedPlayersString);
        }
    }

//    public void sendData(byte[] data, InetAddress ipAddress, int port) {
//        DatagramPacket packetToPlayer = new DatagramPacket(data, data.length, ipAddress, port);
//        try {
//            out.writeObject(packetToPlayer);
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
//    }

//    public void sendDataToAllPlayers(byte[] data) {
//        for (Player connectedPlayer : connectedPlayers) {
//            //sendData(data, connectedPlayer.getIPAddress(),connectedPlayer.getPort());
//            // out.writeObject();
//        }
//    }

    //set up streams
    private void setUpStreams(Socket s) throws IOException {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

}
