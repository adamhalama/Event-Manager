package Shared.Room;

import java.util.ArrayList;

public class RoomList
{
    private ArrayList<Room> rooms;
    private static int roomsCreated;

    public RoomList()
    {
        rooms = new ArrayList<>();
        roomsCreated = 1;
    }

    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        rooms.add(new Room(roomsCreated, roomCode, buildingAddress, numberOfSeats, floor));
        roomsCreated++;
    }

    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        rooms.add(new Room(roomsCreated, roomCode, buildingAddress, numberOfSeats, floor, equipment));
        roomsCreated++;
    }

    public void removeRoom(int roomID)
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            if (roomID == rooms.get(i).getRoomID())
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

    public void modifyRoom(String roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        for (int i = 0; i < rooms.size(); i++)
        {
            if (roomID.equals(rooms.get(i).getRoomID()))
            {
                rooms.get(i).modifyRoom(roomCode, buildingAddress, numberOfSeats, floor, equipment);
                break;
            }
        }


    }

    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        room.modifyRoom(roomCode, buildingAddress, numberOfSeats, floor, equipment);
    }

    public static int getRoomsCreated()
    {
        return roomsCreated;
    }

    public ArrayList<Room> getRoomsByAnything(String keyword)
    {

        ArrayList<Room> selectedRooms = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++)
        {
            Room current = rooms.get(i);
            if
            (
                    String.valueOf(current.getRoomID()).toLowerCase().contains(keyword.toLowerCase()) ||
                            current.getBuildingAddress().toLowerCase().contains(keyword.toLowerCase()) ||
                            String.valueOf(current.getFloor()).toLowerCase().contains(keyword.toLowerCase()) ||
                            current.getRoomNumber().toLowerCase().contains(keyword.toLowerCase()) ||
                            String.valueOf(current.getNumberOfSeats()).toLowerCase().contains(keyword.toLowerCase())
            )
            {
                selectedRooms.add(current);
            } else
            {
                for (int j = 0; j < current.getEquipment().size(); j++)
                {
                    String currentEquipment = current.getEquipment().get(j);

                    if (currentEquipment.toLowerCase().contains(keyword.toLowerCase()))
                    {
                        selectedRooms.add(current);
                        break;
                    }
                }
            }

        }

        return selectedRooms;
    }

    public ArrayList<Room> getRooms()
    {
        return rooms;
    }

    public Room getRoomByID(int roomID)
    {
        for (Room r:
             rooms)
        {
            if (r.getRoomID() == roomID)
                return r;
        }
        return null;
    }
}
