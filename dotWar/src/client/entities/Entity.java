package client.entities;

import client.Position;

import java.awt.*;

/**
 * Created by Zane on 2014-11-21.
 */
public abstract class Entity {
    private Position position;
    private int width;
    private int height;
    private Image image;
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void draw(Graphics g){
        g.fillOval(0,0,25,25);
    }
    public Entity(){
        this.setPosition(new Position(0,0));
        this.setWidth(10);
        this.setHeight(10);
    }
    public Entity(Position p, int w, int h){
        this.setPosition(p);
        this.setWidth(w);
        this.setHeight(h);
    }

}
