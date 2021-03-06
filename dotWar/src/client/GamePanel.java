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
import client.entities.Weapon;
import server.*;
import server.ClientConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Nicholas on 20/11/2014.
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener {
    private Player player;
    private Wall wall1;
    private Wall wall2;
    private client.ClientConnection cc = client.ClientConnection.getInstance();
    private Player enemy;
    private ArrayList<Projectile> projectileArray = new ArrayList();
    private ArrayList<Wall> wallArray = new ArrayList();
    private ArrayList<Player> playerArray = new ArrayList();
    private ArrayList<Weapon> weaponArray = new ArrayList();
    private long start, elapsed, wait;

    private JLabel playerStats;
    private JLabel enemyStats;
    private JProgressBar playerHealth;
    public GamePanel() {
        //Get player information from server
        //player.setName(cc.getName());
        this.setLayout(null);
        initPlayers();
        //initWalls(10);
        wallArray.addAll(cc.getWallArray());
        setPreferredSize(new Dimension(1000, 768));
        setBounds(0, 0, 1000, 790);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);


        projectileArray = cc.getProjectileArray();
        //initLabels();

    }

    public void initLabels() {
        int width = this.getWidth();
        int height = this.getHeight();

//        JLabel enemyLabel = new JLabel("Enemy Health:");
//        enemyLabel.setSize(100, 25);
//        enemyLabel.setLocation(width - 130, height - 150);
//        enemyLabel.setForeground(Color.WHITE);
//        add(enemyLabel);
//        enemyStats = new JLabel("" + enemy.getHealth());
//        enemyStats.setSize(25, 25);
//        enemyStats.setLocation(width - 50, height - 150);
//        enemyStats.setForeground(Color.WHITE);
//        enemyStats.setBackground(Color.WHITE);
//        add(enemyStats);

        JLabel playerLabel = new JLabel("Player Health:");
        playerLabel.setSize(100, 25);
        playerLabel.setLocation(25, height - 150);
        playerLabel.setForeground(Color.WHITE);
        add(playerLabel);
        playerStats = new JLabel("" + player.getHealth());
        playerStats.setSize(25, 25);
        playerStats.setLocation(105, height - 150);
        playerStats.setForeground(Color.WHITE);
        playerStats.setBackground(Color.WHITE);
        add(playerStats);
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
    public void initPlayers() {
        //Placeholders
        Random rnd = new Random();

        //player = new Player(15,15,"Zane","1");
        //System.out.println("Game panel initPlayer");
        player = new Player(cc.getPlayer());
        //System.out.println(player.toString());
        //player.setWeapon(new Weapon(2,10,1,Color.RED));
        /*playerArray.add(new Player(15, 15, "Enemy", "" + (rnd.nextInt(3))));
        playerArray.add(new Player(15,15,"Bob",""+(rnd.nextInt(3))));
        playerArray.add(new Player(15,15,"Steve",""+(rnd.nextInt(3))));*/
        playerArray = cc.getPlayerArray();
        Weapon w = new Weapon(new Position(200,500),5,12,0,Color.ORANGE);
        Weapon w1 = new Weapon(new Position(200,500),10,8,1,Color.RED);
        Weapon w2 = new Weapon(new Position(200,500),5,16,2,new Color(199,244,100));
        w.setPosition(new Position(300,400));
        w1.setPosition(new Position(400,200));
        w2.setPosition(new Position(200,500));

        weaponArray.add(w);
        weaponArray.add(w1);
        weaponArray.add(w2);
//
//        enemy = new Player(15,15,"Joe","2");
//        enemy.setHealth(90);

        //player.setPosition(new Position(20, 20));
        for(Player eP:playerArray){
            int rposX = rnd.nextInt(768);
            int rposY = rnd.nextInt(1000);
            eP.setPosition(new Position(rposX,rposY));
        }
        Position p1 = new Position(10,10);
        Position p2 = new Position(10,10);
        p1.add(p2);
       // player.die();
        //player.setPosition(new Position(20, 20));
        //player.updatePosition();
        // enemy.setPosition(new Position(950,690));

    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        if (!player.isAlive()) {
            // Respawn code
            //TODO: Respawn menu
            player.setWinner(false);
            hideAll();
            g.drawImage(new ImageIcon(this.getClass().getResource("/assets/dotwarlogo.png")).getImage(), (this.getWidth() / 2) - 279, 200, null);
            g.setColor(new Color(255, 107, 107));

            Font font = new Font("Tahoma", Font.BOLD, 96);
            g.setFont(font);
            g.drawString("YOU LOSE!", (this.getWidth() / 2) - 250, 500);
            System.out.println("You Died");
            font = new Font("Tahoma", Font.PLAIN, 40);
            g.setFont(font);
            if(player.getLives() > 0) {
                g.drawString(player.getLives() + " lives remaining! \nClick to respawn", (this.getWidth() / 2) - 300, 600);
                font = new Font("Tahoma", Font.PLAIN, 20);
                g.setFont(font);
                for(Player p: playerArray){
                    ((Graphics2D) g).drawString(p.getName() + " - " + p.getLives()+" lives",this.getWidth()/2-180,625);
                }
            }
            if(start == 0)
                start = System.currentTimeMillis();
            player.updatePosition();
            cc.updatePlayer(player);
        } else {
            System.out.println("Player is alive");
            //If player isn't dead we care about everything else
            int remainingPlayers = playerArray.size();
            for (Player eP : playerArray) {
                if (eP.isAlive()) {
                    eP.draw(g2d);
                } else {
                    remainingPlayers--;
                    if (remainingPlayers == 0 && player.isAlive()) {
                        //TODO: Implement victory animation
                        player.setWinner(true);
                        hideAll();
                        //Victory conditions satisfied
                        System.out.println("Victory for " + player.getName());
//                    player.setVisible(false);
                        //g2d.setColor(new Color(85, 98, 112));
                        //g2d.fillRect(0, 0, 1366, 768, null);
//                        g.setColor(this.getBackground());
//                        g.fillRect(0, 0, 1366, 768);
                        g.setColor(new Color(199, 244, 100));
                        Font font = new Font("Tahoma", Font.BOLD, 96);
                        g.setFont(font);
                        g.drawString("YOU WIN!", (this.getWidth() / 2) - 220, 500);
                        g.setColor(new Color(199, 244, 100));
                        font = new Font("Tahoma", Font.PLAIN, 40);
                        g.setFont(font);
                        g.drawString("Click to Continue", (this.getWidth() / 2) - 220, 600);
                        g.drawImage(new ImageIcon(this.getClass().getResource("/assets/dotwarlogo.png")).getImage(), (this.getWidth() / 2) - 279, 200, null);
                        player.updatePosition();
                        cc.updatePlayer(player);
                    }
                }
            }
            for (Wall w : wallArray) {
                if (w.isVisible()) {
                    w.draw(g2d);
                }
            }

            for (Iterator<Weapon> iterator = weaponArray.iterator(); iterator.hasNext(); ) {
                Weapon w = iterator.next();
                if (w.isVisible()) {
                    g.setColor(w.getColor());
                    w.draw(g);
                }

                if (checkCollisions(player, w)) {
                    player.setWeapon(w);
                    w.setVisible(false);
                    iterator.remove();
                    System.out.println(player.getName() + " picked up a power up");

                }
            }
            if (player.isVisible()) {
                player.draw(g2d);
            }

            if (checkOutOfBounds(player) != 0) {
                switch (checkOutOfBounds(player)) {
                    case 1:
                        player.setPosition(new Position(player.getPosition().getX(), player.getPosition().getY() + 15));
                        break;
                    case 2:
                        player.setPosition(new Position(player.getPosition().getX(), player.getPosition().getY() - 15));
                        break;
                    case 3:
                        player.setPosition(new Position(player.getPosition().getX() - 15, player.getPosition().getY()));
                        break;
                    case 4:
                        player.setPosition(new Position(player.getPosition().getX() + 15, player.getPosition().getY()));
                        break;
                }
                player.updatePosition();
                cc.updatePlayer(player);
            } else {
                player.updatePosition();
                cc.updatePlayer(player);
                for (Wall wall : wallArray) {
                    if (checkCollisions(player, wall)) {

                        player.setPosition(player.getPosition().subtract(new Position((player.getRightMove() - player.getLeftMove()) * 2, (player.getDownMove() - player.getUpMove()) * 2)));
                        System.out.println(player.getName() + " hit wall ");

                    }
                }

            }
            synchronized (projectileArray) {
                for (Iterator<Projectile> iterator = projectileArray.iterator(); iterator.hasNext(); ) {
                    Projectile p = iterator.next();
                    p.move();
                    p.draw(g2d);
                    if (checkOutOfBounds(p) > 0) {
                        System.out.println("Projectile flew out of bounds!");
                        iterator.remove();
                    }
                    for (Wall w : wallArray) {
                        if (checkCollisions(p, w)) {
                            System.out.println("Projectile bounced off wall!" + w.getWallOrientation());
                            p.bounce(w.getWallOrientation());
                        }
                    }
                    for (Player eP : playerArray) {
                        if (checkCollisions(p, eP)) {
                            System.out.println("Projectile hit " + eP.getName());
                            eP.takeDamage(p.getDamage());
                            iterator.remove();
                        }
                    }
                    if(checkCollisions(p,player)){
                        player.takeDamage(p.getDamage());
                        iterator.remove();
                    }
                    //Hacky code to limit range!
                    if (p.getType() == 1) {
                        if (Math.abs(p.getStartPosition().getX() - p.getPosition().getX()) > 200 || Math.abs(p.getStartPosition().getY() - p.getPosition().getY()) > 200) {
                            iterator.remove();
                        }
                    } else if (p.getType() == 0) {
                        if (Math.abs(p.getStartPosition().getX() - p.getPosition().getX()) > 1000 || Math.abs(p.getStartPosition().getY() - p.getPosition().getY()) > 1000) {
                            iterator.remove();
                        }
                    } else if (p.getType() == 2) {
                        if (Math.abs(p.getStartPosition().getX() - p.getPosition().getX()) > 400 || Math.abs(p.getStartPosition().getY() - p.getPosition().getY()) > 400) {
                            iterator.remove();
                        }
                    }
                }
            }
            playerArray = cc.getPlayerArray();
        }
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    public boolean collision(Position p1,Position p2) {
        boolean collide = true;
        //Check if left side of p1 is more than right side of p2
        if(p1.getX() > p2.getX()+5) {
            collide = false;
        }
        //Check if right side of p1 is less than left side of p2
        else if(p1.getX()+5 < p2.getX()) {
            collide = false;
        }
        else if(p1.getY() > p2.getY()+5) {
            collide = false;
        }
        else if(p1.getY()+5 < p2.getY()) {
            collide = false;
        }
        return collide;
    }
    //GENERAL COLLISION DETECTION FOR ALL ENTITIES
    public boolean checkCollisions(Entity e1, Entity e2) {
        return e1.getBounds().intersects(e2.getBounds());
    }
    //TODO: Remove this
//    public int checkWallCollision(Entity e1, Entity e2){
//        int direction = 1;
//        Position e1TopLeft = e1.getPosition();
//        Position e1TopRight = new Position(e1.getPosition().getX()+e1.getWidth(),e1.getPosition().getY());
//        Position e1BottomLeft = new Position(e1.getPosition().getX(),e1.getPosition().getY()+e1.getHeight());
//        Position e1BottomRight = new Position(e1.getPosition().getX()+e1.getWidth(),e1.getPosition().getY()+e1.getHeight());
//
//        Position e2TopLeft = e2.getPosition();
//        Position e2TopRight = new Position(e2.getPosition().getX()+e2.getWidth(),e2.getPosition().getY());
//        Position e2BottomLeft = new Position(e2.getPosition().getX(),e2.getPosition().getY()+e2.getHeight());
//        Position e2BottomRight = new Position(e2.getPosition().getX()+e2.getWidth(),e2.getPosition().getY()+e2.getHeight());
//
//
//
//        return direction;
//    }
    /*
    DEFAULT not out of bounds 0
    TOP 1
    BOTTOM 2
    RIGHT 3
    LEFT 4
    */
    public int checkOutOfBounds(Entity e){
        if(e.getPosition().getY() < 0) {
            return 1;
        }
        if(e.getPosition().getY()+e.getHeight() > this.getHeight()) {
            return 2;
        }
        if(e.getPosition().getX()+e.getWidth() > this.getWidth()) {
            return 3;
        }
        if(e.getPosition().getX() < 0) {
            return 4;
        }
        return 0;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        int moveSpeed = 2;
        player.keyPressed(e);

    }

    public void keyReleased(KeyEvent e){
        player.keyRelease(e);
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!player.isAlive()) {
            if (player.getLives() > 0) {
                System.out.println("Respawn");

                elapsed = System.currentTimeMillis() - start;
                try {
                    if (elapsed > 1000) {

                        player.respawn();
                        showAll();
                        start = 0;
                    }

            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position mousePos = new Position(e.getX(), e.getY());
        //projectileArray.add(new Projectile(player.getPosition(), mousePos, 5, 5));
        System.out.println("Attack");
        for (Projectile p: player.attack(mousePos)){
            //projectileArray.add(p);
            cc.sendObject(p);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void hideAll(){
        player.setVisible(false);
        for (Player p:playerArray){
            p.setVisible(false);
        }
        for(Wall w: wallArray){
            w.setVisible(false);
        }
        for(Weapon w: weaponArray){
            w.setVisible(false);
        }
    }

    public void showAll(){
        player.setVisible(true);
        for (Player p:playerArray){
            if(p.isAlive()) {
                p.setVisible(true);
            }
        }
        for(Wall w: wallArray){
            w.setVisible(true);
        }
        for(Weapon w: weaponArray){
            w.setVisible(true);
        }
    }
}
