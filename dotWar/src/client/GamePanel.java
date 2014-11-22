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

import client.entities.Entity;
import client.entities.Player;
import client.entities.Projectile;
import client.entities.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nicholas on 20/11/2014.
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener {
    private Player player;
    private Wall wall1;
    private Player enemy;
    private ArrayList<Projectile> projectileArray = new ArrayList();
    private JLabel playerStats;
    private JLabel enemyStats;
    private JProgressBar playerHealth;
    public GamePanel() {
        //Get player information from server

        this.setLayout(null);
        initPlayers();
        setPreferredSize(new Dimension(1000, 768));
        setBounds(0, 0, 1000, 720);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        initLabels();
        
    }

    public void initLabels() {
        int width = this.getWidth();
        int height = this.getHeight();
        
        JLabel enemyLabel = new JLabel("Enemy Health:");
        enemyLabel.setSize(100, 25);
        enemyLabel.setLocation(width - 130, height - 150);
        enemyLabel.setForeground(Color.WHITE);
        add(enemyLabel);
        enemyStats = new JLabel("" + enemy.getHealth());
        enemyStats.setSize(25, 25);
        enemyStats.setLocation(width - 50, height - 150);
        enemyStats.setForeground(Color.WHITE);
        enemyStats.setBackground(Color.WHITE);
        add(enemyStats);

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

    public void initPlayers() {

        //Placeholders
        player = new Player(15,15,"Zane","/assets/players/player.png");
        enemy = new Player(15,15,"Enemy");
        enemy.setHealth(90);
        wall1 = new client.entities.Wall(new Position(400,300),15,300);
        player.setPosition(new Position(20, 20));
        enemy.setPosition(new Position(950,690));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        enemy.draw(g2d);
        player.draw(g2d);
        wall1.draw(g2d);
        if(checkOutOfBounds(player.getPosition()) !=0){
            switch (checkOutOfBounds(player.getPosition())){
                case 1:
                    player.setPosition(new Position(player.getPosition().getX(), player.getPosition().getY()+15));
                    break;
                case 2:
                    player.setPosition(new Position(player.getPosition().getX(), player.getPosition().getY() - 15));
                    break;
                case 3:
                    player.setPosition(new Position(player.getPosition().getX() -15, player.getPosition().getY()));
                    break;
                case 4:
                    player.setPosition(new Position(player.getPosition().getX()+15, player.getPosition().getY()));
                    break;
            }
            player.updatePosition();
            //playerStats.setVerticalAlignment((int)player.getPosition().getY());
        } else {
            player.updatePosition();
            //playerStats.setVerticalAlignment((int)player.getPosition().getY());
        }

        //for (int i =0; i < projectileArray.size(); i++) {

        for (Iterator<Projectile> iterator = projectileArray.iterator(); iterator.hasNext();) {
            Projectile p = iterator.next();
            p.move();
            p.draw(g2d);
            if(checkOutOfBounds(p.getPosition()) > 0) {
                iterator.remove();
            }
            if(checkCollisions(p,enemy) ){
                enemy.takeDamage(p.getDamage());
                iterator.remove();
            }
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
        Rectangle r1 = e1.getBounds();
        Rectangle r2 = e2.getBounds();
        if(r1.intersects(r2)){
            //collision occurred!
            return true;
        } else{
            return false;
        }
    }
    /*
    DEFAULT not out of bounds 0
    TOP 1
    BOTTOM 2
    RIGHT 3
    LEFT 4
    */
    public int checkOutOfBounds(Position p){
        //TODO: move player width and height inside player object
        if(p.getY() < 0) {
            return 1;
        }
        if(p.getY()+player.getHeight() > this.getHeight()) {
            return 2;
        }
        if(p.getX()+player.getWidth() > this.getWidth()) {
            return 3;
        }
        if(p.getX() < 0) {
            return 4;
        }
        return 0;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        int moveSpeed = 2;
        player.keyPressed(e);
        //repaint();
    }

    public void keyReleased(KeyEvent e){
        player.keyRelease(e);
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position mousePos = new Position(e.getX(), e.getY());
        projectileArray.add(new Projectile(player.getPosition(), mousePos, 5, 5));
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
    public void checkCollisions(){
//        Rectangle playerBounds = player.getBounds();
//            if (r3.intersects(r2)) {
//                //player has been hit or touched another player
//        }
    }
}
