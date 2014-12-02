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
import client.entities.*;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Nicholas on 24/11/2014.
 */
public class ClientManager implements Runnable{
    private static final int HEIGHT = 790;
    private static final int WIDTH = 1000;
    private static ClientManager instance = null;
    static ArrayList<ClientConnection> ccArr = new ArrayList<ClientConnection>();
    static ArrayList<String> playerArray = new ArrayList<String>();
    static ArrayList<Projectile> projectileArray = new ArrayList<Projectile>();
    static ArrayList<Weapon> weaponArray = new ArrayList<Weapon>();
    static ArrayList<Wall> wallArray = new ArrayList<Wall>();
    static ArrayList<Player> players = new ArrayList<Player>();

    protected ClientManager() {
        //initWalls(10);
        initMap();
        initWeapons(6);
    }

    private void initWeapons(int i) {
        Weapon w = new Weapon(new Position(300,400),5,15,0, Color.ORANGE);
        Weapon w1 = new Weapon(new Position(400,200),10,5,1,Color.RED);
        Weapon w2 = new Weapon(new Position(200,500),5,20,2,new Color(199,244,100));


        weaponArray.add(w);
        weaponArray.add(w1);
        weaponArray.add(w2);
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
                for (ClientConnection client: ccArr){
                    System.out.println(client.getName() + "\n");
                }
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
        int deadPlayers = 0;
        int sentProjectiles = 0;
        while(true) {
            start = System.currentTimeMillis();
            synchronized (ccArr) {
                for (Iterator<ClientConnection> iterator = ccArr.iterator(); iterator.hasNext(); ) {
                    ClientConnection cc = iterator.next();
                    if(cc.isOpen()) {
                        synchronized (players) {
                            for (Iterator<Player> inIt = players.iterator(); inIt.hasNext(); ) {
                                Player p = inIt.next();
                                if (!p.isAlive()) {
                                    deadPlayers++;
                                }
                                if (p.getName() != null) {
                                    cc.sendObject(p);
                                    Object o = cc.readObject();
                                    if (o.getClass().toString().equals("class client.entities.Projectile")) {
                                        synchronized (projectileArray) {
                                            projectileArray.add((Projectile) o);
                                            System.out.println("Got projectile");
                                        }
                                    } else if (o.getClass().toString().equals("class client.entities.Player")) {
                                        if (p.getName().equals(cc.getName())) {
                                            p.setEqual(cc.getPlayer());
                                        }
                                    }
                                }
                            }
                        }
                        cc.sendObject(projectileArray);
                        sentProjectiles++;
                        cc.readObject();
                        if(sentProjectiles >= ccArr.size()) {
                            projectileArray.clear();
                            sentProjectiles = 0;
                        }
                    } else {
                        System.out.println("Client DC'd");
                        iterator.remove();
                        players.remove(cc.getPlayer());
                    }
                }

            }
        elapsed = System.currentTimeMillis() - start;
        try {
           // if (elapsed < 10) {
               // Thread.sleep(10 - elapsed);
           // } else {
                // don't starve the garbage collector
                Thread.sleep(0,5);
           // }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
//            System.out.println("end of the loop");
    }
}
    public boolean checkCollisions(Entity e1, Entity e2) {
        return e1.getBounds().intersects(e2.getBounds());

    }

    public int checkOutOfBounds(Entity e){
        //TODO: move player width and height inside player object
        if(e.getPosition().getY() < 0) {
            return 1;
        }
        if(e.getPosition().getY()+e.getHeight() > HEIGHT) {
            return 2;
        }
        if(e.getPosition().getX()+e.getWidth() > WIDTH) {
            return 3;
        }
        if(e.getPosition().getX() < 0) {
            return 4;
        }
        return 0;
    }

    public void initWalls(int numWalls){
        Random rnd = new Random();
        for(int i = 0; i < numWalls; i++) {
            int r = rnd.nextInt(700-25) + 25;
            int rposX = rnd.nextInt(700);
            int rposY = rnd.nextInt(900);
            //wallArray.add(new Wall(new Position(rposX,rposY),15,200,1));
            //wallArray.add(new Wall(new Position(rposX,rposY),400,15,0));
            wallArray.addAll(wallBox(new Position(rposX,rposY),15,200));
            wallArray.addAll(wallBox(new Position(rposX,rposY),400,15));

        }
    }

    public ArrayList<Wall> wallBox(Position p , int w, int h){
        ArrayList<Wall> wall = new ArrayList<Wall>();
        wall.add(new Wall(p,w,h));
        if(w>h){
            wall.add(new Wall(p,2,h,1));
            wall.add(new Wall(new Position(p.getX()+w-2,p.getY()),2,h,0));
        }
        else{
            wall.add(new Wall(p,w,2,0));
            wall.add(new Wall(new Position(p.getX(),p.getY()+h-2),w,2,1));
        }
        return wall;
    }

    public void initMap(){
        //Bottom Left
        wallArray.addAll(wallBox(new Position(430, HEIGHT - 230),15,200));
        wallArray.addAll(wallBox(new Position(30, HEIGHT - 230),400,15));
        //Bottom Right
        wallArray.addAll(wallBox(new Position(WIDTH - 430, HEIGHT - 230),15,200));
        wallArray.addAll(wallBox(new Position(WIDTH - 430, HEIGHT - 230),400,15));
        //Top Left
        wallArray.addAll(wallBox(new Position(430, 30),15,200));
        wallArray.addAll(wallBox(new Position(30, 230),415,15));
        //Top Right
        wallArray.addAll(wallBox(new Position(WIDTH - 430, 30),15,200));
        wallArray.addAll(wallBox(new Position(WIDTH - 430, 230),400,15));
        //Middle cross
        wallArray.addAll(wallBox(new Position((WIDTH/2), (HEIGHT/2)-92.5),15,200));
        wallArray.addAll(wallBox(new Position((WIDTH/2)-192.5, (HEIGHT/2)),400,15));
    }
}
