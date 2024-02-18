/**
 * Simple class to create debug messages for the user.
 *
 * @author Dawson
 * @version 0.3
 */
public class Message {
    //class variables
    private String msg = "";
    private int changes = 0;
    private boolean debug;

    //constructor for a Message
    public Message(boolean debug) {
        this.debug = debug;
    }

    //method to see if there is a message
    public boolean hasMessage() {
        return !msg.equals("");
    }

    //method to get the message
    public String getMessage() {
        return msg + "";
    }

    //method to set the message
    public void setMessage(String msg) {
        this.msg = msg;
        changes++;
    }

    //method to add to the message
    public void addMessage(String msg) {
        this.msg += ( (changes == 0) ? "" : "\n" ) + msg;
        changes++;
    }

    //method to get the number of changes (sets or additions)
    public int numChanges() {
        return changes;
    }

    //method to see if the program is in debugging mode
    public boolean debugging() {
        return debug;
    }

    //clearing message to refill it with a new one
    public void clear(boolean debug) {
        msg = "";
        changes = 0;
        this.debug = debug;
    }
}