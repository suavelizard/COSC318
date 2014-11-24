package client.entities;

import client.Position;

import java.awt.*;

/**
 * Created by Zane on 2014-11-23.
 */
public class Weapon extends Entity{
    private int damageModifier;
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
        super.setPosition(new Position(0, 0));
        super.setWidth(3);
        super.setHeight(3);
    }
    public Weapon(int damageModifier,int moveSpeedModifer, Color color){
        super.setPosition(new Position(0, 0));
        super.setWidth(3);
        super.setHeight(3);
        this.setColor(color);
        this.setDamageModifier(damageModifier);
        this.setMoveSpeedModifer(moveSpeedModifer);
        this.setType(1);
    }
    public void draw(Graphics g){
        g.fillOval(0,0,25,25);
    }
}
