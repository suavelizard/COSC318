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
    private String name;
    private JTextArea chatHistory = null;
    ClientConnection cc = ClientConnection.getInstance();


    // Class constructor
    public GUI( String titleText, Socket clientSocket ) throws IOException, ClassNotFoundException{
        super(titleText);
        setJMenuBar(buildMenuBar());
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));

        JPanel game = new GamePanel();
        JScrollPane chatScroll = new JScrollPane();
        JPanel chatPane = new JPanel();
        JPanel composePanel = new JPanel();
        chatPane.setLayout(new BoxLayout(chatPane,BoxLayout.Y_AXIS));
        //chatPane.setPreferredSize(new Dimension(366,768));
        JTextField sendMessageField = new JTextField();
        JButton sendButton = new JButton("Send");
        chatHistory = new JTextArea();
        //chatScroll.setBounds(0,0,360,400);
        chatScroll.add(chatHistory);
        chatHistory.setEditable(true);
        //chatHistory.setBackground(Color.black);
        //chatHistory.setRows(20);
        //sendMessageField.setBackground(Color.red);
        //sendMessageField.setMaximumSize(new Dimension(366,50));
        game.setBackground(new Color(85,98,112));

        cp.add(game);
        chatPane.add(chatScroll);
        chatPane.add(sendMessageField);
        chatPane.add(sendButton);
//
        //chatPane.add(chatScroll);
//        composePanel.setBounds(0,401,360,200);
//        composePanel.add(sendMessageField,BorderLayout.NORTH);
//        composePanel.add(sendButton,BorderLayout.SOUTH);
//        chatPane.add(composePanel,BorderLayout.NORTH);
        chatHistory.setText("hello");
        //statusTextarea.setText("heel");
        cp.add(chatPane);
        setResizable(false);
        setBounds(0,0, 1366, 768 );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        serverConnect();

        long start, elapsed, wait;
        while(true) {
            start = System.currentTimeMillis();
            game.repaint();
            elapsed = System.currentTimeMillis() - start;
            try {
                if (elapsed < 20) {
                    Thread.sleep(20 - elapsed);
                } else {
                    // don't starve the garbage collector
                    Thread.sleep(5);
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
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

    public void serverConnect() {
        serverIp = JOptionPane.showInputDialog(this, "New Server Connection", "Enter server address");
        name = JOptionPane.showInputDialog(this, "New Server Connection", "Enter Unique Name");

        if(isValidIP(serverIp)){
            try {
                cc.setServerAddress(serverIp);
                cc.run();


                //ClientConnection.getInstance().run();

            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        JMenuItem source = (JMenuItem)(e.getSource());
        System.out.println(source.getText());
        if(source.getText().equals("Connect to Server")) {
            serverConnect();
        }
    }
    public boolean isValidIP(String ip){
        //test for valid ip
        return true;
    }
    public void sendPacket(Packet p){

    }


}
