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

/**
 * Created by Zane on 2014-11-06.
 */
package client.entities;

import client.Position;
import client.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Player extends Entity{
    private int health;
    private int moveSpeed;
    private int rightMove;
    private int leftMove;
    private int upMove;
    private int downMove;
    private boolean alive;
    private int score;
    private Position position;
    private String name;
    private boolean isVisible= true;

    private String playerImageString = "default";

    public String getPlayerImageString() {
        return playerImageString;
    }

    public void setPlayerImageString(String playerImageString) {
        this.playerImageString = playerImageString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getRightMove() {
        return rightMove;
    }

    public void setRightMove(int rightMove) {
        this.rightMove = rightMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    //calculates damage
    public void takeDamage(double damage){

        if(this.getHealth() - damage <= 0){
            setAlive(false);
            this.setHealth(0);
            this.setAlive(false);
            this.setVisible(false);
        }else {
            this.setHealth(this.getHealth() - (int) damage);
        }
    }
    public Player() {
        super();
        super.setHeight(10);
        super.setWidth(10);
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        super.setPosition(new Position(400.0, 400.0));
    }
    //constructor with player size
    public Player(int width,int height){
        super.setWidth(width);
       super.setHeight(height);
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        super.setPosition(new Position(400.0, 400.0));

    }
    //
    public Player(int width, int height,String name){
        super.setWidth(width);
        super.setHeight(height);
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        super.setPosition(new Position(400.0, 400.0));
        this.name = name;
        ImageIcon ii = new ImageIcon(this.getClass().getResource(playerImageString));
        //super.setImage(ii.getImage());
        Image img = ii.getImage();
        Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        super.setImage(new ImageIcon(newimg).getImage());

    }
    //FULL CONSTRUCTOR
    public Player(int width, int height,String name,String imageString){
        super.setWidth(width);
        super.setHeight(height);
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        super.setPosition(new Position(400.0, 400.0));
        this.name = name;
        this.setPlayerImageString("/assets/players/player-"+imageString+".png");
        ImageIcon ii = new ImageIcon(this.getClass().getResource(playerImageString));
        Image img = ii.getImage();
        Image newimg = img.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        super.setImage(new ImageIcon(newimg).getImage());

    }
    public void draw(Graphics g){
        //draw player
        //HARDCODED SHADOW
//        g.setColor(Settings.getSHADOW_COLOR());
//        g.fillOval((int) this.getPosition().getX() - 3, (int) this.getPosition().getY() - 3, 15, 15);
//        g.setColor(Settings.getSHADOW_COLOR());
//        g.fillRect((int) this.getPosition().getX() + 13, (int) this.getPosition().getY()-8, 50, 5);
//
        //END SHADOW
        g.drawImage(this.getImage(), (int) this.getPosition().getX(), (int) this.getPosition().getY(), null);
        g.setColor(new Color(255, 107, 107));
        g.fillRect((int) this.getPosition().getX() + 10, (int) this.getPosition().getY()-5, 50, 5);
        g.setColor(new Color(199,244,100));
        g.fillRect((int) this.getPosition().getX() + 10, (int) this.getPosition().getY()-5, this.getHealth()/2, 5);
        g.drawString("" + this.getName(), (int) this.getPosition().getX() + 10, (int) this.getPosition().getY() - 10);
//        g.setColor(Color.BLACK);
//        int centerX = (int)this.getPosition().getX()+this.getWidth()/2;
//        int centerY = (int)this.getPosition().getY()+this.getHeight()/2 ;
//        double angle = Math.atan2(centerY - this.mousepos.getY() - this.mousepos.getX(), centerX - this.mousepos.getX()- this.mousepos.getY()) - Math.PI / 2;
//        ((Graphics2D)g).rotate(angle, centerX, centerY);
//        int[] pointsX= {(int)this.getPosition().getX()+this.getWidth(),(int)this.getPosition().getX()+this.getWidth(),(int)this.getPosition().getX()+this.getWidth()+5,};
//        int[] pointsY = {(int)this.getPosition().getY()-3, (int)this.getPosition().getY()-this.getHeight()+3,(int)this.getPosition().getY() -(this.getHeight()/2)};
//        g.fillPolygon(pointsX,pointsY,3);
//
//
//        //((Graphics2D)g).rotate(angle, centerX, centerY);
//        //g.fillRect((int) this.mousepos.getX() + 10, (int) this.mousepos.getY()-5, 50, 5);
//        ((Graphics2D)g).rotate(-angle, centerX, centerY);
    }
    //I don't think we need this, I added methods to the position class to handle movement
    public void updatePosition(){
        //movement
        position.moveRight(rightMove - leftMove);
        position.moveDown(downMove - upMove);
    }
    public void attack(Position mousePos){
        //attack
        Projectile projectile = new Projectile(position,mousePos,2,2);

    }
    //control keys
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key){
            case KeyEvent.VK_LEFT:
                //move left
                //position.moveLeft(moveSpeed);
                leftMove = moveSpeed;
                break;
            case KeyEvent.VK_RIGHT:
                //move right
                //position.moveRight(moveSpeed);
                rightMove = moveSpeed;
                break;
            case KeyEvent.VK_UP:
                //move up
                //position.moveUp(moveSpeed);
                upMove = moveSpeed;
                break;
            case KeyEvent.VK_DOWN:
                //move down
                //position.moveDown(moveSpeed);
                downMove = moveSpeed;
                break;
            case KeyEvent.VK_D:
                //move right
                //position.moveRight(moveSpeed);
                rightMove = moveSpeed;
                break;
            case KeyEvent.VK_A:
                //move left
                //position.moveLeft(moveSpeed);
                leftMove = moveSpeed;
                break;
            case KeyEvent.VK_W:
                //move up
                //position.moveUp(moveSpeed);
                upMove = moveSpeed;
                break;
            case KeyEvent.VK_S:
                //move down
                //position.moveDown(moveSpeed);
                downMove = moveSpeed;
                break;
        }
    }

    public void keyRelease(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key){
            case KeyEvent.VK_LEFT:
                //move left
                //position.moveLeft(0);
                leftMove = 0;
                break;
            case KeyEvent.VK_RIGHT:
                //move right
                //position.moveRight(0);
                rightMove = 0;
                break;
            case KeyEvent.VK_UP:
                //move up
                //position.moveUp(0);
                upMove = 0;
                break;
            case KeyEvent.VK_DOWN:
                //move down
                //position.moveDown(0);
                downMove = 0;
                break;
            case KeyEvent.VK_D:
                //move right
                //position.moveRight(0);
                rightMove = 0;
                break;
            case KeyEvent.VK_A:
                //move left
                //position.moveLeft(0);
                leftMove = 0;
                break;
            case KeyEvent.VK_W:
                //move up
                //position.moveUp(0);
                upMove = 0;
                break;
            case KeyEvent.VK_S:
                //move down
                //position.moveDown(0);
                downMove = 0;
                break;
        }
    }
//    public void mouseMoved(MouseEvent e) {
//        this.mousepos = new Position(e.getX(),e.getY());
//
//    }
//
//    public void mouseDragged(MouseEvent e) {
//
//    }
}
