/**
 * Created by Zane on 2014-11-06.
 */
public class PositionPacket {
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

    public PositionPacket(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
