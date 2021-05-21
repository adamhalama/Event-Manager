package Shared.Room;

import java.util.ArrayList;

public class Room
{
    private final int roomID;
    private String roomNumber;
    private int floor;
    private String buildingAddress;

    private int numberOfSeats;
    private ArrayList<String> equipment;

    public Room(int roomID, String roomNumber, String buildingAddress, int numberOfSeats, int floor)
    {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;

        this.numberOfSeats = numberOfSeats;
        equipment = new ArrayList<>();
    }

    public Room(int roomID, String roomNumber, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;

        this.numberOfSeats = numberOfSeats;
        this.equipment = equipment;
    }

    public void modifyRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;
        this.numberOfSeats = numberOfSeats;
        this.equipment = equipment;
    }

    public void removeEquipment(String removedEquipment)
    {
        equipment.remove(removedEquipment);
    }

    public void addEquipment(String addedEquipment)
    {
        equipment.add(addedEquipment);
    }

    public void setBuildingAddress(String buildingAddress)
    {
        this.buildingAddress = buildingAddress;
    }

    public void setEquipment(ArrayList<String> equipment)
    {
        this.equipment = equipment;
    }

    public void setFloor(int floor)
    {
        this.floor = floor;
    }

    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    public void setRoomNumber(String roomNumber)
    {
        this.roomNumber = roomNumber;
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

    @Override public String toString()
    {
        return "{" + "roomID=" + roomID + ", roomNumber='" + roomNumber
            + '\'' + ", floor=" + floor + ", buildingAddress='"
            + buildingAddress + '\'' + ", numberOfSeats=" + numberOfSeats + '}';
    }
}


