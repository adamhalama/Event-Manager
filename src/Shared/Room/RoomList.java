package Shared.Room;

import java.util.ArrayList;

public class RoomList
{
    private ArrayList<Room> rooms;
    private static int numberOfRooms;

    public RoomList()
    {
        rooms = new ArrayList<>();
    }

    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        numberOfRooms++;
        String id = "R" + numberOfRooms;
        rooms.add(new Room(id, roomCode, buildingAddress, numberOfSeats, floor));
    }

    public void removeRoom(String roomID)
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            if(roomID.equals(rooms.get(i).getRoomID()))
            {
                rooms.remove(i);
                break;
            }
        }
    }

    public void removeRoom(Room room)
    {
        rooms.remove(room);
    }

    public void modifyRoom(String roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            if(roomID.equals(rooms.get(i).getRoomID()))
            {
                rooms.get(i).modifyRoom(roomCode, buildingAddress, numberOfSeats, floor);
                break;
            }
        }
    }

    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        room.modifyRoom(roomCode, buildingAddress, numberOfSeats, floor);
    }


}
