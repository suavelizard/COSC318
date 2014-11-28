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

package client;

import client.entities.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Zane on 2014-11-06.
 * Handles creating connection to server
 */
public class ClientConnection implements  Runnable{
    private static ClientConnection instance = null;
    static Socket socket;
    static ObjectOutputStream toServer;
    static ObjectInputStream fromServer;
    static String SERVER_ADDRESS = "localhost";
    private int SERVER_PORT;
    private String clientName;
    private Player player;


    private ArrayList<Projectile> projectileArray = new ArrayList();
    private ArrayList<Wall> wallArray = new ArrayList();
    private ArrayList<Player> playerArray = new ArrayList();
    private ArrayList<client.entities.Weapon> weaponArray = new ArrayList();

    public static String getServerAddress() {
        return SERVER_ADDRESS;
    }

    public static void setServerAddress(String serverAddress) {
        SERVER_ADDRESS = serverAddress;
    }

    protected ClientConnection() {
        /*try {
            socket = new Socket(SERVER_ADDRESS,9264);
            fromServer = new ObjectInputStream(socket.getInputStream());
            toServer = new ObjectOutputStream((socket.getOutputStream()));
        } catch (IOException ioe){
            ioe.printStackTrace();
        }*/
        player = new Player();
    }
    public static ClientConnection getInstance(){

        if(instance == null){
            instance = new ClientConnection();
        }
        return instance;
    }
    /**
     * Connects to the server then enters the processing loop.
     */
    public void run(){

        // Make connection and initialize streams
//        System.out.println("Server at:" + SERVER_ADDRESS );
//

        try{

            System.out.println("Attempting to connect to server at: " + getServerAddress());
            socket = new Socket(SERVER_ADDRESS, 9264);
            System.out.println("New Socket created");

            fromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println("New ObjectInputStream");

            toServer = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("New ObjectOutputStream");
            toServer.flush();

            String line = "Server Message Here";
            System.out.println("Beginning connection");

            line = fromServer.readObject().toString();
            System.out.println(line);
            if (line.startsWith("[SERVER]: Enter Player Name:")) {
                toServer.writeObject(clientName);
                toServer.flush();
                System.out.println("[" +clientName + "]: " + clientName);
                //player.setName(clientName);
            }
            player = new Player((Player)fromServer.readObject());
            player.setName("FUCK YOU");
            //System.out.println(player.toString());
            //player.initImage(player.getPlayerImageString());
            toServer.writeObject(player);
            toServer.flush();
           /* System.out.println(line);
            if (line.startsWith("[SERVER]:[Player]")) {
                String [] playerPacket = line.split(",");
                if(playerPacket[1].equals(getName())){
                    System.out.println("Self recieved");
                    player.setHealth(Integer.parseInt(playerPacket[2]));
                    player.setAlive(Boolean.parseBoolean(playerPacket[3]));
                    player.setWeapon(null);
                    player.setPosition(new Position(Integer.parseInt(playerPacket[5]), Integer.parseInt(playerPacket[6])));
                    player.setVisible(true);
                    player.setPlayerImageString(playerPacket[8]);
                    ImageIcon ii = new ImageIcon(player.getPlayerImageString());
                    Image img = ii.getImage();
                    Image newimg = img.getScaledInstance(15, 15,  java.awt.Image.SCALE_SMOOTH);
                    player.setImage(newimg);
                    System.out.println(player.toString());
                }
                toServer.writeObject("Player initialized");
                toServer.flush();
            }*/

            // Process all messages from server, according to the protocol.
            while (true) {
                Player p;
                p = new Player((Player)fromServer.readObject());
                //line = fromServer.readObject().toString();
                //System.out.println(line);

                //processs packets!
                //toServer.writeObject("NAME TEST");
                if (!p.getName().equals(getName())) {
                    if(playerArray.contains(p)){
                        System.out.println("Updating:"+p.getName());
                        playerArray.get(playerArray.indexOf(p)).setPosition(p.getPosition());
                        System.out.println(playerArray.get(playerArray.indexOf(p)).getPosition().toString());
                    }
                    else {
                        playerArray.add(p);
                        System.out.println("Adding "+p.getName());
                    }

                }
                else {
                    System.out.println("Self recieved");
                }
                toServer.writeObject(player);
                System.out.println("Client:" + player.getPosition().toString());
                System.out.println("Server:" + p.getPosition().toString());
                toServer.flush();

            }

        } catch(ClassNotFoundException clnfe){
            clnfe.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {
            try {
                toServer.writeObject("Disconnect");
                toServer.flush();
                toServer.close();
                fromServer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setName(String name) {
        this.clientName = name;
    }

    public String getName(){
        return clientName;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void updatePlayerPosition(Position p) {
        player.setPosition(p);
    }

    public ArrayList<Player> getPlayerArray() {
        return playerArray;
    }
}

