package Shared.Messages;

import java.util.ArrayList;

/**
 * Represents one message room. Chat room or private message room between two people.
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class MessageRoom
{
    /**
     * Room ID, representing the system id of the message room.
     */
    private int id;
    /**
     * Name of the message room, mostly used in group chats.
     */
    private String name;
    /**
     * An arraylist that contains the users tha belong to this room, by storing their system ID's.
     */
    private ArrayList<Integer> usersIDs;
    /**
     * An arraylist that contains the messages sent in the message room.
     */
    private ArrayList<Message> messages;
    /**
     * Holds a boolean containing the information if the room is a group chat or a private chat.
     * true - Private chat - 2 people,
     * false - Group chat - unlimited number of people
     */
    private boolean isPrivate;
    /**
     * A boolean holding the information if all the messages are loaded from the database.
     * Used to know if all messages were loaded to disable infinite scroll
     */
    private boolean allMessagesLoaded = true;

    /**
     * Three-argument constructor. Used for private rooms.
     * The two IDs can't be the same.
     * @param id An integer containing the ID of the message room.
     * @param name a String containing the name of the chat.
     * @param firstParticipant an integer containing the ID of one of the users in the private chat.
     * @param secondParticipant an integer containing the ID of the second user in the private chat.
     */
    public MessageRoom(int id, String name, int firstParticipant, int secondParticipant)
    {
        if (firstParticipant == secondParticipant)
        {
            System.out.println("Two same users cant be in the same room");
            return;
        }
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = new ArrayList<>();

        usersIDs.add(firstParticipant);
        usersIDs.add(secondParticipant);
        isPrivate = true;
    }

    /**
     * Four-argument constructor. Used for private rooms.
     * The two IDs can't be the same.
     * Used when loading from the database
     * @param id An integer containing the ID of the message room.
     * @param name a String containing the name of the chat.
     * @param firstParticipant an integer containing the ID of one of the users in the private chat.
     * @param secondParticipant an integer containing the ID of the second user in the private chat.
     * @param messages a
     */
    public MessageRoom(int id, String name,int firstParticipant, int secondParticipant, ArrayList<Message> messages)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = messages;

        usersIDs.add(firstParticipant);
        usersIDs.add(secondParticipant);
        isPrivate = true;
    }

    public MessageRoom(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = new ArrayList<>();
        isPrivate = false;
    }
    public MessageRoom(int id, String name, ArrayList<Integer> usersIDs)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = usersIDs;
        this.messages = new ArrayList<>();
        this.isPrivate = false;
    }

    public MessageRoom(int id, String name, boolean isPrivate)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.isPrivate = isPrivate;
    }

    public MessageRoom(int id, String name, ArrayList<Integer> usersIDs, ArrayList<Message> messages, boolean isPrivate)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = usersIDs;
        this.messages = messages;
        this.isPrivate = isPrivate;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setUsersIDs(ArrayList<Integer> usersIDs)
    {
        this.usersIDs = usersIDs;
    }

    public void addUser(int ID)
    {
        usersIDs.add(ID);
    }

    public void setMessages(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

    public void addMessages(ArrayList<Message> messages)
    { // Adds messages at the start
        messages.addAll(this.messages);
        this.messages = messages;
    }

    public void addMessage(Message message)
    { // Adds new message at the end
        this.messages.add(message);
    }

    public void setAllMessagesLoaded(boolean state)
    {
        this.allMessagesLoaded = state;
    }

    public String getName()
    {
        return this.name;
    }

    public int getId()
    {
        return id;
    }

    public boolean getAllMessagesLoaded()
    {
        return this.allMessagesLoaded;
    }

    public ArrayList<Integer> getUsersIDs()
    {
        return this.usersIDs;
    }

    public ArrayList<Message> getMessages()
    {
        return this.messages;
    }

    public Message getLastMessage()
    {
        if (messages == null || messages.size() == 0)
            return null;
        else
            return messages.get(messages.size() - 1);
    }

    public boolean isPrivate()
    {
        return isPrivate;
    }

    @Override public String toString()
    {
        return "{" + "id='" + id + '\'' + ", name=" + name + ", isPrivate=" + isPrivate +'}';
    }
}
