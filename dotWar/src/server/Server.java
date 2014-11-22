/*
 * Copyright (c) 2014 Zane Ouimet, Nicholas Wilkinson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
