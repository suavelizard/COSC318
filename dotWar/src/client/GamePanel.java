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
    private Player player;
    private Wall wall1;
    private Player enemy;
    private ArrayList<Projectile> projectileArray = new ArrayList();
    private JLabel playerStats;
    private JLabel enemyStats;
    private JProgressBar playerHealth;
    public GamePanel() {
        //Get player information from server
        this.setLayout(null);
        initPlayers();
        setPreferredSize(new Dimension(1000, 768));
        setBounds(0, 0, 1000, 720);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
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
        player = new Player(15,15,"Zane","/assets/players/player.png");
        enemy = new Player(15,15,"Enemy");
        enemy.setHealth(90);
        wall1 = new client.entities.Wall(new Position(400,300),15,300);
        player.setPosition(new Position(20, 20));
        enemy.setPosition(new Position(950,690));
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g2d);
        enemy.draw(g2d);
        player.draw(g2d);
        wall1.draw(g2d);
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
            p.draw(g2d);
            p.move();
            if(checkOutOfBounds(p.getPosition()) > 0) {
                projectileArray.remove(p);
            }
            if(checkCollisions(p,enemy)){
                enemy.takeDamage(p.getDamage());
               //projectileArray.remove(p);
                //enemyStats.setText("" + enemy.getHealth());
                projectileArray.remove(p);
            }
//            int c = collision(enemy.getPosition(), p.getPosition());
//            if(c == 0){
//                enemy.takeDamage(p.getDamage());
//                projectileArray.remove(p);
//            }
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

    public boolean checkCollisions(Entity e1, Entity e2) {
        Rectangle r1 = e2.getBounds();
        Rectangle r2 = e2.getBounds();
        if(e1.equals(player) || e1.equals(player)){
            System.out.println("pew");
            return false;
        } else if(r1.intersects(r2)){
            //collision occurred!
            return true;
        } else{
            return false;
        }
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
        //default no collision 0
        //top 1
        //bottom 2
        //right 3
        //left 4
        //TODO: move player width and height inside player object
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
