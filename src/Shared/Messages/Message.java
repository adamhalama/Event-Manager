package Shared.Messages;

public class Message {
  private int userID; // PK
  private long timestamp; // When the message was posted | Unix timestamp
  private String message; // Message text

  public Message(int userID, long timestamp, String message) {
    this.userID = userID;
    this.timestamp = timestamp;
    this.message = message;
  }

  public int getUserID() {
    return this.userID;
  }
  public long getTimestamp() {
    return this.timestamp;
  }
  public String getMessage() {
    return this.message;
  }
}