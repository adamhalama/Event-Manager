package client.Model;

import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Model {
    //only for event, room, employee now
    //add other methods for other classes later


    //messageRoom class

    void login(String username, String password) throws SQLException, GeneralSecurityException, IOException;

    void logOut();

    int getLoggedEmployeeID();

    Employee getLoggedEmployee();

    void addMessageRoom(String name);

    MessageRoom messageRoomCreatePrivate(int employeeID2) throws SQLException, RemoteException;

    void addMessageRoom(String name, ArrayList<Integer> usersIDs);

    Message sendMessage(int messageRoomID, String message) throws SQLException, RemoteException;

    MessageRoom messageRoomSetName(int messageRoomID, String name) throws SQLException, RemoteException;

    ArrayList<Message> messagesGet(int messageRoomID, int offset) throws RemoteException;

    void removeMessageRoomFromEmployee(int messageRoomID);

    void removeMessageRoomFromEmployee(MessageRoom room);

    ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID);

    ArrayList<MessageRoom> messageRoomGetPrivate() throws RemoteException;

    ArrayList<MessageRoom> messageRoomGetAll() throws RemoteException;

    ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword) throws RemoteException;

    ArrayList<MessageRoom> getMessageRooms();

    String getSenderAndBody(Message message);

    ArrayList<String> getMessageRoomParticipantNames(MessageRoom messageRoom) throws RemoteException;

    MessageRoom getMessageRoomByID(int id) throws SQLException, RemoteException;


    //part 3 class employee

    void addEmployee(Employee employee);

    void addEmployee(String username, String password, String name, String surname, String role) throws SQLException, GeneralSecurityException, IOException;

    void addEmployee(String username, String password, String name, String surname, String role, ArrayList<String> permissions) throws SQLException, GeneralSecurityException, IOException;

    Employee removePermission(int employeeID2, String permission) throws SQLException, RemoteException;

    Employee setPermissions(int employeeID2, String[] permissions) throws SQLException, RemoteException;

    Employee removeEmployee(int employeeID) throws SQLException, RemoteException;

    void updateLocalEmployee(Employee employee);

    void removeLocalEmployee(int employeeID);

    Employee employeeRestore(int employeeID) throws SQLException, RemoteException;

    ArrayList<Employee> getEmployees() throws RemoteException;

    ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) throws RemoteException;

    ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom);

    ArrayList<Employee> getEmployeesByEvent(int eventID);

    ArrayList<Employee> getEmployeesByRole(String role);

    ArrayList<Employee> getEmployeesByText(String text);

    ArrayList<Employee> getEmployeesByAnything(String keyword);

    Employee getEmployeeByID(int ID) throws SQLException, RemoteException;

    Employee employeeSetName(int employeeID2, String name) throws SQLException, RemoteException;

    Employee employeeSetSurname(int employeeID2, String surname) throws SQLException, RemoteException;

    Employee employeeSetPassword(int employeeID2, String password) throws GeneralSecurityException, SQLException, IOException, RemoteException;

    Employee employeeSetUsername(int employeeID2, String username) throws SQLException, RemoteException;

    Employee employeeSetRole(int employeeID2, String role) throws SQLException, RemoteException;


    //part4 class room

    void removeEventFromEmployee(int employeeID, int eventID) throws SQLException, RemoteException;

    void removeMessageRoomFromEmployee(int employeeID, int messageRoomID)throws SQLException, RemoteException;

    void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException;

    void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment) throws SQLException, RemoteException;

    void removeRoom(int roomID) throws RemoteException;

    void removeRoom(Room room) throws RemoteException;

    void modifyRoom(int roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException;

    boolean roomEquipmentAdd(int roomID, String equipment) throws RemoteException, SQLException;

    boolean roomEquipmentRemove(int roomID, String equipment) throws RemoteException, SQLException;

    boolean roomEquipmentSet(int roomID, String[] equipment) throws SQLException, RemoteException;

    String[] roomEquipmentGet(int roomID) throws RemoteException;

    void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor);

    int getRoomsCreated();

    ArrayList<Room> getRooms() throws RemoteException;

    ArrayList<Room> getRoomsByAnything(String keyword) throws RemoteException;

    Room getRoomByID(int roomID) throws SQLException, RemoteException;


    //part1 class event
    void setTitle(String title);

    ArrayList<Event> eventGetByDate(String date);

    ArrayList<Event> eventGetByText(String text);

    void setOnline(boolean isOnline);

    void setRoom(int room);

    void setPlatform(String platform);

    void setParticipants(ArrayList<Integer> employees);

    Event eventGetByID(int eventID) throws SQLException, RemoteException;


    Event eventSetTitle(int eventID, String title) throws SQLException, RemoteException;

    Event eventSetDescription(int eventID, String description) throws SQLException, RemoteException;

    Event eventSetOnlineURL(int eventID, String url) throws SQLException, RemoteException;

    Event eventSetPlatform(int eventID, String platform) throws SQLException, RemoteException;

    Event eventSetOnlineState(int eventID, boolean isOnline) throws SQLException, RemoteException;

    Event eventSetTime(int eventID, long startTime, long endTime) throws SQLException, RemoteException;

    boolean eventJoin(int eventID) throws SQLException, RemoteException;

    boolean eventLeave(int eventID) throws SQLException, RemoteException;

    ArrayList<Integer> getParticipants();

    int getEvent_id();

    String getTitle();

    String getPlatform();

    int getRoomID();

    boolean isOnline();

    //part2 class eventList

    void add(Event event) throws IllegalArgumentException;

    ArrayList<Event> getEvents() throws RemoteException;

    ArrayList<Event> getEventByAnything(String s, String d);

    ArrayList<Event> getEventExceptDate(String s);

    ArrayList<Event> getEventOnlyDate(String date);


    ArrayList<Event> getEventsByRoom(int roomID);


    Event getEventByID(int id);


    Event eventCreate(int roomID, long timeStart, long timeEnd, String title, String description, String platform, String onlineLink) throws SQLException, RemoteException;

    boolean removeByEventID(int id) throws RemoteException, SQLException;


    int getSize();

    //extra part for choose participants
    ArrayList<Integer> getParticipantsIDT(); //temporary store the ids

    ArrayList<Employee> getParticipantsT(); //temporary store the employees

    void addIDT(int id);

    void addEmployeeT(int id);
}
