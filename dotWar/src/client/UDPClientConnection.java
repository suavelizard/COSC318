package client;

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
    private static ClientConnection instance = null;
    static String SERVER_ADDRESS = "localhost";

    public static ClientConnection getInstance(){

        if(instance == null){
            instance = new ClientConnection();
        }
        return instance;
    }

    public void run() {
        try{
            doSendRequest(SERVER_ADDRESS,"Hello");
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    public static void doSendRequest(String s, Object o)throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);

        out.writeObject(o);
        out.close();
        byte[] obj = baos.toByteArray();
        baos.close();
        byte[] yourBytes = baos.toByteArray();
        // get a datagram socket
        DatagramSocket dsocket = new DatagramSocket();

        // send request
        byte[] buf = new byte[9000];
        InetAddress address = InetAddress.getByName(SERVER_ADDRESS);
        DatagramPacket packet = new DatagramPacket(obj, obj.length, address, 4445);
        dsocket.send(packet);

        // get response
        packet = new DatagramPacket(buf, buf.length);
        dsocket.receive(packet);

        // display response
        //String received = new String(packet.getData());
//        recievedt = new String(packet.getData());
//        System.out.println(recievedt);

        dsocket.close();
        //return received;
    }
}
