package Shared.Room;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Room
{
    private final int roomID;
    private String roomNumber;
    private int floor;
    private String buildingAddress;

    private int numberOfSeats;
    private ArrayList<String> equipment;

    private final Timestamp creationDate;
    private Timestamp lastModifiedDate;

    public Room(int roomID, String roomNumber, String buildingAddress, int numberOfSeats, int floor)
    {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;

        this.numberOfSeats = numberOfSeats;
        equipment = new ArrayList<>();

        creationDate = new Timestamp(System.currentTimeMillis());
        lastModifiedDate = creationDate;
    }

    public void modifyRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor)
    {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;
        this.numberOfSeats = numberOfSeats;
    }

    public void removeEquipment(String removedEquipment)
    {
        equipment.remove(removedEquipment);
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public void addEquipment(String addedEquipment)
    {
        equipment.add(addedEquipment);
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public void setBuildingAddress(String buildingAddress)
    {
        this.buildingAddress = buildingAddress;
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public void setEquipment(ArrayList<String> equipment)
    {
        this.equipment = equipment;
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public void setFloor(int floor)
    {
        this.floor = floor;
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public void setRoomNumber(String roomNumber)
    {
        this.roomNumber = roomNumber;
        lastModifiedDate = new Timestamp(System.currentTimeMillis());
    }

    public int getRoomID()
    {
        return roomID;
    }

    public ArrayList<String> getEquipment()
    {
        return equipment;
    }

    public int getFloor()
    {
        return floor;
    }

    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    public String getBuildingAddress()
    {
        return buildingAddress;
    }

    public String getRoomNumber()
    {
        return roomNumber;
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    public Timestamp getLastModifiedDate()
    {
        return lastModifiedDate;
    }
}


