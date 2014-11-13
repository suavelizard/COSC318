package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Zane on 2014-11-06.
 */
public class GUI extends JFrame{
    public String getServerIp() {
        return serverIp;
    }

    private String serverIp;
    private JTextArea statusTextarea = null;
    public void updateStatusTextareaText(String s){
        statusTextarea.setText(statusTextarea.getText() +"\n"+ s);
    }
    // Class constructor
    public GUI( String titleText ) {
        super(titleText);
        setJMenuBar(buildMenuBar());
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
        JPanel game = new JPanel();
        JPanel chatPane = new JPanel();
        statusTextarea = new JTextArea();
        game.setBackground(Color.black);
        cp.add(game);

        chatPane.add(statusTextarea);
        cp.add(chatPane);

        //addWindowListener(this);

        setBounds(0,0, 1366, 768 );
        setVisible( true );
    }
    public JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu( "File" );
        JMenuItem connectItem = new JMenuItem( "Connect to Server" );
        JMenuItem exitItem = new JMenuItem( "Exit" );

        connectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                serverIp = JOptionPane.showInputDialog("Enter server address:");
            }
        });

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

        //fileMenu.add(fileNewPeer);
        fileMenu.add(connectItem);

        fileMenu.add(exitItem);
        editMenu.add(editSettings);


        return menuBar;
    }

}
