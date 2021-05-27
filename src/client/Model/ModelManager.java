package client.Model;

import Shared.Employee.Employee;
import Shared.Employee.EmployeeList;
import Shared.Event.Event;
import Shared.Event.EventList;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Messages.MessageRoomList;
import Shared.Room.Room;
import Shared.Room.RoomList;
import client.RmiClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


//TODO Add database updating

public class ModelManager implements Model
{

    private EmployeeList employeeList;
    private Event event;
    private EventList eventList;
    private RoomList roomList;
    private MessageRoomList messageRoomList;
    private ArrayList<Integer> idT;
    private ArrayList<Employee> employeesT;
    private Model model;

    private Employee loggedEmployee;

    private RmiClient api;

    public ModelManager(RmiClient client)
    {
        this.api = client;

        this.event = new Event();
        this.eventList = new EventList();
        this.roomList = new RoomList();
        this.employeeList = new EmployeeList();
        this.messageRoomList = new MessageRoomList();
        this.idT = new ArrayList<>();
        this.employeesT = new ArrayList<>();

        employeeList.setMessageRoomList(messageRoomList);
        employeeList.setEventList(eventList);
        messageRoomList.setEmployeeList(employeeList);


        //todo REMOVE THIS

        employeeList.addEmployee("Adam123", "Adam", "Halama", "CFO");
        employeeList.addEmployee("Klaudi123", "klaudi", "Var", "CBO");


        messageRoomList.addMessageRoom("Test room t1");
        messageRoomList.getMessageRoomByID(1).addUser(1);
        messageRoomList.getMessageRoomByID(1).addMessage(new Message(1, (System.currentTimeMillis()) , "Helooooo"));


        messageRoomList.addPrivateMessageRoom("Private message room t2", 1, 2);

        /*try
        {
            System.out.println(api.registerEmployee("admin", "admin", "Admin", "Admin", "Admin"));
        } catch (GeneralSecurityException | IOException | SQLException e)
        {
            e.printStackTrace();
        }*/

//        loggedClientID = 1;

        //loging works, but i dont want to go to the login view every time
       /* loggedEmployee = new Employee(7, "admin", "Admin", "Admin", "Admin");
        employeeList.addEmployee(loggedEmployee);*/

    }

    @Override
    public void login(String username, String password) throws SQLException, GeneralSecurityException, IOException
    {
         loggedEmployee = api.loginEmployee(username, password);

         employeeList.addEmployee(loggedEmployee);
    }



    @Override
    public int getLoggedClientID()
    {
        return loggedEmployee.getId();
    }

    @Override
    public void addMessageRoom(String name)
    {
        messageRoomList.addMessageRoom(name);
    }

    @Override
    public void addMessageRoom(String name, ArrayList<Integer> usersIDs)
    {
        messageRoomList.addMessageRoom(name, usersIDs);
    }

    @Override
    public void removeMessageRoom(int messageRoomID)
    {
        messageRoomList.removeMessageRoom(messageRoomID);
    }

    @Override
    public void removeMessageRoom(MessageRoom room)
    {
        messageRoomList.removeMessageRoom(room);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID)
    {
        return messageRoomList.getMessageRoomsByEmployeeID(employeeID);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword)
    {
        return messageRoomList.getMessageRoomsByAnything(keyword);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRooms()
    {
        return messageRoomList.getMessageRooms();
    }

    @Override
    public String getSenderAndBody(Message message)
    {
        if (message == null)
            return "";
        return employeeList.getEmployeeByID(message.getUserID()).getFullName() + ": " + message.getMessage();
    }

    @Override
    public ArrayList<String> getMessageRoomParticipantNames(MessageRoom messageRoom)
    {
        ArrayList<String> participants = new ArrayList<>();

        for (int userID:
             messageRoom.getUsersIDs())
        {
            participants.add(employeeList.getEmployeeByID(userID).getName());
        }

        return participants;
    }

    @Override
    public MessageRoom getMessageRoomByID(int id) throws SQLException, RemoteException
    {
        return api.getMessageRoomByID(id);
    }


    @Override
    public void addEmployee(Employee employee)
    {
        employeeList.addEmployee(employee);
        //TODO probably delete the whole method
    }

    @Override
    public void addEmployee(String username, String password, String name, String surname, String role) throws SQLException, GeneralSecurityException, IOException
    {
        employeeList.addEmployee(api.registerEmployee(username, password, name, surname, role));
    }

    @Override
    public void addEmployee(String username, String name, String surname, ArrayList<Integer> events, ArrayList<Integer> messageRooms,
                            String role, ArrayList<String> permissions)
    {
        employeeList.addEmployee(username, name, surname, events, messageRooms, role, permissions);
        // TODO add database write
    }

    @Override
    public void removeEmployee(int employeeID)
    {
        employeeList.removeEmployee(employeeID);
    }

    @Override
    public ArrayList<Employee> getEmployees()
    {
        return employeeList.getEmployees();
    }

    @Override
    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom)
    {
        return employeeList.getEmployeesByMessageRoom(messageRoom);
    }

    @Override
    public ArrayList<Employee> getEmployeesByEvent(int eventID)
    {
        return employeeList.getEmployeesByEvent(eventID);
    }

