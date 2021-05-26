package Shared.Messages;

/**
 * Represents one message.
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class Message
{
    /**
     * An int representing an ID of the user that sent this message, used for system purposes, can also be displayed.
     */
    private int userID; // PK
    /**
     * An long representing an exact moment (millisecond precision) for time logging purposes.
     * Using an Unix-timestamp format.
     * Single variable representing year, month, day, hour, minute, second, millisecond
     */
    private long timestamp;
    /**
     * A string representing the text message body itself.
     */
    private String message;

    /**
     * Three-argument constructor. Creates a message with specified parameters.
     * @param userID An int containing the ID of the user that sent/is sending this message.
     * @param timestamp An long containing the time this message was sent.
     * @param message A string containing the message body.
     */
    public Message(int userID, long timestamp, String message)
    {
        this.userID = userID;
        this.timestamp = timestamp;
        this.message = message;
    }

    /**
     * Gets the ID of the user that send the message.
     * @return An int representing the userID.
     */
    public int getUserID()
    {
        return this.userID;
    }

    /**
     * Gets the exact moment when this message was sent.
     * @return A long representing the date and time.
     */
    public long getTimestamp()
    {
        return this.timestamp;
    }

    /**
     * Gets the message body.
     * @return A string representing the message body.
     */
    public String getMessage()
    {
        return this.message;
    }

    /**
     * Gets the all the message information available in one string.
     * @return A string representing all the object information.
     */
    @Override public String toString()
    {
        return "{" + "userID=" + userID + ", timestamp=" + timestamp + ", message='" + message + '\'' + '}';
    }
}