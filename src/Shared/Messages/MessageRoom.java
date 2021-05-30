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
     * @param messages an ArrayList of Message objects, containing all the messages in the existing conversation.
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

    /**
     * Three-argument constructor. Used for group message rooms.
     * Used when creating new room.
     * @param id An integer containing the ID of the message room.
     * @param name a String containing the name of the chat.
     * @param usersIDs an ArrayList of integers, containing userIDs
     */
    public MessageRoom(int id, String name, ArrayList<Integer> usersIDs)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = usersIDs;
        this.messages = new ArrayList<>();
        this.isPrivate = false;
    }

    /**
     * Four-argument constructor. Used for group message rooms.
     * Used when loading from the database
     * @param id An integer containing the ID of the message room.
     * @param name a String containing the name of the chat.
     * @param usersIDs an ArrayList of integers, containing userIDs
     * @param messages an ArrayList of Message objects, containing all the messages in the existing conversation.
     */
    public MessageRoom(int id, String name, ArrayList<Integer> usersIDs, ArrayList<Message> messages)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = usersIDs;
        this.messages = messages;
        this.isPrivate = false;
    }

    //todo change the unit test for a new constructor
    // for unit testing
    public MessageRoom(int id, String name, ArrayList<Integer> usersIDs, ArrayList<Message> messages, boolean isPrivate)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = usersIDs;
        this.messages = messages;
        this.isPrivate = false;
    }

    //TODO maybe delete
    // not sure if we need this
    public MessageRoom(int id, String name)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = new ArrayList<>();
        isPrivate = false;
    }

    //TODO maybe delete
    // not sure if we need this
    public MessageRoom(int id, String name, boolean isPrivate)
    {
        this.id = id;
        this.name = name;
        this.usersIDs = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.isPrivate = isPrivate;
    }


    /**
     * Sets the name.
     * @param name A string containing the new name of the group chat
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the user pool.
     * @param usersIDs An arraylist containing userIDs of the new user list.
     */
    public void setUsersIDs(ArrayList<Integer> usersIDs)
    {
        this.usersIDs = usersIDs;
    }

    /**
     * Adds a user to the groupChat based on the parameter.
     * @param ID an int containing the userID that is going to be added to the group chat.
     * @throws IllegalStateException if the method is used in private message room - "Cant add user to a private chat"
     */
    public void addUser(int ID)
    {
        if (!isPrivate)
            usersIDs.add(ID);
        else
            throw new IllegalStateException("Cant add user to a private chat");
    }

    /**
     * Sets the message list.
     * @param messages An arraylist containing messages that are going to replace the current message list.
     */
    public void setMessages(ArrayList<Message> messages)
    {
        this.messages = messages;
    }

    /**
     * Adds messages to the pool of messages.
     * Adds the messages at the beginning of the list.
     * @param messages Arraylist of messages containing the messages that are going to be added.
     */
    public void addMessages(ArrayList<Message> messages)
    { // Adds messages at the start
        messages.addAll(this.messages);
        this.messages = messages;
    }

    /**
     * Adds a single message at the end.
     * @param message A Message object containing the message,
     */
    public void addMessage(Message message)
    { // Adds new message at the end
        this.messages.add(message);
    }

    /**
     * Sets the load state of the message list.
     * @param state A boolean containing the state of the loading. true - all messages were loaded. false - some messages still loading.
     */
    public void setAllMessagesLoaded(boolean state)
    {
        this.allMessagesLoaded = state;
    }

    /**
     * Gets the name of the chat.
     * @return A string representing the name of the chat.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the system ID of the chat.
     * @return an int representing system ID of the chat.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the state of the message room loading.
     * @return A boolean containing the state. True - all loaded, False - still loading
     */
    public boolean getAllMessagesLoaded()
    {
        return this.allMessagesLoaded;
    }

    /**
     * Gets all the users.
     * @return An ArrayList of ints representing the users with system employee id's
     */
    public ArrayList<Integer> getUsersIDs()
    {
        return this.usersIDs;
    }

    /**
     * Gets all the messages that are in the chat room.
     * @return An arraylist of Message representing all the messages.
     */
    public ArrayList<Message> getMessages()
    {
        return this.messages;
    }

    /**
     * Gets the last message in the chat.
     * @return An Message object containing the last message in the chat.
     */
    public Message getLastMessage()
    {
        if (messages == null || messages.size() == 0)
            return null;
        else
            return messages.get(messages.size() - 1);
    }


    /**
     * Gets the private state.
     * @return A boolean representing if the chat is private - true, or its a group message room - false.
     */
    public boolean isPrivate()
    {
        return isPrivate;
    }

    /**
     * toString method returning the id, name and if its private.
     * @return A string containing the data.
     */
    @Override public String toString()
    {
        return "{" + "id='" + id + '\'' + ", name=" + name + ", isPrivate=" + isPrivate +'}';
    }
}
