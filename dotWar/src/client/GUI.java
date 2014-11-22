package client;

import client.packets.Packet;
import client.packets.connectPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOError;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Zane on 2014-11-06.
 */
public class GUI extends JFrame implements ActionListener{
    public String getServerIp() {
        return serverIp;
    }
    private String SERVER_ADDRESS = "localhost";
    private int SERVER_PORT = 9264;

    private String serverIp;
    private JTextArea statusTextarea = null;


    // Class constructor
    public GUI( String titleText, Socket clientSocket ) throws IOException, ClassNotFoundException{
        super(titleText);
        setJMenuBar(buildMenuBar());
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
        JPanel game = new GamePanel();
        JPanel chatPane = new JPanel();
        statusTextarea = new JTextArea();
        game.setBackground(Color.black);
        cp.add(game);

        chatPane.add(statusTextarea);
        cp.add(chatPane);

        setBounds(0,0, 1366, 768 );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        while(true) {
//            game.repaint();
//        }
    }

    public JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem connectItem = new JMenuItem("Connect to Server");
        JMenuItem exitItem = new JMenuItem("Exit");

        connectItem.addActionListener(this);

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        JMenu editMenu = new JMenu( "Edit" );
        JMenuItem editSettings = new JMenuItem( "Settings" );

        fileMenu.setMnemonic( KeyEvent.VK_F );
        exitItem.setMnemonic( KeyEvent.VK_X );

        menuBar.add(fileMenu);
        menuBar.add( editMenu );

        fileMenu.add(connectItem);

        fileMenu.add(exitItem);
        editMenu.add(editSettings);

        return menuBar;
    }
    public void actionPerformed(ActionEvent e){
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println(source.getText());
        if(source.getText() == "Connect to Server") {
            serverIp = JOptionPane.showInputDialog(this, "New Server Connection", "Enter server address:");
            if(isValidIP(serverIp)){
                try {
                    Socket clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);

                }catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public boolean isValidIP(String ip){
        //test for valid ip
        return true;
    }
    public void sendPacket(Packet p){

    }


}
