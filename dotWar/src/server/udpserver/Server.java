package server.udpserver;

/**
 * Created by Zane on 2014-12-01.
 */
public class Server {
    public static void main(String[] Args) {
        try {
            new ClientConnectionThread().start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
