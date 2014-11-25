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

import java.awt.*;

/**
 * Created by Zane on 2014-11-21.
 */

/**
 * This class is used to create game entities like walls, players, bullets, powerups, etc.
 */
public abstract class Entity {
    private Position position;
    private int width;
    private int height;
    private Image image;
    private boolean visible = true;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    //calculates and returns the boundries of an enttiy
    public Rectangle getBounds() {
        return new Rectangle((int)this.getPosition().getX(), (int)this.getPosition().getY(), this.getWidth(), this.getHeight());
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
