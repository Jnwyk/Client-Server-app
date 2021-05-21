import java.net.*;
import java.io.*;

public class Server{
    public static void main(String[] args){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(4500);
            ss.setReuseAddress(true);

            while(true){
                Socket s = ss.accept();
                ClientHandler client = new ClientHandler(s);
                Thread t = new Thread(client);
                t.start();
                System.out.println("Client connected");
            }
        }
        catch(IOException e){
            System.err.println(e);
        }
        finally {
            try{
                ss.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
