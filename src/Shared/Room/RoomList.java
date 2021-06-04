package Shared.Room;

import java.util.ArrayList;

/**
 * Represents a list of rooms (physical rooms) in the system.
 *
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class RoomList {
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
    public RoomList() {
        rooms = new ArrayList<>();
        roomsCreated = 0;
    }

    /**
     * One-argument constructor. Loads the ArrayList of Room class objects.
     *
     * @param rooms An arrayList containing the rooms.
     */
    public RoomList(ArrayList<Room> rooms) {
        this.rooms = rooms;
        roomsCreated = rooms.size();
    }

    public void addAllRooms(ArrayList<Room> newRoomList) {
        this.rooms = newRoomList;
    }

    /**
     * Add a room into the list.
     *
     * @param room A Room object containing all the messages.
     */
    public void addRoom(Room room) {
        for (Room r :
                rooms) {
            if (r.getRoomID() == room.getRoomID())
                return;
        }
        rooms.add(room);
    }

    /**
     * Remove the room from the list by its ID.
     *
     * @param roomID An integer storing the ID of the room to be removed.
     * @return True - room has been removed; False - room hasn't been removed.
     */
    public boolean removeRoom(int roomID) {
        for (int i = 0; i < rooms.size(); i++) {
            if (roomID == rooms.get(i).getRoomID()) {
                rooms.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Change the information of a specific room.
     *
     * @param roomID          An integer storing the ID of the room to be changed.
     * @param roomCode        A string storing the actual number of the room to be changed.
     * @param buildingAddress A string storing the address of the room to be changed.
     * @param numberOfSeats   An integer storing the seats quantity of the room to be changed.
     * @param floor           An integer storing which floor does room locate.
     */
    public void modifyRoom(int roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor) {
        for (int i = 0; i < rooms.size(); i++) {
            if (roomID == rooms.get(i).getRoomID()) {
                rooms.get(i).modifyRoom(roomCode, buildingAddress, numberOfSeats, floor);
                break;
            }
        }


    }

    /**
     * Change the information of a specific room.
     *
     * @param room            A Room object used to find the room.
     * @param roomCode        A string storing the actual number of the room to be changed.
     * @param buildingAddress A string storing the address of the room to be changed.
     * @param numberOfSeats   An integer storing the seats quantity of the room to be changed.
     * @param floor           An integer storing which floor does room locate.
     */
    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor) {
        room.modifyRoom(roomCode, buildingAddress, numberOfSeats, floor);
    }


    /**
     * Gets the number of how many rooms created.
     *
     * @return An integer storing the quantity.
     */
    public static int getRoomsCreated() {
        return roomsCreated;
    }

    /**
     * Gets rooms by any key words.
     *
     * @param keyword A string storing the key words.
     * @return An arraylist containing the matched rooms.
     */
    public ArrayList<Room> getRoomsByAnything(String keyword) {

        ArrayList<Room> selectedRooms = new ArrayList<>();

        for (int i = 0; i < rooms.size(); i++) {
            Room current = rooms.get(i);
            if
            (
                    String.valueOf(current.getRoomID()).toLowerCase().contains(keyword.toLowerCase()) ||
                            current.getBuildingAddress().toLowerCase().contains(keyword.toLowerCase()) ||
                            String.valueOf(current.getFloor()).toLowerCase().contains(keyword.toLowerCase()) ||
                            current.getRoomNumber().toLowerCase().contains(keyword.toLowerCase()) ||
                            String.valueOf(current.getNumberOfSeats()).toLowerCase().contains(keyword.toLowerCase())
            ) {
                selectedRooms.add(current);
            } else {
                for (int j = 0; j < current.getEquipment().size(); j++) {
                    String currentEquipment = current.getEquipment().get(j);

                    if (currentEquipment.toLowerCase().contains(keyword.toLowerCase())) {
                        selectedRooms.add(current);
                        break;
                    }
                }
            }

        }

        return selectedRooms;
    }

    /**
     * Gets all the rooms.
     *
     * @return An arraylist containing all the rooms.
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Gets a room by its ID.
     *
     * @param roomID An integer storing the ID of room to be searched.
     * @return A Room object containing all the info of matched room. Null - no matched found.
     */
    public Room getRoomByID(int roomID) {
        for (Room r :
                rooms) {
            if (r.getRoomID() == roomID)
                return r;
        }
        return null;
    }
}
