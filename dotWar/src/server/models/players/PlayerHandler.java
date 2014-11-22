/*
 * Copyright (c) 2014 Zane Ouimet, Nicholas Wilkinson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package server.models.players;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Zane on 2014-11-09.
 */
public class PlayerHandler extends Thread{
    private Player player;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    //private static HashSet<String> names = new HashSet<String>();
    private static ArrayList<Player> playerList = new ArrayList<Player>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();


    /**
     * Constructs a handler thread, squirreling away the socket.
     * All the interesting work is done in the run method.
     */
    public PlayerHandler(Socket socket, ArrayList<Player> playerList) {
        System.out.println("New client attempting to connect..");
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
            System.out.println("New thread for client at "+socket.getInetAddress()+" spawned.");
            // Create character streams for the socket.
            out = new ObjectOutputStream(socket.getOutputStream());
            //in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Streams set up for new client.");

            // Request a name from this client.  Keep requesting until
            // a name is submitted that is not already used.  Note that
            // checking for the existence of a name and adding the name
            // must be done while locking the set of names.
            System.out.println("Requesting unique name for player..");
            out.writeObject((Object)"[SERVER]: Enter Player Name:");

            while (true) {
                //player = new Player(); //use limited constructor for a temporary player (could jsut use a string..)
                String input = null;
                try {

                     input = in.readObject().toString();
                }catch (ClassNotFoundException clnfe){
                    clnfe.printStackTrace();
                }
                //System.out.println(input);
                player = new Player(socket.getInetAddress().toString(),socket.getPort(), input);
                //System.out.println(player.getName());
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
                        playerList.add(player);
                        break;
                    }
                }
            }

            // Now that a successful name has been chosen, add the
            // socket's print writer to the set of all writers so
            // this client can receive broadcast messages.
            System.out.println("Name accepted: " + player.getName());

            out.writeObject((Object)"[SERVER]: Name accepted.");
            //writers.add(out);

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

