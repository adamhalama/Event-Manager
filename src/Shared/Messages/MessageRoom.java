package Shared.Messages;

import java.util.ArrayList;

public class MessageRoom {
  private String name; // Room name
  private ArrayList<Integer> usersIDs; // Message Room users PK
  private ArrayList<Message> messages; // Loaded messages list
  private boolean allMessagesLoaded = true; // Will be used to know if all messages were loaded to disable infinite scroll (I guess)

  public MessageRoom(String name, ArrayList<Integer> usersIDs, ArrayList<Message> messages) {
    this.name = name;
    this.usersIDs = usersIDs;
    this.messages = messages;
  }

  public void setName(String name) {
    this.name = name;
  }
  public void setUsersIDs(ArrayList<Integer> usersIDs) {
    this.usersIDs = usersIDs;
  }
  public void setMessages(ArrayList<Message> messages) {
    this.messages = messages;
  }
  public void addMessages(ArrayList<Message> messages) { // Adds messages at the start
    messages.addAll(this.messages);
    this.messages = messages;
  }
  public void addMessage(Message message) { // Adds new message at the end
    this.messages.add(message);
  }
  public void setAllMessagesLoaded(boolean state) {
    this.allMessagesLoaded = state;
  }

  public String getName() {
    return this.name;
  }
  public boolean getAllMessagesLoaded() {
    return this.allMessagesLoaded;
  }
  public ArrayList<Integer> getUsersIDs() {
    return this.usersIDs;
  }
  public ArrayList<Message> getMessages() {
    return this.messages;
  }
}
