/*
 * Copyright (c) 2014 Zane Ouimet, Nicholas Wilkinson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
    //0 is VERTICAL
    //1 is HORIZONTAL
    private int wallOrientation = 0;

    public int getWallOrientation() {
        return wallOrientation;
    }
    public void setWallOrientation(int wallOrientation) {
        this.wallOrientation = wallOrientation;
    }

    public Wall(Position p, int w,int h){
        super(p, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(wallImageString));
        Image img = ii.getImage();
        Image newimg = img.getScaledInstance(10, 21,  java.awt.Image.SCALE_SMOOTH);
        super.setImage(new ImageIcon(newimg).getImage());
        this.setWallOrientation(0);
    }
    public Wall(Position p, int w,int h,int wallOrientation){
        super(p, w, h);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(wallImageString));
        Image img = ii.getImage();
        Image newimg = img.getScaledInstance(10, 21,  java.awt.Image.SCALE_SMOOTH);
        super.setImage(new ImageIcon(newimg).getImage());
        this.setWallOrientation(wallOrientation);
    }

    public void draw(Graphics g) {

//        BufferedImage bim = new BufferedImage(10,21,BufferedImage.TYPE_INT_RGB);
//        bim.getGraphics().drawImage(this.getImage(),0,0,null);
//        TexturePaint tp = new TexturePaint(bim, new Rectangle(bim.getWidth(), bim.getHeight(), 10, 21));
//        ((Graphics2D) g).setPaint(tp);
        g.setColor(Color.BLACK);
        g.fillRect((int)this.getPosition().getX(), (int)this.getPosition().getY(), this.getWidth(), this.getHeight());
    }
    public void drawImage(Graphics g) {
        //g.drawImage(img, x, y, null);
    }

}
