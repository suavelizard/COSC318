package client;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Nicholas on 20/11/2014.
 */
public class GamePanel extends JPanel implements KeyListener {
    private Player player;

    public GamePanel() {
        //Get player information from server
        initPlayers();
        setFocusable(true);
        addKeyListener(this);
    }

    public void initPlayers() {

        //Placeholders
        player = new Player();
        //enemy = new Player();
        player.setPosition(new Position(20,20));
        //enemy.setPosition(new Position(300,300));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        g2d.setColor(Color.WHITE);
        g2d.fillRect((int)player.getPosition().getX(),(int)player.getPosition().getY(),5,5);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        int moveSpeed = 2;
        player.keyPressed(e);
        repaint();
    }

    public void keyReleased(KeyEvent e){

    }

    public void keyTyped(KeyEvent e) {
    }
}
