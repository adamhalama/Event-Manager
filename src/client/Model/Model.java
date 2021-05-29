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
import java.time.LocalDate;
import java.util.ArrayList;

public interface Model {
    //only for event, room, employee now
    //add other methods for other classes later


    //messageRoom class

    void login(String username, String password) throws SQLException, GeneralSecurityException, IOException;

    void logOut();

    int getLoggedClientID();

    Employee getLoggedEmployee();

    void addMessageRoom(String name);

    MessageRoom createPrivateMessageRoom(int employeeID1, int employeeID2) throws SQLException, RemoteException;

    void addMessageRoom(String name, ArrayList<Integer> usersIDs);

    void removeMessageRoomFromEmployee(int messageRoomID);

    void removeMessageRoomFromEmployee(MessageRoom room);

    ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID);

    ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword);

    ArrayList<MessageRoom> getMessageRooms();

    String getSenderAndBody(Message message);

    ArrayList<String> getMessageRoomParticipantNames(MessageRoom messageRoom);

    MessageRoom getMessageRoomByID(int id) throws SQLException, RemoteException;


    //part 3 class employee

    void addEmployee(Employee employee);

    void addEmployee(String username, String password, String name, String surname, String role) throws SQLException, GeneralSecurityException, IOException;

    void addEmployee(String username, String password, String name, String surname, String role, ArrayList<String> permissions) throws SQLException, GeneralSecurityException, IOException;

    void removeEmployee(int employeeID);

    ArrayList<Employee> getEmployees();

    ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) throws RemoteException;

    ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom);

    ArrayList<Employee> getEmployeesByEvent(int eventID);

    ArrayList<Employee> getEmployeesByRole(String role);

    ArrayList<Employee> getEmployeesByText(String text);

    ArrayList<Employee> getEmployeesByAnything(String keyword);

    Employee getEmployeeByID(int ID) throws SQLException, RemoteException;

    Employee employeeSetName(int employeeID2, String name) throws SQLException, RemoteException;

    Employee employeeSetSurname(int employeeID2, String surname) throws SQLException, RemoteException;

    Employee employeeSetRole(int employeeID2, String role) throws SQLException, RemoteException;


    //part4 class room

    void removeEventFromEmployee(int employeeID, int eventID) throws SQLException, RemoteException;

    void removeMessageRoomFromEmployee(int employeeID, int messageRoomID)throws SQLException, RemoteException;

    void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException;

    void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment);

    void removeRoom(int roomID);

    void removeRoom(Room room);

    void modifyRoom(String roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment);

    void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment);

    int getRoomsCreated();

    ArrayList<Room> getRooms();

    ArrayList<Room> getRoomsByAnything(String keyword);

    Room getRoomByID(int roomID) throws SQLException, RemoteException;

    void removeEquipment(Room room, String removedEquipment);

    public void addEquipment(Room room, String addedEquipment);

    void setBuildingAddress(Room room, String buildingAddress);

    void setEquipment(Room room, ArrayList<String> equipment);

    void setFloor(Room room, int floor);

    void setNumberOfSeats(Room room, int numberOfSeats);

    void setRoomNumber(Room room, String roomNumber);


    //part1 class event
    void setTitle(String title);

    void setOnline(boolean isOnline);

    void setRoom(int room);

    void setPlatform(String platform);

    void setParticipants(ArrayList<Integer> employees);

    ArrayList<Integer> getParticipants();

    int getEvent_id();

    String getTitle();

    String getPlatform();

    int getRoomID();

    boolean isOnline();

    long getStartTime();

    long getEndTime();

    long getCreateTime();

    //part2 class eventList

    void add(Event event) throws IllegalArgumentException;

    ArrayList<Event> getEvents();

    ArrayList<Event> getEventByAnything(String s, String d);

    ArrayList<Event> getEventExceptDate(String s);

    ArrayList<Event> getEventOnlyDate(String date);

    Event getEventByIndex(int index);

    Event getEventByID(int id);

    void remove(int index);

    void removeByEventID(int id);

    void removeAllEvents();

    int getSize();

    //extra part for choose participants
    ArrayList<Integer> getParticipantsIDT(); //temporary store the ids

    ArrayList<Employee> getParticipantsT(); //temporary store the employees

    void addIDT(int id);

    void addEmployeeT(int id);

    void removeEmployeeT(int id);

    void clearTemporary();
}
