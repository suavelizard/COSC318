package server;

import java.io.*;
import java.net.Socket;
import java.util.Iterator;

/**
 * Created by Zane on 2014-11-24.
 */
public class ClientConnection implements Runnable{
    private String IPAddress;
    private String name;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    private Socket socket;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    private boolean open = true;

    public ObjectOutputStream getToClient() {
        return toClient;
    }

    public void setToClient(ObjectOutputStream toClient) {
        this.toClient = toClient;
    }

    public ClientConnection(Socket s) throws IOException{
        //System.out.println("New client constructor");
        this.socket = s;
        /**
         * get the input and output streams associated with the socket.
         */
        try {
            toClient = new ObjectOutputStream(socket.getOutputStream());
            toClient.flush();
            this.fromClient = new ObjectInputStream(socket.getInputStream());
            Thread.sleep(200);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch (InterruptedException ie){
            ie.printStackTrace();
        }
    }
    public void run() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process() throws IOException {
        getClientName();
    }

    public void getClientName() throws IOException {
        try {
                toClient.writeObject("[SERVER]: Enter Player Name:");
                toClient.flush();
                String objectFromClient = (String) fromClient.readObject();
                System.out.println(objectFromClient + " is now connected.");
                if (objectFromClient.toString().equals("Disconnect")) {
                    //remove dc'd player
                    System.out.println("Client disconnected");
                    closeConnection();
                }


        } catch (IOException ioe) {
            System.err.println(ioe);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }
    //requests client update
    public void sendPlayerInfo(String s){
        try {
            toClient.writeObject("[SERVER]:" + s.toString());
            System.out.println("send post write");
            toClient.flush();
            System.out.println((String)fromClient.readObject());
            System.out.println("send post read");

        } catch (IOException ioe) {
            System.err.println(ioe);
            ioe.printStackTrace();
            setOpen(false);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
    }

    public void closeConnection() throws IOException{

            if (fromClient != null)
                fromClient.close();
            if (toClient != null)
                toClient.close();
            if (socket != null)
                socket.close();
            setOpen(false);

    }

    private void sendObject(Object o) {
        try {
            toClient.writeObject(o);
            toClient.flush();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
