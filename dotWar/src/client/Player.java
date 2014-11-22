/**
 * Created by Zane on 2014-11-06.
 */
package client;

import java.awt.event.KeyEvent;

public class Player {
    private int health;
    private int moveSpeed;
    private int rightMove;
    private int leftMove;
    private int upMove;
    private int downMove;
    private boolean alive;
    private int score;
    private int playerHeight;
    private int playerWidth;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getPlayerWidth() {
        return playerWidth;
    }

    public void setPlayerWidth(int playerWidth) {
        this.playerWidth = playerWidth;
    }

    public int getPlayerHeight() {
        return playerHeight;
    }

    public void setPlayerHeight(int playerHeight) {
        this.playerHeight = playerHeight;
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

    public Player() {
        this.setPlayerHeight(10);
        this.setPlayerWidth(10);
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        this.setPosition(new Position(400.0,400.0));
    }
    //constructor with player size
    public Player(int width,int height){
        this.setPlayerHeight(width);
        this.setPlayerWidth(height);
        this.setHealth(100);
        this.setAlive(true);
        this.setScore(0);
        this.setMoveSpeed(3);
        this.setPosition(new Position(400.0, 400.0));
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
}
