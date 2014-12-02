package server;

import client.entities.Player;
import client.entities.Wall;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Zane on 2014-11-24.
 */
public class ClientConnection implements Runnable{
    private String IPAddress;
    private String name;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;
    //private Socket socket;
    private DatagramSocket socket;
    private Player p;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public ClientConnection(DatagramSocket s) throws IOException{
        //System.out.println("New client constructor");
        this.socket = s;
       // this.socket.setTcpNoDelay(false);
        /**
         * get the input and output streams associated with the socket.
         */
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(9000);
            toClient = new ObjectOutputStream(baos);
            //toClient.flush();
            byte[] buffer = new byte[9000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length );
            //socket.send(packet);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            this.fromClient = new ObjectInputStream(bais);
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
                //String objectFromClient = (String) fromClient.readObject();
                String objectFromClient = (String)fromClient.readObject();
                setName(objectFromClient);

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
            toClient.flush();
            System.out.println((String)fromClient.readObject());

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

    public void sendObject(Object o) {
        try {
            toClient.writeObject(o);
            System.out.println(o.toString());
            toClient.flush();
            toClient.reset();
        }catch(IOException ioe){
            ioe.printStackTrace();
            setOpen(false);
        }
    }

    public void sendWalls(Object o) {
        try {
            ArrayList<Wall> walls = new ArrayList<Wall>((ArrayList<Wall>)o);
            for(Iterator<Wall> iterator = walls.iterator();iterator.hasNext();) {
                Wall wall = iterator.next();
                toClient.writeObject(wall.getBounds());
                toClient.flush();
                toClient.reset();
                System.out.println(wall.toString());
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
            setOpen(false);
        }
    }

    public Object readObject(){
        Object o = null;
        try {
            o = fromClient.readObject();
            if(o.getClass().toString().equals("class client.entities.Player")){
                p = new Player((Player)o);
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
            setOpen(false);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return o;
    }

    public Player getPlayer() {
        return p;
    }
}
