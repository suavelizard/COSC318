package client;

/**
 * Created by Zane on 2014-11-06.
 */
public class Powerup {
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Powerup() {
        this.setPosition(new Position(400,400));
    }
}
