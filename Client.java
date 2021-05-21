import java.net.*;
import java.io.*;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Client extends Thread{

    private Socket s;

    private ObjectOutputStream outObj;
    //private ObjectInputStream inObj;

    private InputStreamReader inputS;
    private BufferedReader in;
    //private PrintWriter out;

    public void connectSocket(String host, int port) throws IOException{
        s = new Socket(host, port);
        outObj = new ObjectOutputStream(s.getOutputStream());
        //inObj = new ObjectInputStream(s.getInputStream());

        inputS = new InputStreamReader(s.getInputStream());
        in = new BufferedReader(inputS);

    }

    public void waitForMessage(){
        Thread t = new Thread(() -> {
            while(true){
                try{
                    System.out.println("Message: " + in.readLine());
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        System.out.println("Waiting for message from server");
        t.start();
    }

    public static String getMessageInput(){
        System.out.println("Input message: ");
        Scanner m = new Scanner(System.in);
        String message = m.nextLine();
        return message;
    }

    public static Date getDateInput() throws ParseException{
        Date time;
        Scanner m = new Scanner(System.in);
        System.out.println("Input date (dd-MM-yyyy HH:mm:ss): ");
        String d = m.nextLine();
        time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(d);
        return time;
    }

    public static void main(String[] args){
        try {
            Client client = new Client();
            client.connectSocket("localhost", 4500);

            client.waitForMessage();

            while(true){
                String text = getMessageInput();
                Date date = getDateInput();

                Message message = new Message(text, date);

                client.outObj.writeObject(message);
                client.outObj.flush();

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(ParseException e){
            System.out.println("Wrong date");
        }
    }



    public static void sendMessage(Socket s)throws IOException{

    }
}
