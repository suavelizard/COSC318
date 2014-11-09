package client;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Zane on 2014-11-06.
 */
public class Client {
    private String SERVER_ADDRESS = "localhost";
    private int SERVER_PORT = 9264;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static Socket clientSocket;


    public Client() throws IOException, ClassNotFoundException{
        System.out.println("Attempting to connect to server...");
        clientSocket = new Socket(SERVER_ADDRESS,SERVER_PORT);
        setUpStreams(clientSocket);
        Object o = in.readObject();
        System.out.println((String)o);
    }

    //set up streams
    private void setUpStreams(Socket s) throws IOException{
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public static void main(String args[]){
        GUI mainUI = new GUI( "dotWar" );
        mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            new Client();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }

    }
}
