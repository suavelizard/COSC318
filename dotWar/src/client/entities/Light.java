package client.entities;

import client.Position;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Zane on 2014-11-22.
 */
public class Light extends Entity{
    private String lightImageString = "/assets/lights/light-default.png";

    public String getLightImageString() {
        return lightImageString;
    }

    public void setLightImageString(String lightImageString) {
        this.lightImageString = lightImageString;
    }

    public Light() {
        super.setPosition(new Position(400.0, 400.0));
    }
    public Light(Position p, int w, int h){
        this.setPosition(p);
        this.setWidth(w);
        this.setHeight(h);
        ///this.setLightImageString("/assets/lights/player-"++".png");
        ImageIcon ii = new ImageIcon(this.getClass().getResource(lightImageString));
        Image img = ii.getImage();
        Image newimg = img.getScaledInstance(this.getWidth(), this.getHeight(),  java.awt.Image.SCALE_SMOOTH);
        super.setImage(new ImageIcon(newimg).getImage());
    }
    public void draw(Graphics g){
        g.drawImage(this.getImage(), (int) this.getPosition().getX(), (int) this.getPosition().getY(), null);

    }
}
