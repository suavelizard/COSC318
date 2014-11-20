package client;

/**
 * Created by Zane on 2014-11-06.
 */
public class Position {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //Move right r
    public void moveRight(double r) {
        this.x += r;
    }

    //Move left l
    public void moveLeft(double l) {
        this.x -= l;
    }

    //Move up u
    public void moveUp(double u) {
        this.y -= u;
    }

    //Move down d
    public void moveDown(double d) {
        this.y += d;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
