package client.entities;

import client.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Zane on 2014-11-08.
 */
public class Wall extends Entity {

    private String type;
    private String wallImageString = "/assets/walls/wall1.gif";
    public Wall(Position p, int w,int h){
        super(p, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(wallImageString));
        Image img = ii.getImage();
        Image newimg = img.getScaledInstance(10, 21,  java.awt.Image.SCALE_SMOOTH);
        super.setImage(new ImageIcon(newimg).getImage());
   }

    public void draw(Graphics g) {
        BufferedImage bim = new BufferedImage(10,21,BufferedImage.TYPE_INT_RGB);
        bim.getGraphics().drawImage(this.getImage(),0,0,null);
        TexturePaint tp = new TexturePaint(bim, new Rectangle(bim.getWidth(), bim.getHeight(), 10, 21));
        ((Graphics2D) g).setPaint(tp);
        g.fillRect((int)this.getPosition().getX(), (int)this.getPosition().getX(), this.getWidth(), this.getHeight());
    }
    public void drawImage(Graphics g) {
        //g.drawImage(img, x, y, null);
    }

}
