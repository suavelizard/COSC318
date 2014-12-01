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
import java.io.Serializable;

/**
 * Created by Zane on 2014-11-06.
 */

public class Projectile extends Entity implements Serializable{
    private Position position;
    private Position startPosition;
    private double damage;
    private Position targetPos;
    private int moveSpeed;
    private double angle;
    private boolean angleSet;
    private Color color;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getTargetPos() {
        return targetPos;
    }

    public void setTargetPos(Position targetPos) {
        this.targetPos = targetPos;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public int getMoveSpeed() {
        return moveSpeed;
    }

    public void setMoveSpeed(int moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Projectile() {
        this.setPosition(new Position(400.0, 400.0));
        this.setStartPosition(new Position(400.0, 400.0));
        this.setMoveSpeed(10);
        this.setDamage(1);
        angleSet = false;
    }

    public Projectile(Position position, Position targetPos) {
        this.setPosition(position);
        this.setStartPosition(new Position(position));
        super.setWidth(3);
        super.setHeight(3);
        this.setTargetPos(targetPos);
        this.setMoveSpeed(10);
        this.setDamage(5);
        angleSet = false;
        this.setColor(new Color(78,205,196));
    }
    //with weapon modifer
    public Projectile(Position position, Position targetPos,Weapon w) {
        this.setPosition(position);
        this.setStartPosition(new Position(position));
        super.setWidth(3);
        super.setHeight(3);
        this.setTargetPos(targetPos);
        this.setMoveSpeed(w.getMoveSpeedModifer());
        this.setDamage(w.getDamageModifier());
        angleSet = false;
        this.setColor(w.getColor());
        this.setDamage(w.getDamageModifier());
        this.setType(w.getType());
    }

    public void move() {
        if(!angleSet) {
            double differenceX = this.getPosition().getX() - targetPos.getX();
            double differenceY = this.getPosition().getY() - targetPos.getY();
//            angle = (float) Math.atan2(differenceY, differenceX) * 180 / Math.PI;
            angle = (float) Math.atan2(differenceY, differenceX);

            angleSet = true;
        }
        
//        this.setPosition(new Position(this.getPosition().getX() - Math.cos(angle * Math.PI / 180) * moveSpeed,
//                this.getPosition().getY() - Math.sin(angle * Math.PI / 180) * moveSpeed));
        this.setPosition(new Position(this.getPosition().getX() - Math.cos(angle) * moveSpeed,
                this.getPosition().getY() - Math.sin(angle) * moveSpeed));

    }
    public void bounce(int direction){
        if(direction == 1){
            angle = angle -90;
        }
        else if(direction == 0){
            angle = angle +90;
        }
    }
    public void draw(Graphics g){
        //hardcoded shadow
//        g.setColor(Color.darkGray);
//        g.fillRect((int) this.getPosition().getX()-3, (int) this.getPosition().getY()-3, 4, 4);
        g.setColor(this.getColor());
        g.fillRect((int) this.getPosition().getX(), (int) this.getPosition().getY(), 4, 4);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = new Position(position);
    }
}
