import java.net.*;
import java.io.*;
import java.sql.Time;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler extends Thread{

    private Socket client;
    private PrintWriter out;
    private ObjectInputStream inObj;
    private Timer timer;

    ClientHandler(Socket client){
        this.client = client;
    }

    public void create() throws IOException{
        inObj = new ObjectInputStream(client.getInputStream());
        out = new PrintWriter(client.getOutputStream());
    }


    public void run() {
        try {
            timer = new Timer();


            create();

            while(true){
                Message message = (Message)inObj.readObject();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println("Message from client: " + message.getMessage());
                        out.println(message.getMessage());
                        out.flush();
                    }
                };
                timer.schedule(task, message.getTime());
            }
        } catch (SocketException a) {
            System.out.println("Client connection lost");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }


}
