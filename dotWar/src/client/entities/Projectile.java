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
 * Created by Zane on 2014-11-06.
 */

public class Projectile extends Entity{
    private double damage;
    private Position targetPos;
    private int moveSpeed;
    private double angle;
    private boolean angleSet;

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

    public Projectile() {
        super.setPosition(new Position(400.0, 400.0));
        this.setMoveSpeed(50);
        this.setDamage(1);
        angleSet = false;
    }

    public Projectile(Position position, Position targetPos, int moveSpeed, double damage) {
        super.setPosition(position);
        super.setWidth(3);
        super.setHeight(3);
        this.setTargetPos(targetPos);
        this.setMoveSpeed(moveSpeed);
        this.setDamage(damage);
        angleSet = false;
    }

    public void move() {
        if(!angleSet) {
            double differenceX = this.getPosition().getX() - targetPos.getX();
            double differenceY = this.getPosition().getY() - targetPos.getY();
            angle = (float) Math.atan2(differenceY, differenceX) * 180 / Math.PI;
            angleSet = true;
        }
        
        this.setPosition(new Position(this.getPosition().getX() - Math.cos(angle * Math.PI / 180) * moveSpeed,
                this.getPosition().getY() - Math.sin(angle * Math.PI / 180) * moveSpeed));


    }
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect((int) this.getPosition().getX(), (int) this.getPosition().getY(), 4, 4);
    }
}
