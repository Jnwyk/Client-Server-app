import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Message implements Serializable {

    private String message;
    private Date time;

    public String getMessage() {
        return message;
    }

    public Date getTime(){
        return time;
    }

    Message(String message, Date time){
        this.message = message;
        this.time = time;
    }

}