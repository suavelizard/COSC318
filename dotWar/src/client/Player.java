/**
 * Created by Zane on 2014-11-06.
 */
package client;

import java.awt.event.KeyEvent;

public class Player {
    private int health;
    private int moveSpeed;
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
        this.setMoveSpeed(10);
        this.setPosition(new Position(400.0,400.0));
    }
    public void updatePoisition(){
        //movement
    }
    public void attack(){
        //attack
    }
    //control keys
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_SPACE:
                //Do attack
                attack();
            case KeyEvent.VK_LEFT:
                //move left
            case KeyEvent.VK_RIGHT:
                //move right
            case KeyEvent.VK_UP:
                //move up
            case KeyEvent.VK_DOWN:
                //move down
        }
    }
}
