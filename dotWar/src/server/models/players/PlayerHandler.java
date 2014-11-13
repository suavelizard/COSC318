package server.models.players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Zane on 2014-11-09.
 */
public class PlayerHandler extends Thread{
    private Player player;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    //private static HashSet<String> names = new HashSet<String>();
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();


    /**
     * Constructs a handler thread, squirreling away the socket.
     * All the interesting work is done in the run method.
     */
    public PlayerHandler(Socket socket, ArrayList<Player> playerList) {
        this.socket = socket;
    }

    /**
     * Services this thread's client by repeatedly requesting a
     * screen name until a unique one has been submitted, then
     * acknowledges the name and registers the output stream for
     * the client in a global set, then repeatedly gets inputs and
     * broadcasts them.
     */
    public void run() {
        try {

            // Create character streams for the socket.
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Request a name from this client.  Keep requesting until
            // a name is submitted that is not already used.  Note that
            // checking for the existence of a name and adding the name
            // must be done while locking the set of names.
            while (true) {
                out.println("[SERVER]: Enter Player Name:");

                player = new Player(in.readLine()); //use limited constructor for a temporary player (could jsut use a string..)
                if (player.getName() == null) {
                    return;
                }
                synchronized (playerList) {
                    boolean doesPlayerExist = false;
                    for(Player p: playerList){
                        if(player.getName() == p.getName()) {
                            doesPlayerExist = true;
                        }
                    }
                    if (!doesPlayerExist) {
                        playerList.add(new Player(socket.getInetAddress().toString(),socket.getPort(), player.getName()));
                        break;
                    }
                }
            }

            // Now that a successful name has been chosen, add the
            // socket's print writer to the set of all writers so
            // this client can receive broadcast messages.
            out.println("[SERVER]: Name accepted.");
            writers.add(out);

            // Accept messages from this client and broadcast them.
            // Ignore other clients that cannot be broadcast to.
            String input;
            while (true) {
                while ((input = in.readLine()) == null) {};

                for (PrintWriter writer : writers) {
                    writer.println("MESSAGE " + player.getName() + ": " + input);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            // This client is going down!  Remove its name and its print
            // writer from the sets, and close its socket.
            if (player != null) {
                playerList.remove(player);
            }
            if (out != null) {
                writers.remove(out);
            }
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}

