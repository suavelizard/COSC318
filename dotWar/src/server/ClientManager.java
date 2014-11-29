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

import client.Position;
import client.entities.Player;
import client.entities.Wall;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Nicholas on 24/11/2014.
 */
public class ClientManager implements Runnable{
    private static ClientManager instance = null;
     static ArrayList<ClientConnection> ccArr = new ArrayList<ClientConnection>();
    static ArrayList<String> playerArray = new ArrayList<String>();
    static ArrayList<String> projectileArray = new ArrayList<String>();
    static ArrayList<String> weaponArray = new ArrayList<String>();
    static ArrayList<Wall> wallArray = new ArrayList<Wall>();
    static ArrayList<Player> players = new ArrayList<Player>();

    protected ClientManager() {
        initWalls(10);
    }

    public static ClientManager getInstance(){
        if(instance==null)
            instance = new ClientManager();
        return instance;
    }

    public void newClient(Socket s) {
        try {
            synchronized (ccArr) {
                ccArr.add(new server.ClientConnection(s));

                new Thread(ccArr.get(ccArr.size() - 1)).start();
                //System.out.println("Number of strings in string array: " +stringarr.size());

                System.out.println("Number of connected clients: " + ccArr.size());
                Thread.sleep(200);

                Random rnd = new Random(System.currentTimeMillis());
                Player p = new Player(15, 15, ccArr.get(ccArr.size() - 1).getName(), "" + (rnd.nextInt(3)));
                p.setPosition(rnd.nextInt(400), rnd.nextInt(400));
                //playerArray.add(p.toString());

                synchronized (players) {
                    players.add(p);


                    ccArr.get(ccArr.size() - 1).sendObject(p);

                    ccArr.get(ccArr.size() - 1).readObject();
                    //ccArr.get(ccArr.size() - 1).sendObject(new Wall(new Position(20,20),20,20).getBounds());
                    ccArr.get(ccArr.size() - 1).sendWalls(wallArray);

                }
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long start, elapsed, wait;

        while(true) {
            start = System.currentTimeMillis();
            synchronized (ccArr) {
                for (Iterator<ClientConnection> iterator = ccArr.iterator(); iterator.hasNext(); ) {
                    ClientConnection cc = iterator.next();
                    synchronized (players) {
                        for (Iterator<Player> inIt = players.iterator(); inIt.hasNext(); ) {
                            Player p = inIt.next();
                            if (p.getName() != null) {
                                cc.sendObject(p);
                                cc.readObject();
                            }
                            if (p.getName().equals(cc.getName())) {
                                p.setPosition(cc.getPlayer().getPosition());
                            }
                            if (!cc.isOpen()) {
                                System.out.println("Client DC'd");
                                iterator.remove();
                                inIt.remove();
                            }
                        }
                    }

                }
            }
            elapsed = System.currentTimeMillis() - start;
            try {
                if (elapsed < 20) {
                    Thread.sleep(20 - elapsed);
                } else {
                    // don't starve the garbage collector
                    Thread.sleep(5);
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
//            System.out.println("end of the loop");
        }
    }

    public void initWalls(int numWalls){
        Random rnd = new Random();
        for(int i = 0; i < numWalls; i++) {
            int r = rnd.nextInt(700-25) + 25;
            int rposX = rnd.nextInt(700);
            int rposY = rnd.nextInt(900);
            wallArray.add(new Wall(new Position(rposX,rposY),15,200,1));
            wallArray.add(new Wall(new Position(rposX,rposY),400,15,0));

        }
    }
}
