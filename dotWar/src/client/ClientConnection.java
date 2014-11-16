package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Zane on 2014-11-06.
 * Handles creating connection to server
 */
public class ClientConnection {
    private ObjectOutputStream outputstream;
    private ObjectInputStream inputstream;
    private String SERVER_ADDRESS;
    private int SERVER_PORT;

    public ClientConnection(Socket clientSocket,String serverip) {
        try {
            clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            inputstream = new ObjectInputStream(clientSocket.getInputStream());
            outputstream = new ObjectOutputStream((clientSocket.getOutputStream()));
            this.SERVER_ADDRESS = serverip;
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    /**
     * Connects to the server then enters the processing loop.
     */
    private void run() throws IOException {

        // Make connection and initialize streams
        System.out.println("Server at:" + SERVER_ADDRESS );

        Socket socket = new Socket(SERVER_ADDRESS, 9264);
        System.out.println("New Socket created");

        inputstream = new ObjectInputStream(socket.getInputStream());
        System.out.println("New ObjectInputStream");

        outputstream = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("New ObjectOutputStream");

        String line;
        System.out.println("Beginning connection");


        // Process all messages from server, according to the protocol.
        while (true) {
            try {
                line = inputstream.readObject().toString();
                System.out.println(line);
                //processs packets!

                if (line.startsWith("[SERVER]: Enter Player Name:")) {
                    //outputstream.writeObject((Object)getName());
                } else if (line.startsWith("[SERVER]: Name accepted.")) {
                    //textField.setEditable(true);
                } else if (line.startsWith("MESSAGE")) {
                   // messageArea.append(line.substring(8) + "\n");
                }
            } catch(ClassNotFoundException clnfe){
                clnfe.printStackTrace();
            }

        }
    }
}
