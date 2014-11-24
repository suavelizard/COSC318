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

package client;

/**
 * Created by Zane on 2014-11-06.
 */
public class Position {
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //Move right r
    public void moveRight(double r) {
        this.x += r;
    }
    //Move left l
    public void moveLeft(double l) {
        this.x -= l;
    }

    //Move up u
    public void moveUp(double u) {
        this.y -= u;
    }

    //Move down d
    public void moveDown(double d) {
        this.y += d;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Position(Position p){
        this.setX(p.getX());
        this.setY(p.getY());
    }
    public String toString(){
        return "X: " + this.getX() + " Y: "+this.getY();
    }
    public Position add(Position p){
        Position pNew = new Position(this.getX() + p.getX(), this.getY() + p.getY());
        System.out.println("Doing math: "+this.toString()+" + "+ p.toString() + " = "+pNew.toString());
        return pNew;
    }

    public Position subtract(Position p){
        Position pNew = new Position(this.getX() - p.getX(), this.getY() - p.getY());
        System.out.println("Doing math: "+this.toString()+"  "+ p.toString() + " = "+pNew.toString());
        return pNew;
    }
}
