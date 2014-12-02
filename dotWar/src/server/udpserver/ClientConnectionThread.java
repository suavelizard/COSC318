package server.udpserver;


import java.io.*;
import java.lang.Integer;
import java.lang.String;
import java.lang.System;
import java.lang.Thread;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class ClientConnectionThread extends Thread {

    protected DatagramSocket dsocket = null;
    protected BufferedReader in = null;
    private ObjectInputStream fromClient;

    protected boolean moreInfo = true;

    public ClientConnectionThread() throws IOException {
	this("server.udpserver.ClientConnectionThread");
    }

    public ClientConnectionThread(String name) throws IOException {
        super(name);
        dsocket = new DatagramSocket(9264);


    }

    public void run() {
        System.out.println("Run");
        while (moreInfo) {
            try {
                System.out.println("Try");
                byte[] buffer = new byte[512];
                //DatagramPacket packet = new DatagramPacket(buffer, buffer.length );
                //socket.send(packet);
                byte[] buf = new byte[1024];
                byte[] rbuf = new byte[512];
                System.out.println("rBuf");
                    // receive request
                DatagramPacket packet = new DatagramPacket(rbuf, rbuf.length);
                System.out.println("packet");


                System.out.println("Receive");
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                this.fromClient = new ObjectInputStream(bais);
                System.out.println("obj output");
                    // figure out response
                Object dObject = null;
                String request = new String(packet.getData());
                System.out.println(request);
                System.out.println("request");

                if (Integer.parseInt(request) == 0) {
                    System.out.println("hello1");
                    dObject = new Date().toString();
                }
                else {
                    System.out.println("hello2");
                    dObject = getNext();
                }
                System.out.println("After if");
                buf = dObject.toString().getBytes();

		    // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                dsocket.send(packet);
                System.out.println("Send");
            } catch (IOException e) {
                e.printStackTrace();
		moreInfo = false;
            }
        }
        dsocket.close();
        System.out.println("Close");
    }

    protected Object getNext() {
        Object returnValue = null;
        try {
            if ((returnValue = fromClient.readObject()) == null) {
                in.close();
		        moreInfo = false;
                returnValue = "Nothing to do";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in server.";
        } catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
        return returnValue;
    }
}
