package Shared.Messages;

import Shared.Employee.EmployeeList;

import java.util.ArrayList;

public class MessageRoomList
{
    private ArrayList<MessageRoom> messageRooms;
    private static int messageRoomsCreated = 0;
    private EmployeeList employeeList;

    public MessageRoomList()
    {
        messageRooms = new ArrayList<>();
    }

    public void addMessageRoom(String name)
    {
        messageRooms.add(new MessageRoom(messageRoomsCreated + 1, name));
        messageRoomsCreated++;
    }

    public void addMessageRoom(String name, ArrayList<Integer> usersIDs)
    {
        messageRooms.add(new MessageRoom(messageRoomsCreated, name, usersIDs));
    }

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

    public void removeMessageRoom(MessageRoom room)
    {
        messageRooms.remove(room);
    }

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

    public ArrayList<MessageRoom> getMessageRooms()
    {
        return messageRooms;
    }

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

    public static int getMessageRoomsCreated()
    {
        return messageRoomsCreated;
    }

    public void setEmployeeList(EmployeeList employeeList)
    {
        this.employeeList = employeeList;
    }

}
