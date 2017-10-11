package JavaChat;

import javax.sound.midi.Soundbank;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Created by Aldres on 11.10.17.
 */
public class ChatServer implements Runnable {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream streamIn = null;
    private Thread thread = null;

    public ChatServer(int port) {
        try {
            System.out.println("Binding to port: " + port + " please wait...");
            server = new ServerSocket(port);
            System.out.println("Server started: " + server);
            start();
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void run(){
        while (thread!=null){
            try{
                System.out.println("Waiting for a client...");
                socket = server.accept();
                System.out.println("Client accepted " + socket);
                boolean isDone = false;
                while (!isDone){
                    try {
                        String line = streamIn.readUTF();
                        System.out.println(line);
                        isDone = line.equals("/stop");
                    }
                    catch (IOException e){
                        isDone = true;
                    }
                }
                close();
            }

            catch (IOException ioe){
                System.out.println("Error, while accepting: " + ioe.getMessage());
            }
        }
    }

    public void start()
    {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            thread.stop();
            thread = null;
    }
    }

    public void open() throws IOException
    {  streamIn = new DataInputStream(new
            BufferedInputStream(socket.getInputStream()));
    }

    public void close() throws IOException {
        if(socket!=null) socket.close();
        if(streamIn != null) streamIn.close();
    }

    public static void main(String[] args) {
        ChatServer server = null;
        if (args.length!=1){
            System.out.println("Usage: java ChatServer port");
        }
        else {
             server = new ChatServer(Integer.parseInt(args[0]));
        }
    }
}

