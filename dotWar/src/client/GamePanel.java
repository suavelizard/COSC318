package client;

import client.entities.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Nicholas on 20/11/2014.
 */
public class GamePanel extends JPanel implements KeyListener, MouseListener {
    private client.entities.Player player;
    private client.entities.Wall wall1;
    private Player enemy;
    private ArrayList<Projectile> projectileArray = new ArrayList();
    private JLabel playerStats;
    private JLabel enemyStats;
    private JProgressBar playerHealth;
    public GamePanel() {
        //Get player information from server
        initPlayers();
        setPreferredSize(new Dimension(800, 768));
        setBounds(0, 0, 800, 768);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        playerStats = new JLabel("" + player.getHealth());
        enemyStats = new JLabel("" + enemy.getHealth());
        enemyStats.setSize(100, 100);
        enemyStats.setBackground(Color.WHITE);
        add(enemyStats);
//        playerStats.setVerticalAlignment((int)player.getPosition().getY());
        add(playerStats);
    }

    public void initPlayers() {

        //Placeholders
        player = new client.entities.Player(100,100,"Zane");
        enemy = new Player();
        wall1 = new client.entities.Wall(new Position(400,300),15,300);
        player.setPosition(new Position(20, 20));
        enemy.setPosition(new Position(300,300));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);

        wall1.draw(g2d);
//        g2d.drawImage(player.getPlayerImage(), (int) player.getPosition().getX(), (int) player.getPosition().getY(), this);
//        g2d.setColor(new Color(255,107,107));
//        g2d.fillRect((int) player.getPosition().getX() + 10, (int) player.getPosition().getY()-5, 50, 5);
//        g2d.setColor(new Color(199,244,100));
//        g2d.fillRect((int) player.getPosition().getX() + 10, (int) player.getPosition().getY()-5, player.getHealth()/2, 5);
//        g2d.drawString("" + player.getName(), (int) player.getPosition().getX() + 10, (int) player.getPosition().getY() - 10);
        player.draw(g2d);
        g2d.setColor(Color.BLACK);
        g2d.fillRect((int) enemy.getPosition().getX(), (int) enemy.getPosition().getY(), 10, 10);
        g2d.setColor(new Color(255,107,107));
        g2d.fillRect((int) enemy.getPosition().getX() + 10, (int) enemy.getPosition().getY()-5, 50, 5);
        g2d.setColor(new Color(199,244,100));
        g2d.fillRect((int) enemy.getPosition().getX() + 10, (int) enemy.getPosition().getY()-5, enemy.getHealth()/2, 5);
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
            //playerStats.setVerticalAlignment((int)player.getPosition().getY());
        } else {
            player.updatePosition();
            //playerStats.setVerticalAlignment((int)player.getPosition().getY());
        }

        for (Projectile p : projectileArray) {
            g2d.fillRect((int) p.getPosition().getX(), (int) p.getPosition().getY(), 4, 4);
            p.move();
            if(checkOutOfBounds(p.getPosition()) > 0) {
                projectileArray.remove(p);
            }
            int c = collision(enemy.getPosition(), p.getPosition());
            if(c == 0){

                enemy.takeDamage(p.getDamage());
                projectileArray.remove(p);
            }
        }
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    public int collision(Position p1,Position p2) {
        int side = 0;
        //Check if left side of p1 is more than right side of p2
        if(p1.getX()-3 > p2.getX()+3) {
            side = 1;
        }
        //Check if right side of p1 is less than left side of p2
        if(p1.getX()+3 < p2.getX()-3) {
            side = 2;
        }
        if(p1.getY()-3 > p2.getY()+3) {
            side = 3;
        }
        if(p1.getY()+3 < p2.getX()-3) {
            side = 4;
        }
        return side;
    }
    //public int checkCollision(Ob)

    /*
    DEFAULT not out of bounds 0
    TOP 1
    BOTTOM 2
    RIGHT 3
    LEFT 4
    */
    public int checkOutOfBounds(Position p){
        if(p.getY() < 0) {
            return 1;
        }
        if(p.getY()+player.getHeight() > this.getBounds().getHeight()) {
            return 2;
        }
        if(p.getX()+player.getWidth() > this.getBounds().getWidth()) {
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
