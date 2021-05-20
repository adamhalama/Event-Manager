package client.Model;

import Shared.Event.Event;
import Shared.Room.Room;

import java.util.ArrayList;

public interface Model {
    //only for event now
    //add other methods for other classes later

    //part2 class room

    void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor);
    void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment);
    void removeRoom(int roomID);
    void removeRoom(Room room);
    void modifyRoom(String roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment);
    void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment);
    int getRoomsCreated();
    ArrayList<Room> getRooms();
    ArrayList<Room> getRoomsByAnything(String keyword);
    Room getRoomByID(int roomID);

    void removeEquipment(Room room, String removedEquipment);
    public void addEquipment(Room room, String addedEquipment);
    void setBuildingAddress(Room room, String buildingAddress);
    void setEquipment(Room room, ArrayList<String> equipment);
    void setFloor(Room room, int floor);
    void setNumberOfSeats(Room room, int numberOfSeats);
    void setRoomNumber(Room room, String roomNumber);


    //part1 class event
    void setTitle(String title);
    void setTimeS(int year, int month, int day, int hour, int minute);
    void setTimeE(int year, int month, int day, int hour, int minute);
    void setDescription(String des);
    void setOnline(boolean isOnline);
    void setRoom(int room);
    void setPlatform(String platform);
    void setOnlineLink(String link, String platform);
    int getEvent_id();
    String getTitle();
    String getDescription();
    String getPlatform();
    String getOnlineLink();
    String getTime_create();
    String getTime_start();
    String getTime_end();
    int getRoomID();
    boolean isOnline();
    //part2 class eventList
    void add(Event event) throws IllegalArgumentException;
    Event addEvent(Event event);
    ArrayList<Event> getEvents();
    ArrayList<Event> getEventByCreateTime(String date);
    ArrayList<Event> getEventByStartTime(String date);
    ArrayList<Event> getEventByTitle(String title);
    ArrayList<Event> getEventByAnything(String s, String d);
    ArrayList<Event> getEventExceptDate(String s);
    ArrayList<Event> getEventOnlyDate(String date);
    Event getEventByIndex(int index);
    Event getEvent(Event e);
    void remove(int index);
    void removeByEvent(Event e);
    void removeByEventID(int id);
    void removeAllEvents();
    ArrayList<Event> getEventsOnline();
    ArrayList<Event> getEventsPhysical();
    ArrayList<Event> getEventsDiscord();
    ArrayList<Event> getEventsZoom();
    ArrayList<Event> getEventTeams();
    int getSize();
}
