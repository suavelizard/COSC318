package client;

import client.packets.connectPacket;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

/**
 * Created by Zane on 2014-11-06.
 */
public class Client {

    private static Socket clientSocket;
    static Client client = null;



    public static void main(String args[]){
        try {
            final GUI mainUI = new GUI("dotWar",clientSocket);
        } catch(Exception e){
            //catching all exceptions temporarily
        }
    }
}

