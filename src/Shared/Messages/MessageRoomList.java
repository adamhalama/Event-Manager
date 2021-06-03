package Shared.Messages;

import Shared.Employee.EmployeeList;

import java.util.ArrayList;

/**
 * Represents the message room list. All the message rooms will show here.
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */

public class MessageRoomList
{
    /**
     * The arrayList to store all the message rooms.
     */
    private ArrayList<MessageRoom> messageRooms;
    /**
     * The number for counting how many message rooms have been created, the initial amount is 0.
     */
    private static int messageRoomsCreated = 0;
    /**
     * The EmployeeList object containing all the participants.
     */
    private EmployeeList employeeList;

    /**
     * Zero-argument constructor.
     * Only initial the arrayList instance variable.
     */
    public MessageRoomList()
    {
        messageRooms = new ArrayList<>();
    }

    /**
     * Add a message room only with title into the list.
     * @param name A string for the title of the room.
     */
    public void addMessageRoom(String name)
    {
        messageRooms.add(new MessageRoom(messageRoomsCreated + 1, name));
        messageRoomsCreated++;
    }

    /**
     * Add message room with name and participants.
     * @param name A string for the title of the room.
     * @param usersIDs An arraylist for storing the participants' ids.
     */
    public void addMessageRoom(String name, ArrayList<Integer> usersIDs)
    {
        messageRooms.add(new MessageRoom(messageRoomsCreated + 1, name, usersIDs));
        messageRoomsCreated++;
    }


    /**
     * Remove the chat room from the list by its id.
     * @param messageRoomID An integer containing the id of the chat room which user want to remove.
     */
    public void removeMessageRoom(int messageRoomID)
    {
        for (MessageRoom room :
                messageRooms)
        {
            if (room.getId() == messageRoomID)
            {
                messageRooms.remove(room);
                return;
            }
        }
    }

    /**
     * Remove the chat room from the list.
     * @param room A room object containing all the information.
     */
    public void removeMessageRoom(MessageRoom room)
    {
        messageRooms.remove(room);
    }

    /**
     * Use an arraylist to get all the chat rooms by a employeeID.
     * @param employeeID An integer storing the employeeID which used to search chat rooms.
     * @return An arraylist containing all the matched chat rooms. Null is means no chat rooms matched the requirement.
     */
    public ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID)
    {
        ArrayList<MessageRoom> picked = new ArrayList<>();

        for (MessageRoom room :
                messageRooms)
        {
            for (int id :
                    room.getUsersIDs())
            {
                if (id == employeeID)
                {
                    picked.add(room);
                    break;
                }
            }
        }
        return picked;
    }

    /**
     * Searches for any occurrence of the keyword in the: ID, name, user name and surname, messages
     *
     * @param keyword A string on which the search is based
     * @return An arraylist of MessageRoom objects that contain the keyword
     */
    public ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword)
    {
        ArrayList<MessageRoom> picked = new ArrayList<>();

        for1:
        for (MessageRoom messageRoom :
                messageRooms)
        {
            if (String.valueOf(messageRoom.getId()).contains(keyword) ||
                    messageRoom.getName().toLowerCase().contains(keyword.toLowerCase()))
            {
                picked.add(messageRoom);
                continue for1;
            }

            for (int userID :
                    messageRoom.getUsersIDs())
            {
                if (employeeList.getEmployeeByID(userID).getFullName().toLowerCase().contains(keyword))
                {
                    picked.add(messageRoom);
                    continue for1;
                }
            }

            for (Message message :
                    messageRoom.getMessages())
            {
                if (message.getMessage().toLowerCase().contains(keyword.toLowerCase()))
                {
                    picked.add(messageRoom);
                    continue for1;
                }
            }
        }

        return picked;
    }

    /**
     * Gets all the message rooms.
     * @return An arraylist storing all the message rooms in the list.
     */
    public ArrayList<MessageRoom> getMessageRooms()
    {
        return messageRooms;
    }

    /**
     * Gets a single message room by its id.
     * @param id The integer stands for the id of the room user wants to search.
     * @return The MessageRoom object matched the requirement, Null - no matched message room
     */
    public MessageRoom getMessageRoomByID(int id)
    {
        for (MessageRoom room :
                messageRooms)
        {
            if (room.getId() == id)
                return room;
        }

        return null;
    }

    /**
     * Set a new list of participants.
     * @param employeeList An EmployeeList object storing all the participants.
     */
    public void setEmployeeList(EmployeeList employeeList)
    {
        this.employeeList = employeeList;
    }

    /**
     * Add a list of message rooms.
     * @param messageRooms An arraylist containing the message rooms.
     */
    public void addRoomList(ArrayList<MessageRoom> messageRooms)
    {
        this.messageRooms = messageRooms;

    }
    
    /**
     * Add a message room.
     * @param messageRoom A MessageRoom object containing all the information of the message room.
     */
    public void addMessageRoom(MessageRoom messageRoom)
    {
        for (MessageRoom room :
                this.messageRooms)
        {
            if (room.getId() == messageRoom.getId())
                return;
        }
        messageRooms.add(messageRoom);
    }
}
