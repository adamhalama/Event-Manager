package Shared.Messages;

import java.util.ArrayList;

public class MessageRoom
{
    private int id; // Room id
    private String name; // Room name
    private ArrayList<Integer> usersIDs; // Message Room users PK
    private ArrayList<Message> messages; // Loaded messages list
    private boolean isPrivate; //if true the message room can have only 2 members
    private boolean allMessagesLoaded = true; // Will be used to know if all messages were loaded to disable infinite scroll (I guess)

    public MessageRoom(int id, String name,int firstParticipant, int secondParticipant)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = new ArrayList<>();

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
        isPrivate = false;
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
        return "{" + "id='" + id + '\'' + ", name=" + name +'}';
    }
}
