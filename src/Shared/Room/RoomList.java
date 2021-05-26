package Shared.Room;

import java.util.ArrayList;

/**
 * Represents a list of rooms (physical rooms) in the system.
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class RoomList
{
    /**
     * Represents the ArrayList of Room class objects.
     * Hold all of the rooms.
     * A class used for loading objects from the database.
     */
    private ArrayList<Room> rooms;
    private static int roomsCreated;

    /**
     * Zero-argument constructor. Creates the ArrayList of Room class objects.
     */
    public RoomList()
    {
        rooms = new ArrayList<>();
        roomsCreated = 0;
    }

    /**
     * One-argument constructor. Loads the ArrayList of Room class objects.
     * @param rooms An arrayList containing the rooms.
     */
    public RoomList(ArrayList<Room> rooms)
    {
        this.rooms = rooms;
        roomsCreated = rooms.size();
    }

    /**
     * Creates a new Room in the arrayList of rooms.
     * @param roomCode
     * @param buildingAddress
     * @param numberOfSeats
     * @param floor
     */
    //TODO javaDoc not finished, i want to do Lists after i do individual-object classes.
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        rooms.add(new Room(roomsCreated + 1, roomCode, buildingAddress, numberOfSeats, floor));
        roomsCreated++;
    }

    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        rooms.add(new Room(roomsCreated + 1, roomCode, buildingAddress, numberOfSeats, floor, equipment));
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
