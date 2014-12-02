package client;

import client.entities.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Zane on 2014-12-01.
 */
public class UDPClientConnection implements  Runnable{
    private static UDPClientConnection instance = null;
    static String SERVER_ADDRESS = "localhost";

    public static String getServerAddress() {
        return SERVER_ADDRESS;
    }

    public static void setServerAddress(String serverAddress) {
        SERVER_ADDRESS = serverAddress;
    }

    public static UDPClientConnection getInstance(){

        if(instance == null){
            instance = new UDPClientConnection();
        }
        return instance;
    }
    protected UDPClientConnection() {
       Player  player = new Player();
        System.out.println("Constructor");
    }

    public void run() {
        try{
            System.out.println("Run");
            boolean run = true;
            while(run){
                doSendRequest(SERVER_ADDRESS,"Hello");
            }
            System.out.println("After do send");
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    public static void doSendRequest(String s, Object o)throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        System.out.println("streams");
        out.writeObject(o);
        System.out.println("wrrote 0");
        out.close();
        System.out.println("Close");
        byte[] obj = baos.toByteArray();
        System.out.println("To byte");
        baos.close();
        byte[] yourBytes = baos.toByteArray();
        // get a datagram socket
        DatagramSocket dsocket = new DatagramSocket();
        System.out.println("dsocket");
        // send request
        byte[] buf = new byte[512];
        System.out.println("buf[]");
        InetAddress address = InetAddress.getByName(s);
        System.out.println("inet address");
        DatagramPacket packet = new DatagramPacket(obj, obj.length, address, 9264);
        System.out.println("new packet");
        dsocket.send(packet);
        System.out.println("Sent");
        // get response
        packet = new DatagramPacket(buf, buf.length);
        System.out.println("Run");
        dsocket.receive(packet);
        System.out.println("recieve");
        // display response
        //String received = new String(packet.getData());
//        recievedt = new String(packet.getData());
//        System.out.println(recievedt);

        dsocket.close();
        System.out.println("close");
        //return received;
    }
}
