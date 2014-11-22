/**
 * Created by Zane on 2014-11-06.
 */
package client;

import java.awt.event.KeyEvent;

public class Player {
    private int health;
    private int moveSpeed;
    private int xMove;
    private int yMove;
    private boolean alive;
    private int score;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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

    public Player() {
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        this.setPosition(new Position(400.0,400.0));
    }
    
    //I don't think we need this, I added methods to the position class to handle movement
    public void updatePosition(){
        //movement
        position.moveRight(xMove);
        position.moveDown(yMove);
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
                xMove = -moveSpeed;
                break;
            case KeyEvent.VK_RIGHT:
                //move right
                //position.moveRight(moveSpeed);
                xMove = moveSpeed;
                break;
            case KeyEvent.VK_UP:
                //move up
                //position.moveUp(moveSpeed);
                yMove = -moveSpeed;
                break;
            case KeyEvent.VK_DOWN:
                //move down
                //position.moveDown(moveSpeed);
                yMove = moveSpeed;
                break;
            case KeyEvent.VK_D:
                //move right
                //position.moveRight(moveSpeed);
                xMove = moveSpeed;
                break;
            case KeyEvent.VK_A:
                //move left
                //position.moveLeft(moveSpeed);
                xMove = -moveSpeed;
                break;
            case KeyEvent.VK_W:
                //move up
                //position.moveUp(moveSpeed);
                yMove = -moveSpeed;
                break;
            case KeyEvent.VK_S:
                //move down
                //position.moveDown(moveSpeed);
                yMove = moveSpeed;
                break;
        }
    }

    public void keyRelease(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key){
            case KeyEvent.VK_LEFT:
                //move left
                //position.moveLeft(0);
                xMove = -0;
                break;
            case KeyEvent.VK_RIGHT:
                //move right
                //position.moveRight(0);
                xMove = 0;
                break;
            case KeyEvent.VK_UP:
                //move up
                //position.moveUp(0);
                yMove = -0;
                break;
            case KeyEvent.VK_DOWN:
                //move down
                //position.moveDown(0);
                yMove = 0;
                break;
            case KeyEvent.VK_D:
                //move right
                //position.moveRight(0);
                xMove = 0;
                break;
            case KeyEvent.VK_A:
                //move left
                //position.moveLeft(0);
                xMove = -0;
                break;
            case KeyEvent.VK_W:
                //move up
                //position.moveUp(0);
                yMove = -0;
                break;
            case KeyEvent.VK_S:
                //move down
                //position.moveDown(0);
                yMove = 0;
                break;
        }
    }
}
