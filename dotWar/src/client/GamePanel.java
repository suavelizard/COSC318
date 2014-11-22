package client;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Nicholas on 20/11/2014.
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener {
    private Player player;
    private ArrayList<Projectile> projectileArray = new ArrayList();

    public GamePanel() {
        //Get player information from server
        initPlayers();
        setPreferredSize(new Dimension(800, 768));
        setBounds(0,0, 800, 768 );
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
    }

    public void initPlayers() {

        //Placeholders
        player = new Player();
        //enemy = new Player();
        player.setPosition(new Position(20, 20));
        //enemy.setPosition(new Position(300,300));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int)player.getPosition().getX(),(int)player.getPosition().getY(),player.getPlayerWidth(),player.getPlayerHeight());
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
        for (Projectile p : projectileArray) {
            g2d.fillRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), 4, 4);
            p.move();
            if(checkOutOfBounds(p.getPosition()) != 0) {
                projectileArray.remove(p);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }
    public int checkOutOfBounds(Position p){
        //default no collision 0
        //top 1
        //bottom 2
        //right 3
        //left 4
        //TODO: move player width and height inside player object
        if(p.getY() < 0) {
            return 1;
        }
        if(p.getY()+player.getPlayerHeight() > this.getBounds().getHeight()) {
            return 2;
        }
        if(p.getX()+player.getPlayerWidth() > this.getBounds().getWidth()) {
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
        //repaint();
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
        Position playerPos = new Position(player.getPosition().getX(),player.getPosition().getY());
        projectileArray.add(new Projectile(playerPos, mousePos, 5, 5));
        //repaint();
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
    public void checkCollisions(){
//        Rectangle playerBounds = player.getBounds();
//            if (r3.intersects(r2)) {
//                //player has been hit or touched another player
//        }
    }
}
