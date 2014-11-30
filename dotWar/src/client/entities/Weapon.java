package client.entities;

import client.Position;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by Zane on 2014-11-23.
 */
public class Weapon extends Entity implements Serializable{
    private int damageModifier;
    /*weapon types:
    0 = default
    1 = shotgun
    2 = 2 pulse

     */
    private int type;
    private Color color;
    private  int sizeModifier;
    private int moveSpeedModifer;
    private int reloadTime;

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getMoveSpeedModifer() {
        return moveSpeedModifer;
    }

    public void setMoveSpeedModifer(int moveSpeedModifer) {
        this.moveSpeedModifer = moveSpeedModifer;
    }

    public int getDamageModifier() {
        return damageModifier;
    }

    public void setDamageModifier(int damageModifier) {
        this.damageModifier = damageModifier;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSizeModifier() {
        return sizeModifier;
    }

    public void setSizeModifier(int sizeModifier) {
        this.sizeModifier = sizeModifier;
    }

    public Weapon(){
        this.setPosition(new Position(0, 0));
        this.setWidth(10);
        this.setHeight(10);
        this.setType(0);
        this.setColor(Color.black);
    }
    public Weapon(int damageModifier,int moveSpeedModifer,int type, Color color){
        this.setPosition(new Position(0, 0));
        this.setWidth(10);
        this.setHeight(10);
        this.setColor(color);
        this.setDamageModifier(damageModifier);
        this.setMoveSpeedModifer(moveSpeedModifer);
        this.setType(type);
    }
    public void draw(Graphics g){
        g.fillOval((int)this.getPosition().getX(),(int)this.getPosition().getY(),this.getWidth(),this.getHeight());
    }
}
