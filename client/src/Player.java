/**
 * Created by Zane on 2014-11-06.
 */
public class Player {
    private int health;
    private int moveSpeed;
    private boolean alive;
    private int score;

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
    }
    
   // Player does stuff
    

    //hello world
}
