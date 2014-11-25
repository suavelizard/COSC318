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

    public ObjectOutputStream getToClient() {
        return toClient;
    }

    public void setToClient(ObjectOutputStream toClient) {
        this.toClient = toClient;
    }

    public ClientConnection(Socket s) throws IOException{
        this.socket = s;
        /**
         * get the input and output streams associated with the socket.
         */
        try {
            toClient = new ObjectOutputStream(socket.getOutputStream());
            toClient.flush();
            this.fromClient = new ObjectInputStream(socket.getInputStream());
        }catch(IOException ioe){
            ioe.printStackTrace();
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
            System.out.println(objectFromClient + " now connected.");
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

    public void getPlayerInfo(){
        try {
            toClient.writeObject("[SERVER]: Send Player Packet:");
            toClient.flush();
            System.out.println((String)fromClient.readObject());
        } catch (IOException ioe) {
            System.err.println(ioe);
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

    }

    private void sendObject(Object o) {
        try {
            toClient.writeObject(o);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
