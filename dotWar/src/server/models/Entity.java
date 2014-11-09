package server.models;

import server.world.Position;

/**
 * Created by Zane on 2014-11-08.
 */
public class Entity {
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Entity(){
        this.position = new Position(0, 0);
    }

}
