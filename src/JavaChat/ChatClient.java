package JavaChat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Aldres on 11.10.17.
 */
public class ChatClient {
    private Socket socket;
    private DataInputStream console   = null;
    private DataOutputStream streamOut = null;

    public ChatClient(String serverName, int serverPort) {
        System.out.println("Connecting to the server: " + serverName + " on port:" + serverPort + " please wait...");
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected " + socket);
            start();

        }
        catch (UnknownHostException uhe) {
            System.out.println("Oops, Unknown host " + uhe.getMessage());
        }
        catch (IOException ioe){
            System.out.println("Oops, IOEXCEPTION " + ioe.getMessage());
        }
        String line = "";
        while (!line.equals("/stop")){
            try {
                line = console.readLine();
                streamOut.writeUTF(line);
                streamOut.flush();
            }
            catch (IOException ioe){
                System.out.println("IOE error: " + ioe.getMessage());
            }
        }

    }

    public void start() throws IOException
    {  console   = new DataInputStream(System.in);
        streamOut = new DataOutputStream(socket.getOutputStream());
    }

    public void stop() throws IOException {
        try{
            if(console != null) console.close();
            if(streamOut != null) streamOut.close();
            if(socket != null) socket.close();
        }
        catch (IOException ioe){
            System.out.println("Error, while closing " + ioe.getMessage());
        }
    }

    public static void main(String[] args) {
        {  ChatClient client = null;
            if (args.length != 2)
                System.out.println("Usage: java ChatClient host port");
            else
                client = new ChatClient(args[0], Integer.parseInt(args[1]));
        }
        }
    }

