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

    public ClientConnection(Socket s){
        this.socket = s;
    }
    public void run() {
        try {
            process(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void process(Socket client) throws IOException {
        try {
            /**
             * get the input and output streams associated with the socket.
             */
            toClient = new ObjectOutputStream(socket.getOutputStream());
            toClient.flush();
            this.fromClient = new ObjectInputStream(socket.getInputStream());

            /** continually loop until the client closes the connection */
            String objectFromClient = "Client Message Here";
            while (true) {
                if(fromClient.available() != 0) {
                    objectFromClient = (String) fromClient.readObject();
                    System.out.println(objectFromClient);
                }
                if(objectFromClient.toString().equals("Disconnect")){
                    //remove dc'd player
                    System.out.println("Client disconnected");
                    Server.connectedPlayers.remove(this);
                    break;
                }
                for (Iterator<ClientConnection> iterator = Server.connectedPlayers.iterator(); iterator.hasNext();) {
                    ClientConnection cc = iterator.next();
                    cc.sendObject(objectFromClient);
                }

            }
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        } catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        finally {
            // close streams and socket
            if (fromClient != null)
                fromClient.close();
            if (toClient != null)
                toClient.close();
            if (client != null)
                client.close();
        }
    }

    private void sendObject(Object o) {
        try {
            toClient.writeObject(o);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
}
