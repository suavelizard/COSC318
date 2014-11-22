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

import client.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Nicholas on 20/11/2014.
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener {
    //Lighting stuff
    protected final static float GRADIENT_SIZE = 200f;

    /** The fractions for our shadow gradient, going from 0.0 (black) to 1.0 (transparent). */
    protected final static float[] GRADIENT_FRACTIONS = new float[]{0f, 1f};

    /** The colors for our shadow, going from opaque black to transparent black. */
    protected final static Color[] GRADIENT_COLORS = new Color[] { new Color(36,43,51), new Color(0f,0f,0f,0f) };

    /** A Polygon object which we will re-use for each shadow geometry. */
    protected final static Polygon POLYGON = new Polygon();
    protected int mouseX =300, mouseY =300;
   // protected ArrayList<Shape> entities = new ArrayList<Shape>();
    //END LIGHTING STUFF


    private Player player;
    private Wall wall1;
    private Wall wall2;

    private Player enemy;
    private ArrayList<Projectile> projectileArray = new ArrayList();
    private ArrayList<Entity> entities = new ArrayList<Entity>(); //ARRAY OF ENTITIES TO RENDER LIGHTING ON
    private JLabel playerStats;
    private JLabel enemyStats;
    private JProgressBar playerHealth;
    public GamePanel() {
        //Get player information from server

        this.setLayout(null);
        initPlayers();
        setPreferredSize(new Dimension(1000, 768));
        setBounds(0, 0, 1000, 768);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        this.addMouseMotionListener(new MouseMoveListener());

        initLabels();
        
    }

    public void initLabels() {
        int width = this.getWidth();
        int height = this.getHeight();
        
        JLabel enemyLabel = new JLabel("Enemy Health:");
        enemyLabel.setSize(100, 25);
        enemyLabel.setLocation(width - 130, height - 150);
        enemyLabel.setForeground(Color.WHITE);
        add(enemyLabel);
        enemyStats = new JLabel("" + enemy.getHealth());
        enemyStats.setSize(25, 25);
        enemyStats.setLocation(width - 50, height - 150);
        enemyStats.setForeground(Color.WHITE);
        enemyStats.setBackground(Color.WHITE);
        add(enemyStats);

        JLabel playerLabel = new JLabel("Player Health:");
        playerLabel.setSize(100, 25);
        playerLabel.setLocation(25, height - 150);
        playerLabel.setForeground(Color.WHITE);
        add(playerLabel);
        playerStats = new JLabel("" + player.getHealth());
        playerStats.setSize(25, 25);
        playerStats.setLocation(105, height - 150);
        playerStats.setForeground(Color.WHITE);
        playerStats.setBackground(Color.WHITE);
        add(playerStats);
    }

    public void initPlayers() {
        //Placeholders

        player = new Player(15,15,"Zane","3");
        entities.add(player);
        enemy = new Player(15,15,"Enemy","2");
        enemy.setHealth(90);
        wall1 = new client.entities.Wall(new Position(400,300),15,300);
        wall2 = new client.entities.Wall(new Position(200,300),15,300,1);
        //entities.add(wall1);
        entities.add(wall2);

        player.setPosition(new Position(20, 20));
        enemy.setPosition(new Position(950,690));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);

        if(enemy.isVisible()){
            render((Graphics2D)g);

            enemy.draw(g2d);
        }
        if(player.isVisible()){
            render((Graphics2D)g); //lighting stuff
            player.draw(g2d);
        }
        //light
        Light light1 = new Light(new Position(300,300),25,25);
        light1.draw(g);
        wall2.draw(g2d);

        if(checkOutOfBounds(player.getPosition()) !=0){
            switch (checkOutOfBounds(player.getPosition())){
                case 1:
                    player.setPosition(new Position(player.getPosition().getX(), player.getPosition().getY()+15));
                    break;
                case 2:
                    player.setPosition(new Position(player.getPosition().getX(), player.getPosition().getY() - 15));
                    break;
                case 3:
                    player.setPosition(new Position(player.getPosition().getX() -15, player.getPosition().getY()));
                    break;
                case 4:
                    player.setPosition(new Position(player.getPosition().getX()+15, player.getPosition().getY()));
                    break;
            }
            player.updatePosition();
        } else {
            player.updatePosition();
        }

        for (Iterator<Projectile> iterator = projectileArray.iterator(); iterator.hasNext();) {
            Projectile p = iterator.next();
            p.move();
            p.draw(g2d);
            if(checkOutOfBounds(p.getPosition()) > 0) {
                iterator.remove();
            }
            if(checkCollisions(p,enemy) ){
                enemy.takeDamage(p.getDamage());
                iterator.remove();
            }
//            if(checkCollisions(p, wall1)){
//                p.bounce(wall1.getWallOrientation());
//            }
            if(checkCollisions(p, wall2)){
                p.bounce(wall2.getWallOrientation());
            }
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    public boolean collision(Position p1,Position p2) {
        boolean collide = true;
        //Check if left side of p1 is more than right side of p2
        if(p1.getX() > p2.getX()+5) {
            collide = false;
        }
        //Check if right side of p1 is less than left side of p2
        else if(p1.getX()+5 < p2.getX()) {
            collide = false;
        }
        else if(p1.getY() > p2.getY()+5) {
            collide = false;
        }
        else if(p1.getY()+5 < p2.getY()) {
            collide = false;
        }
        return collide;
    }
    //GENERAL COLLISION DETECTION FOR ALL ENTITIES
    public boolean checkCollisions(Entity e1, Entity e2) {
        //TODO: Find bug that makes boundries weird
        Rectangle r1 = e1.getBounds();
        Rectangle r2 = e2.getBounds();
        if(r1.intersects(r2)){
            //collision occurred!
            return true;
        } else{
            return false;
        }
    }
    /*
    DEFAULT not out of bounds 0
    TOP 1
    BOTTOM 2
    RIGHT 3
    LEFT 4
    */
    public int checkOutOfBounds(Position p){
        //TODO: move player width and height inside player object
        if(p.getY() < 0) {
            return 1;
        }
        if(p.getY()+player.getHeight() > this.getHeight()) {
            return 2;
        }
        if(p.getX()+player.getWidth() > this.getWidth()) {
            return 3;
        }
        if(p.getX() < 0) {
            return 4;
        }
        return 0;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        int moveSpeed = 2;
        player.keyPressed(e);

    }

    public void keyReleased(KeyEvent e){
        player.keyRelease(e);
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Position mousePos = new Position(e.getX(), e.getY());
        projectileArray.add(new Projectile(player.getPosition(), mousePos, 5, 5));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

//    EXPERIMENTAL SHAD0W/LIGHTING STUFF
    /** Called to render the frame. */
    protected void render(Graphics2D g) {
        //we'll use nice quality interpolation
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        g.setColor(Color.white);
//        g.drawString("FPS: "+fps, 10, 20);

        //render the shadows first
        renderShadows(g);

        //render each entity
//        for (int i=0; i<entities.size(); i++) {
//            Entity e = entities.get(i);
//            g.setColor(Color.WHITE);
//            //g.draw(e);
//        }
    }

    protected void renderShadows(Graphics2D g) {
        //old Paint object for resetting it later
        Paint oldPaint = g.getPaint();
        //System.out.println("oobo");
        //minimum distance (squared) which will save us some checks
        float minDistSq = GRADIENT_SIZE*GRADIENT_SIZE;
        //amount to extrude our shadow polygon by
        //use a large enough value to ensure that it is way off screen
        final float SHADOW_EXTRUDE = GRADIENT_SIZE*GRADIENT_SIZE;

        //we'll use a radial gradient from the mouse center
        final Paint GRADIENT_PAINT = new RadialGradientPaint(new Point2D.Float(mouseX, mouseY),
                GRADIENT_SIZE, GRADIENT_FRACTIONS, GRADIENT_COLORS);

        final Point2D.Float mouse = new Point2D.Float(mouseX, mouseY);

        //for each entity
        for (int i=0; i<entities.size(); i++) {
            Entity e = entities.get(i);

            Rectangle bounds = e.getBounds();

            //radius of Entity's bounding circle
            float r = (float)bounds.getWidth()/2f;

            //get center of entity
            float cx = (float)bounds.getX() + r;
            float cy = (float)bounds.getY() + r;

            //get direction from mouse to entity center
            float dx = cx - mouse.x;
            float dy = cy - mouse.y;

            //get euclidean distance from mouse to center
            float distSq = dx * dx + dy * dy; //avoid sqrt for performance

            //if the entity is outside of the shadow radius, then ignore
            if (distSq > minDistSq)
                continue;

            //normalize the direction to a unit vector
            float len = (float)Math.sqrt(distSq);
            float nx = dx;
            float ny = dy;
            if (len != 0) { //avoid division by 0
                nx /= len;
                ny /= len;
            }

            //get perpendicular of unit vector
            float px = -ny;
            float py = nx;

            //our perpendicular points in either direction from radius
            Point2D.Float A = new Point2D.Float(cx - px * r, cy - py * r);
            Point2D.Float B = new Point2D.Float(cx + px * r, cy + py * r);

            //project the points by our SHADOW_EXTRUDE amount
            Point2D.Float C = project(mouse, A, SHADOW_EXTRUDE);
            Point2D.Float D = project(mouse, B, SHADOW_EXTRUDE);

            //construct a polygon from our points
            POLYGON.reset();
            POLYGON.addPoint((int)A.x, (int)A.y);
            POLYGON.addPoint((int)B.x, (int)B.y);
            POLYGON.addPoint((int)D.x, (int)D.y);
            POLYGON.addPoint((int)C.x, (int)C.y);

            //fill the polygon with the gradient paint
            g.setPaint(GRADIENT_PAINT);
            g.fill(POLYGON);
        }

        //reset to old Paint object
        g.setPaint(oldPaint);
    }

    /** Projects a point from end along the vector (end - start) by the given scalar amount. */
    private Point2D.Float project(Point2D.Float start, Point2D.Float end, float scalar) {
        float dx = end.x - start.x;
        float dy = end.y - start.y;
        //euclidean length
        float len = (float)Math.sqrt(dx * dx + dy * dy);
        //normalize to unit vector
        if (len != 0) { //avoid division by 0
            dx /= len;
            dy /= len;
        }
        //multiply by scalar amount
        dx *= scalar;
        dy *= scalar;
        return new Point2D.Float(end.x + dx, end.y + dy);
    }

    /** Mouse motion listener for dynamic 2D shadows. */
    private class MouseMoveListener extends MouseMotionAdapter {

        public void mouseMoved(MouseEvent e) {
//            mouseX = e.getX();
//            mouseY = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
//            mouseX = e.getX();
//            mouseY = e.getY();
        }
    }
}
