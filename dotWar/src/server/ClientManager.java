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
import client.entities.Entity;
import client.entities.Player;
import client.entities.Projectile;
import client.entities.Wall;
import client.entities.Weapon;

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
        initWalls(10);
        initWeapons(6);
    }

    private void initWeapons(int i) {
        Weapon w = new Weapon(5,15,0, Color.ORANGE);
        Weapon w1 = new Weapon(10,5,1,Color.RED);
        Weapon w2 = new Weapon(5,20,2,new Color(199,244,100));
        w.setPosition(new Position(300,400));
        w1.setPosition(new Position(400,200));
        w2.setPosition(new Position(200,500));

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
                        cc.readObject();
                    } else {
                        System.out.println("Client DC'd");
                        iterator.remove();
                        players.remove(cc.getPlayer());
                    }
                }
                projectileArray.clear();
            }
            /*synchronized (projectileArray) {
                for (Iterator<Projectile> iterator = projectileArray.iterator(); iterator.hasNext(); ) {
                    Projectile p = iterator.next();
                    p.move();
                    if (checkOutOfBounds(p) > 0) {
                        System.out.println("Projectile flew out of bounds!");
                        iterator.remove();
                    }
                    for (Wall w : wallArray) {

                        if (checkCollisions(p, w)) {
                            System.out.println("Projectile bounced off wall!");
                            p.bounce(w.getWallOrientation());
                        }
                    }
                    for (Player eP : players) {
                        if (checkCollisions(p, eP)) {
                            System.out.println("Projectile hit " + eP.getName());
                            eP.takeDamage(p.getDamage());
                            iterator.remove();
                        }
                        if (eP.getWeapon().getType() == 1) {
                            if (Math.abs(eP.getPosition().getX() - p.getPosition().getX()) > 200 || Math.abs(eP.getPosition().getY() - p.getPosition().getY()) > 200) {
                                iterator.remove();
                            }
                        } else if (eP.getWeapon().getType() == 0) {
                            if (Math.abs(eP.getPosition().getX() - p.getPosition().getX()) > 1000 || Math.abs(eP.getPosition().getY() - p.getPosition().getY()) > 1000) {
                                iterator.remove();
                            }
                        } else if (eP.getWeapon().getType() == 2) {
                            if (Math.abs(eP.getPosition().getX() - p.getPosition().getX()) > 400 || Math.abs(eP.getPosition().getY() - p.getPosition().getY()) > 400) {
                                iterator.remove();
                            }
                        }
                    }

                }


        }*/
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
            wallArray.add(new Wall(new Position(rposX,rposY),15,200,1));
            wallArray.add(new Wall(new Position(rposX,rposY),400,15,0));

        }
    }
}
