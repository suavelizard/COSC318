package client;

import javax.swing.*;

/**
 * Created by Zane on 2014-11-06.
 */
public class Client {
    public static void main(String args[]){
        //Create client.Connection
        GUI mainUI = new GUI( "dotWar" );

        mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
