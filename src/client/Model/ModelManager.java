package client.Model;

import Shared.Event.Event;
import Shared.Event.EventList;
import Shared.Room.Room;
import Shared.Room.RoomList;

import java.util.ArrayList;


//TODO Add database updating

public class ModelManager implements Model
{
    private Event event;
    private EventList eventList;
    private RoomList roomList;

    public ModelManager()
    {
        this.event = new Event();
        this.eventList = new EventList();
        this.roomList = new RoomList();
    }

    @Override
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        roomList.addRoom(roomCode, buildingAddress, numberOfSeats, floor);
    }

    @Override
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        roomList.addRoom(roomCode, buildingAddress, numberOfSeats, floor, equipment);
    }

    @Override
    public void removeRoom(int roomID)
    {
        roomList.removeRoom(roomID);
    }

    @Override
    public void removeRoom(Room room)
    {
        roomList.removeRoom(room);
    }

    @Override
    public void modifyRoom(String roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        roomList.modifyRoom(roomID, roomCode, buildingAddress, numberOfSeats, floor, equipment);
    }

    @Override
    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        roomList.modifyRoom(room, roomCode, buildingAddress, numberOfSeats, floor, equipment);
    }

    @Override
    public int getRoomsCreated()
    {
        return RoomList.getRoomsCreated();
    }

    @Override
    public ArrayList<Room> getRooms()
    {
        return roomList.getRooms();
    }

    @Override
    public ArrayList<Room> getRoomsByAnything(String keyword)
    {
        return roomList.getRoomsByAnything(keyword);
    }

    @Override
    public Room getRoomByID(int roomID)
    {
        return roomList.getRoomByID(roomID);
    }

    @Override
    public void removeEquipment(Room room, String removedEquipment)
    {
        room.removeEquipment(removedEquipment);
    }

    @Override
    public void addEquipment(Room room, String addedEquipment)
    {
        room.addEquipment(addedEquipment);
    }

    @Override
    public void setBuildingAddress(Room room, String buildingAddress)
    {
        room.setBuildingAddress(buildingAddress);
    }

    @Override
    public void setEquipment(Room room, ArrayList<String> equipment)
    {
        room.setEquipment(equipment);
    }

    @Override
    public void setFloor(Room room, int floor)
    {
        room.setFloor(floor);
    }

    @Override
    public void setNumberOfSeats(Room room, int numberOfSeats)
    {
        room.setNumberOfSeats(numberOfSeats);
    }

    @Override
    public void setRoomNumber(Room room, String roomNumber)
    {
        room.setRoomNumber(roomNumber);
    }

    @Override
    public void setTitle(String title)
    {
        event.setTitle(title);
    }

    @Override
    public void setTimeS(int year, int month, int day, int hour, int minute)
    {
        event.setTimeS(year, month, day, hour, minute);
    }

    @Override
    public void setTimeE(int year, int month, int day, int hour, int minute)
    {
        event.setTimeE(year, month, day, hour, minute);
    }

    @Override
    public void setDescription(String des)
    {
        event.setDescription(des);
    }

    @Override
    public void setOnline(boolean isOnline)
    {
        event.setOnline(isOnline);
    }

    @Override
    public void setRoom(int room)
    {
        event.setRoom(room);
    }

    @Override
    public void setPlatform(String platform) {
        event.setPlatform(platform);
    }

    @Override
    public void setOnlineLink(String link, String platform) {
        event.setOnlineLink(link, platform);
    }

    @Override
    public int getEvent_id() {
        return event.getEvent_id();
    }

    @Override
    public String getTitle() {
        return event.getTitle();
    }

    @Override
    public String getDescription() {
        return event.getDescription();
    }

    @Override
    public String getPlatform() {
        return event.getPlatform();
    }

    @Override
    public String getOnlineLink() {
        return event.getOnlineLink();
    }

    @Override
    public String getTime_create() {
        return event.getTime_create();
    }

    @Override
    public String getTime_start() {
        return event.getTime_start();
    }

    @Override
    public String getTime_end() {
        return event.getTime_end();
    }

    @Override
    public int getRoomID() {
        return event.getRoomID();
    }

    @Override
    public boolean isOnline() {
        return event.isOnline();
    }

    @Override
    public void add(Event event) throws IllegalArgumentException {
        eventList.add(event);
    }

    @Override
    public Event addEvent(Event e) {
        eventList.addEvent(e);
        return e;
    }

    @Override
    public ArrayList<Event> getEvents() {
        return eventList.getEvents();
    }

    @Override
    public ArrayList<Event> getEventByCreateTime(String date)
    {
        return eventList.getEventByCreateTime(date);
    }

    @Override
    public ArrayList<Event> getEventByStartTime(String date) {
        return eventList.getEventByStartTime(date);
    }

    @Override
    public ArrayList<Event> getEventByTitle(String title) {
        return eventList.getEventByTitle(title);
    }

    @Override
    public ArrayList<Event> getEventByAnything(String s, String d) {
        return eventList.getEventByAnything(s, d);
    }

    @Override
    public ArrayList<Event> getEventExceptDate(String s) {
        return eventList.getEventExceptDate(s);
    }

    @Override
    public ArrayList<Event> getEventOnlyDate(String date) {
        return eventList.getEventOnlyDate(date);
    }

    @Override
    public Event getEventByIndex(int index) {
        return eventList.getEventByIndex(index);
    }

    @Override
    public Event getEvent(Event e)
    {
        return eventList.getEvent(e);
    }

    @Override
    public void remove(int index)
    {
        eventList.remove(index);
    }

    @Override
    public void removeByEvent(Event e)
    {
        eventList.removeByEvent(e);
    }

    @Override
    public void removeByEventID(int id) {
        eventList.removeByEventID(id);
    }

    @Override
    public void removeAllEvents() {
        eventList.removeAll();
    }

    @Override
    public ArrayList<Event> getEventsOnline() {
        return eventList.getEventsOnline();
    }

    @Override
    public ArrayList<Event> getEventsPhysical()
    {
        return eventList.getEventsPhysical();
    }

    @Override
    public ArrayList<Event> getEventsDiscord()
    {
        return eventList.getEventsDiscord();
    }

    @Override
    public ArrayList<Event> getEventsZoom()
    {
        return eventList.getEventsZoom();
    }

    @Override
    public ArrayList<Event> getEventTeams()
    {
        return eventList.getEventTeams();
    }

    @Override
    public int getSize() {
        return eventList.getSize();
    }
}
