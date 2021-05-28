package Shared.Room;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents one physical room.
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class Room implements Serializable
{
    /**
     * An integer representing an ID used for system purposes, can also be displayed.
     */
    private final int roomID;
    /**
     * Represents the number on a room / name of the room.
     */
    private String roomNumber;
    private int floor;
    private String buildingAddress;

    private int numberOfSeats;
    private ArrayList<String> equipment;


    /**
     * Four-argument constructor.
     * Creates a room with specified parameters, and with empty equipment ArrayList.
     * Made for use when creating rooms, without equipment.
     * @param roomID an Integer representing an ID of the room.
     * @param roomNumber a String representing the number on a room / name of the room.
     * @param buildingAddress a String representing the physical address of the building the room is located in.
     * @param numberOfSeats an Integer representing the number of seats in the room.
     * @param floor an Integer representing the physical floor in the building the room is located in.
     */
    public Room(int roomID, String roomNumber, String buildingAddress, int numberOfSeats, int floor)
    {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;

        this.numberOfSeats = numberOfSeats;
        equipment = new ArrayList<>();
    }

    /**
     * Five-argument constructor.
     * Creates a room with specified parameters, and with a specified equipment ArrayList.
     * Made for use when creating rooms, with equipment, or when loading rooms from the database, where the ArrayList is already defined, even when empty.
     * @param roomID an Integer containing an ID of the room.
     * @param roomNumber a String containing the number on a room / name of the room.
     * @param buildingAddress a String containing the physical address of the building the room is located in.
     * @param numberOfSeats an Integer containing the number of seats in the room.
     * @param floor an Integer containing the physical floor in the building the room is located in.
     * @param equipment an ArrayList of Strings defining the equipment in the room.
     */
    public Room(int roomID, String roomNumber, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;

        this.numberOfSeats = numberOfSeats;
        this.equipment = equipment;
    }

    /**
     * A method used to modify one specific room, based on the parameters.
     * @param roomNumber a String containing the number on a room / name of the room.
     * @param buildingAddress a String containing the physical address of the building the room is located in.
     * @param numberOfSeats an Integer containing the number of seats in the room.
     * @param floor an Integer containing the physical floor in the building the room is located in.
     * @param equipment an ArrayList of Strings containing the equipment in the room.
     */
    public void modifyRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.buildingAddress = buildingAddress;
        this.numberOfSeats = numberOfSeats;
        this.equipment = equipment;
    }

    /**
     * Removes specified equipment passed as the parameter.
     * @param removedEquipment A string containing the equipment to remove.
     */
    public void removeEquipment(String removedEquipment)
    {
        equipment.remove(removedEquipment);
    }

    /**
     * Adds a specified equipment passed as the parameter.
     * @param addedEquipment A string containing the equipment to add.
     */
    public void addEquipment(String addedEquipment)
    {
        equipment.add(addedEquipment);
    }

    /**
     * Sets the physical building address of the room.
     * @param buildingAddress A string containing the desired address.
     */
    public void setBuildingAddress(String buildingAddress)
    {
        this.buildingAddress = buildingAddress;
    }

    /**
     * Sets the equipment to a specific set, based on the parameter.
     * Used for creating, modifying and loading from the database.
     * @param equipment An ArrayList of strings containing the equipment.
     */
    public void setEquipment(ArrayList<String> equipment)
    {
        this.equipment = equipment;
    }

    /**
     * Sets the physical floor of the room.
     * @param floor An integer containing a number containing the floor.
     */
    public void setFloor(int floor)
    {
        this.floor = floor;
    }

    /**
     * Sets the number of seats in the room.
     * @param numberOfSeats An integer containing the number of seats in the room.
     */
    public void setNumberOfSeats(int numberOfSeats)
    {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Sets the door-number / name of the room.
     * @param roomNumber A string containing the room number.
     */
    public void setRoomNumber(String roomNumber)
    {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the room id.
     * @return An Integer representing a room system id.
     */
    public int getRoomID()
    {
        return roomID;
    }

    /**
     * Gets the equipment of a room.
     * @return ArrayList of Strings, each representing one equipment in the room.
     */
    public ArrayList<String> getEquipment()
    {
        return equipment;
    }

    /**
     * Gets the physical floor of the room in a building.
     * @return An int representing the floor the room is on.
     */
    public int getFloor()
    {
        return floor;
    }

    /**
     * Gets the number of seats in the room.
     * @return An int representing the number of the seats.
     */
    public int getNumberOfSeats()
    {
        return numberOfSeats;
    }

    /**
     * Gets the physical building address the room is located in.
     * @return A string representing the address.
     */
    public String getBuildingAddress()
    {
        return buildingAddress;
    }

    /**
     * Gets the room number.
     * @return A String representing the room number / room name.
     */
    public String getRoomNumber()
    {
        return roomNumber;
    }

    /**
     * toString method to show all the variables from the object.
     * @return A string including all the variables in the object.
     */
    @Override public String toString()
    {
        return "{" + "roomID=" + roomID + ", roomNumber='" + roomNumber
            + '\'' + ", floor=" + floor + ", buildingAddress='"
            + buildingAddress + '\'' + ", numberOfSeats=" + numberOfSeats + '}' + equipment.toString();
    }
}


