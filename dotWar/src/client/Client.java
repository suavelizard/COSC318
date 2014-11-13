package client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    static Client client = null;

    public Client() throws IOException, ClassNotFoundException{
        //System.out.println("Attempting to connect to server...");
        clientSocket = new Socket(SERVER_ADDRESS,SERVER_PORT);
        setUpStreams(clientSocket);
//        Object o = in.readObject();
//        System.out.println((String)o);
    }

    //set up streams
    private void setUpStreams(Socket s) throws IOException{
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public static void main(String args[]){
        final GUI mainUI = new GUI( "dotWar" );
        mainUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(mainUI,
                        "Are you sure to close this window?", "Really Closing?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    //client.out.writeObject();
                    System.exit(0);
                }
            }
        });

        mainUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try{
            mainUI.updateStatusTextareaText("Attempting to Connect to Server..");

            client = new Client();
            mainUI.updateStatusTextareaText((String) client.in.readObject());
        } catch(IOException ioe){
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }

    }
}