    @Override
    public ArrayList<Employee> getEmployeesByRole(String role)
    {
        return employeeList.getEmployeesByRole(role);
    }

    @Override
    public ArrayList<Employee> getEmployeesByText(String text)
    {
        return employeeList.getEmployeesByText(text);
    }

    @Override
    public ArrayList<Employee> getEmployeesByAnything(String keyword)
    {
        return employeeList.getEmployeesByAnything(keyword);
    }

    @Override
    public Employee getEmployeeByID(int ID)
    {
        return employeeList.getEmployeeByID(ID);
    }


    @Override
    public void addRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException
    {
        roomList.addRoom(api.createRoom(getLoggedClientID(), roomNumber, buildingAddress, numberOfSeats, floor));
    }

    @Override
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment)
    {
        roomList.addRoom(roomCode, buildingAddress, numberOfSeats, floor, equipment);
        //TODO add adding rooms with equipment
    }

    @Override
    public void removeRoom(int roomID) {
        roomList.removeRoom(roomID);
    }

    @Override
    public void removeRoom(Room room) {
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
    public int getRoomsCreated() {
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
    public void addEquipment(Room room, String addedEquipment) {
        room.addEquipment(addedEquipment);
    }

    @Override
    public void setBuildingAddress(Room room, String buildingAddress) {
        room.setBuildingAddress(buildingAddress);
    }

    @Override
    public void setEquipment(Room room, ArrayList<String> equipment) {
        room.setEquipment(equipment);
    }

    @Override
    public void setFloor(Room room, int floor) {
        room.setFloor(floor);
    }

    @Override
    public void setNumberOfSeats(Room room, int numberOfSeats) {
        room.setNumberOfSeats(numberOfSeats);
    }

    @Override
    public void setRoomNumber(Room room, String roomNumber) {
        room.setRoomNumber(roomNumber);
    }

    @Override
    public void setTitle(String title) {
        event.setTitle(title);
    }

    @Override
    public void setTimeS(int year, int month, int day, int hour, int minute) {
        event.setTimeS(year, month, day, hour, minute);
    }

    @Override
    public void setTimeE(int hour, int minute) {
        event.setTimeE(hour, minute);
    }

    @Override
    public void setStartTime(int h, int m) {
        event.setStartTime(h, m);
    }

    @Override
    public void setEndTime(int h, int m) {
        event.setEndTime(h, m);
    }

    @Override
    public void setTime(int hS, int mS, int hE, int mE) {
        event.setTime(hS, mS, hE, mE);
    }

    @Override
    public void setDate(int y, int m, int d) {
        event.setDate(y, m, d);
    }

    @Override
    public void addParticipants(int id) {
        event.addParticipants(id);
    }

    @Override
    public void removeParticipants(int id) {
        event.removeParticipants(id);
    }

    @Override
    public void setParticipants(ArrayList<Integer> employees) {
        event.setParticipants(employees);
    }

    @Override
    public ArrayList<Integer> getParticipants() {
        return event.getParticipants();
    }

    @Override
    public int getCreatorID() {
        return event.getCreatorID();
    }

    @Override
    public long getTimestamp() {
        return event.getTimestamp();
    }

    @Override
    public void setDescription(String des) {
        event.setDescription(des);
    }

    @Override
    public void setOnline(boolean isOnline) {
        event.setOnline(isOnline);
    }

    @Override
    public void setRoom(int room) {
        event.setRoom(room);
    }

    @Override
    public void setPlatform(String platform) {
        event.setPlatform(platform);
    }

    @Override
    public void setDateString(String dateString) {
        event.setDateString(dateString);
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
    public LocalDate getDateString() {
        return event.getDateString();
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
    public ArrayList<Event> getEventByCreateTime(String date) {
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
    public Event getEvent(Event e) {
        return eventList.getEvent(e);
    }

    @Override
    public Event getEventByID(int id) {
        return eventList.getEventByID(id);
    }

    @Override
    public void remove(int index) {
        eventList.remove(index);
    }

    @Override
    public void removeByEvent(Event e) {
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
    public ArrayList<Event> getEventsPhysical() {
        return eventList.getEventsPhysical();
    }

    @Override
    public ArrayList<Event> getEventsDiscord() {
        return eventList.getEventsDiscord();
    }

    @Override
    public ArrayList<Event> getEventsZoom() {
        return eventList.getEventsZoom();
    }

    @Override
    public ArrayList<Event> getEventTeams() {
        return eventList.getEventTeams();
    }

    @Override
    public int getSize() {
        return eventList.getSize();
    }

    @Override
    public ArrayList<Integer> getParticipantsIDT() {
        return idT;
    }

    @Override
    public ArrayList<Employee> getParticipantsT() {
        return employeesT;
    }

    @Override
    public void addIDT(int id) {
        idT.add(id);
        addEmployeeT(id);
    }

    @Override
    public void addEmployeeT(int id) {
        employeesT.add(employeeList.getEmployeeByID(id));
    }

    @Override
    public void removeEmployeeT(int id) {
        for (int i = 0; i < employeesT.size(); i++){
            if (employeesT.get(i).getId() == id){
                employeesT.remove(i);
            }
        }
    }

    @Override
    public void clearTemporary() {
        employeesT.clear();
        idT.clear();
    }

}
