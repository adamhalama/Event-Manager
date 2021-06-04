package client.Model;

import Shared.ClientListener;
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
import org.junit.Ignore;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents one physical room.
 *
 * @author Group 6 - 2Y ICT A21
 * @version 1.0 - May 2021
 * @since 1.0
 */
public class ModelManager implements Model {

    /**
     * An EmployeeList object storing all the employees.
     */
    private EmployeeList employeeList;
    /**
     * An Event object storing the event.
     */
    private Event event;
    /**
     * An EventList object storing all the events.
     */
    private EventList eventList;
    /**
     * A RoomList object storing all the rooms.
     */
    private RoomList roomList;
    /**
     * A MessageRoomList object storing all the message rooms.
     */
    private MessageRoomList messageRoomList;
    /**
     * The temporary arraylist for storing employeeIDs.
     */
    private ArrayList<Integer> idT;
    /**
     * The temporary arraylist for storing employees.
     */
    private ArrayList<Employee> employeesT;
    private Model model;

    private Employee loggedEmployee;

    private RmiClient api;
    private PropertyChangeSupport property;

    /**
     * Constructor initial the model manager.
     *
     * @param client The client used to connect to server.
     */
    public ModelManager(RmiClient client) {
        this.api = client;

        this.eventList = new EventList();
        this.roomList = new RoomList();
        this.employeeList = new EmployeeList();
        this.messageRoomList = new MessageRoomList();
        this.idT = new ArrayList<>();
        this.employeesT = new ArrayList<>();
        property = new PropertyChangeSupport(this);

        employeeList.setMessageRoomList(messageRoomList);
        employeeList.setEventList(eventList);
        messageRoomList.setEmployeeList(employeeList);


        //made for development and testing and testing
        /*try
        {
//            addEmployee("admin", "admin", "Admin", "Admin" , "test admin");


            this.login("admin", "admin");
        } catch (SQLException | GeneralSecurityException | IOException throwables)
        {
            throwables.printStackTrace();
        }*/


    }


    /**
     * Add a listener into the class with its name.
     *
     * @param propertyName The name of listener.
     * @param listener     The listener object.
     */
    @Override
    public void addListener(String propertyName, PropertyChangeListener listener) {
        property.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a listener into the class with its name.
     *
     * @param propertyName The name of listener.
     * @param listener     The listener object.
     */
    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener) {
        property.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Login into the system through username and password.
     *
     * @param username A string storing username user entered.
     * @param password A string storing password user entered.
     * @throws SQLException             Throw exceptions when connecting to database.
     * @throws GeneralSecurityException Throw exceptions when connecting to server.
     * @throws IOException              Throw exceptions when connecting to server.
     */
    @Override
    public void login(String username, String password) throws SQLException, GeneralSecurityException, IOException {
        loggedEmployee = api.employeeLogin(username, password);

        employeeList.addEmployee(loggedEmployee);
    }

    /**
     * Log out from the system.
     */
    @Override
    public void logOut() {
        loggedEmployee = null;
    }


    /**
     * Gets the logged account's ID.
     *
     * @return An integer storing the logged account's ID.
     */
    @Override
    public int getLoggedEmployeeID() {
        if (loggedEmployee == null)
            return 0;
        return loggedEmployee.getId();
    }

    /**
     * Gets the logged account's info.
     *
     * @return An Employee object storing messages of logged account.
     */
    @Override
    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }

    @Ignore
    @Override
    public void addMessageRoom(String name) {
        messageRoomList.addMessageRoom(name);
    }

    /**
     * Gets a private message room created by employee ID.
     *
     * @param employeeID2 An integer storing employee ID.
     * @return A message room object get by employee ID.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public MessageRoom messageRoomCreatePrivate(int employeeID2) throws SQLException, RemoteException {
        return api.messageRoomCreatePrivate(getLoggedEmployeeID(), employeeID2);
    }

    @Ignore
    @Override
    public void addMessageRoom(String name, ArrayList<Integer> usersIDs) {
        messageRoomList.addMessageRoom(name, usersIDs);
    }

    /**
     * Sets name of message room searched by message room ID.
     *
     * @param messageRoomID An integer storing message room ID.
     * @param name          A string storing name of the message room.
     * @return A MessageRoom object storing the change.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public MessageRoom messageRoomSetName(int messageRoomID, String name) throws SQLException, RemoteException {
        return api.messageRoomSetName(getLoggedEmployeeID(), messageRoomID, name);
    }

    /**
     * Add a message room into message room list.
     *
     * @param messageRoom A MessageRoom object storing info of message room.
     */
    @Override
    public void messageRoomLocalSet(MessageRoom messageRoom) {
        ArrayList<MessageRoom> messageRooms = messageRoomList.getMessageRooms();
        for (MessageRoom messageRoomItem : messageRooms) {
            if (messageRoomItem.getId() == messageRoom.getId()) {
                messageRoomItem = messageRoom;
                break;
            }
        }
        messageRoomList.addMessageRoom(messageRoom);
    }

    /**
     * Remove a message room by its ID.
     *
     * @param messageRoomID An integer storing message room ID.
     */
    @Override
    public void messageRoomLocalRemove(int messageRoomID) {
        messageRoomList.removeMessageRoom(messageRoomID);
    }

    /**
     * Gets messages by message room ID
     *
     * @param messageRoomID An integer storing message room ID.
     * @param offset        The offset of messages, that will be returned.
     * @return An arraylist containing matched messages.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Message> messagesGet(int messageRoomID, int offset) throws RemoteException {
        return api.messagesGet(getLoggedEmployeeID(), messageRoomID, offset);
    }

    /**
     * Send message to the server.
     *
     * @param messageRoomID An integer storing message room ID.
     * @param message       A string storing message to be sent.
     * @return A Message object containing the sent message.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Message sendMessage(int messageRoomID, String message) throws SQLException, RemoteException {
        return api.messagePost(getLoggedEmployeeID(), messageRoomID, message);
    }

    /**
     * Add messages on local client.
     *
     * @param messageRoomID An integer storing message room ID.
     * @param message       A Message object containing the sent message.
     */
    @Override
    public void messageAddLocal(int messageRoomID, Message message) {
        System.out.println("test rmi listens");
        for (MessageRoom messageRoomItem : messageRoomList.getMessageRooms()) {
            if (messageRoomItem.getId() == messageRoomID) {
                messageRoomItem.addMessage(message);
                property.firePropertyChange("message", null, message);
                break;
            }
        }
    }

    @Ignore
    @Override
    public void removeMessageRoomFromEmployee(int messageRoomID) {
        messageRoomList.removeMessageRoom(messageRoomID);
    }

    @Ignore
    @Override
    public void removeMessageRoomFromEmployee(MessageRoom room) {
        messageRoomList.removeMessageRoom(room);
    }

    @Ignore
    @Override
    public ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID) {
        return messageRoomList.getMessageRoomsByEmployeeID(employeeID);
    }

    @Ignore
    @Override
    public ArrayList<MessageRoom> messageRoomGetPrivate() throws RemoteException {
        ArrayList<MessageRoom> messageRooms = api.messageRoomGetPrivate(getLoggedEmployeeID());
        messageRoomList.addRoomList(messageRooms);
        return messageRooms;
    }

    /**
     * Gets all the message rooms.
     *
     * @return An arraylist storing the message rooms.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<MessageRoom> messageRoomGetAll() throws RemoteException {
        ArrayList<MessageRoom> messageRooms = api.messageRoomGetAll(getLoggedEmployeeID());
        messageRoomList.addRoomList(messageRooms);
        return messageRooms;
    }

    /**
     * Gets message rooms by any key words.
     *
     * @param keyword A string storing the key words.
     * @return An arraylist containing all the matched message rooms.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword) throws RemoteException {
        messageRoomGetAll();
        return messageRoomList.getMessageRoomsByAnything(keyword);
    }

    @Ignore
    @Override
    public ArrayList<MessageRoom> getMessageRooms() {
        return messageRoomList.getMessageRooms();
    }

    /**
     * Gets message sender and content.
     *
     * @param message A string storing message used to be searched.
     * @return A string containing matched employee; "" - the message used is empty.
     */
    @Override
    public String getSenderAndBody(Message message) {
        if (message == null)
            return "";
        return employeeList.getEmployeeByID(message.getUserID()).getFullName() + ": " + message.getMessage();
    }

    /**
     * Gets the employees and name of the message room.
     *
     * @param employees       An array containing all the employee IDs.
     * @param messageRoomName A string containing the message room's name.
     * @return A MessageRoom object containing the employees and name of the message room.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public MessageRoom messageRoomCreateGroup(int[] employees, String messageRoomName) throws SQLException, RemoteException {
        return api.messageRoomCreateGroup(getLoggedEmployeeID(), employees, messageRoomName);
    }

    /**
     * Join a message room.
     *
     * @param messageRoomID An integer storing the message room ID to be joined.
     */
    @Override
    public void messageRoomFollow(int messageRoomID) {
        api.messageRoomFollow(messageRoomID);
    }

    /**
     * Quit a message room.
     *
     * @param messageRoomID An integer storing the message room ID to be exited.
     */
    @Override
    public void messageRoomUnfollow(int messageRoomID) {
        api.messageRoomUnfollow(messageRoomID);
    }

    /**
     * Gets participants' names by a message room.
     *
     * @param messageRoom A MessageRoom object used to be searched.
     * @return An arraylist storing the names.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<String> getMessageRoomParticipantNames(MessageRoom messageRoom) throws RemoteException {
        ArrayList<String> participants = new ArrayList<>();

        getEmployees();

        for (int userID :
                messageRoom.getUsersIDs()) {
            participants.add(employeeList.getEmployeeByID(userID).getName());
        }

        return participants;
    }

    /**
     * Gets message room by ID.
     *
     * @param id An integer storing ID.
     * @return A MessageRoom object containing matched room.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public MessageRoom getMessageRoomByID(int id) throws SQLException, RemoteException {
        MessageRoom messageRoom = messageRoomList.getMessageRoomByID(id);
        if (messageRoom != null) {
            return messageRoom;
        }
        MessageRoom newMessageRoom = api.getMessageRoomByID(id);
        messageRoomList.addMessageRoom(newMessageRoom);
        return newMessageRoom;
    }


    @Ignore
    @Override
    public void addEmployee(Employee employee) {
        employeeList.addEmployee(employee);
    }

    /**
     * Add an employee into the list.
     *
     * @param username A string storing username.
     * @param password A string storing password.
     * @param name     A string storing name.
     * @param surname  A string storing surname.
     * @param role     A string storing role.
     * @throws SQLException             Throw exceptions when connecting to database.
     * @throws GeneralSecurityException GeneralSecurityException
     * @throws IOException              Throw exceptions when connecting to server.
     */
    @Override
    public void addEmployee(String username, String password, String name, String surname, String role) throws SQLException, GeneralSecurityException, IOException {
        employeeList.addEmployee(api.employeeRegister(username, password, name, surname, role));
    }

    /**
     * Add an employee into the list.
     *
     * @param username    A string storing username.
     * @param password    A string storing password.
     * @param name        A string storing name.
     * @param surname     A string storing surname.
     * @param role        A string storing role.
     * @param permissions An arraylist containing all the permissions.
     * @throws SQLException             Throw exceptions when connecting to database.
     * @throws GeneralSecurityException GeneralSecurityException
     * @throws IOException              Throw exceptions when connecting to server.
     */
    @Override
    public void addEmployee(String username, String password, String name, String surname,
                            String role, ArrayList<String> permissions) throws SQLException, GeneralSecurityException, IOException {
        Employee employee = api.employeeRegister(username, password, name, surname, role);
        employeeList.addEmployee(employee);
        api.setPermissions(getLoggedEmployeeID(), employee.getId(), permissions.toArray(new String[0]));
        employee.setPermissions(permissions);
    }

    /**
     * Remove a permission for an employee.
     *
     * @param employeeID2 An integer storing employee ID.
     * @param permission  A string storing the permission to be removed.
     * @return An Employee containing employee and removed permission.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee removePermission(int employeeID2, String permission) throws SQLException, RemoteException {
        return api.removePermission(getLoggedEmployeeID(), employeeID2, permission);
    }

    /**
     * Sets permissions for an employee.
     *
     * @param employeeID2 An integer storing employee ID.
     * @param permissions An arraylist containing the permissions to be set.
     * @return An Employee containing employee and permissions.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee setPermissions(int employeeID2, String[] permissions) throws SQLException, RemoteException {
        return api.setPermissions(getLoggedEmployeeID(), employeeID2, permissions);
    }


    /**
     * Remove an employee by its ID.
     *
     * @param employeeID An integer storing the employee ID.
     * @return An Employee storing the employee info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee removeEmployee(int employeeID) throws SQLException, RemoteException {
        employeeList.removeEmployee(employeeID);
        return api.employeeDelete(getLoggedEmployeeID(), employeeID);
    }

    /**
     * Load all the employees. If there're new employees, add into the list.
     *
     * @param employee An Employee storing the employee info.
     */
    @Override
    public void updateLocalEmployee(Employee employee) {
        ArrayList<Employee> employees = employeeList.getEmployees();
        for (Employee employeeItem : employees) {
            if (employeeItem.getId() == employee.getId()) {
                employeeItem = employee;
                break;
            }
        }
        employeeList.addEmployee(employee);
    }

    /**
     * Remove an employee by its ID at local client.
     *
     * @param employeeID An integer storing the employee ID.
     */
    @Override
    public void removeLocalEmployee(int employeeID) {
        employeeList.removeEmployee(employeeID);
    }

    /**
     * Restore an employee by its ID.
     *
     * @param employeeID An integer storing the employee ID.
     * @return An Employee storing all the info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee employeeRestore(int employeeID) throws SQLException, RemoteException {
        if (!employeeList.restoreEmployee(employeeID)) {
            getEmployeeByID(employeeID); // getting the emp from the server, if he is not in the list
            employeeList.restoreEmployee(employeeID);
        }
        return api.employeeRestore(getLoggedEmployeeID(), employeeID);
    }

    /**
     * Gets all the employees.
     *
     * @return An arraylist containing all the employees.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Employee> getEmployees() throws RemoteException {
        employeeList.addAllEmployees(api.getAllEmployees());
        return employeeList.getEmployees();
    }

    /**
     * Gets employees by IDs.
     *
     * @param employeesIDs An arraylist storing employee IDs.
     * @return An arraylist containing matched employees.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) throws RemoteException {
        return api.employeeGetByIDs(employeesIDs);
    }

    @Ignore
    @Override
    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom) {
        return employeeList.getEmployeesByMessageRoom(messageRoom);
    }

    /**
     * Gets employees by eventID.
     *
     * @param eventID An integer storing event's iD.
     * @return An arraylist containing matched employees.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Employee> getEmployeesByEvent(int eventID) throws RemoteException {
        getEmployees();
        return employeeList.getEmployeesByEvent(eventID);
    }


    @Ignore
    @Override
    public ArrayList<Employee> getEmployeesByRole(String role) {
        return employeeList.getEmployeesByRole(role);
    }

    @Ignore
    @Override
    public ArrayList<Employee> getEmployeesByText(String text) {
        return employeeList.getEmployeesByText(text);
    }

    /**
     * Gets employees by any key words.
     *
     * @param keyword A string storing the key words.
     * @return An arraylist containing the matched employees.
     */
    @Override
    public ArrayList<Employee> getEmployeesByAnything(String keyword) {
        return employeeList.getEmployeesByAnything(keyword);
    }

    /**
     * Gets employee by employeeID.
     *
     * @param id An integer storing the ID of employee want to search.
     * @return An Employee object as matched employee or null means no matched employee found.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee getEmployeeByID(int id) throws SQLException, RemoteException {
        Employee employee = employeeList.getEmployeeByID(id);
        if (employee != null) {
            return employee;
        }
        Employee newEmployee = api.getEmployeeByID(id);
        employeeList.addEmployee(newEmployee);
        return newEmployee;
    }

    /**
     * Sets the name of the employee.
     *
     * @param employeeID2 An integer storing the employee ID.
     * @param name        A string containing the name.
     * @return An Employee object storing info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee employeeSetName(int employeeID2, String name) throws SQLException, RemoteException {
        return api.employeeSetName(getLoggedEmployeeID(), employeeID2, name);
    }

    /**
     * Sets the surname of the employee.
     *
     * @param employeeID2 An integer storing the employee ID.
     * @param surname     A string containing the surname.
     * @return An Employee object storing info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee employeeSetSurname(int employeeID2, String surname) throws SQLException, RemoteException {
        return api.employeeSetSurname(getLoggedEmployeeID(), employeeID2, surname);
    }

    /**
     * Sets the password of the employee.
     *
     * @param employeeID2 An integer storing the employee ID.
     * @param password    A string storing password for change.
     * @return An Employee object storing info.
     * @throws GeneralSecurityException Throw exceptions when connecting to server.
     * @throws SQLException             Throw exceptions when connecting to database.
     * @throws IOException              Throw exceptions when connecting to server.
     * @throws RemoteException          Throw exceptions when connecting to server.
     */
    @Override
    public Employee employeeSetPassword(int employeeID2, String password) throws GeneralSecurityException, SQLException, IOException, RemoteException {
        return api.employeeSetPassword(getLoggedEmployeeID(), employeeID2, password);
    }

    /**
     * Sets the username of the employee.
     *
     * @param employeeID2 An integer storing the employee ID.
     * @param username    A string containing the username.
     * @return An Employee object storing info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee employeeSetUsername(int employeeID2, String username) throws SQLException, RemoteException {
        return api.employeeSetUsername(getLoggedEmployeeID(), employeeID2, username);
    }

    /**
     * Sets the role of the employee.
     *
     * @param employeeID2 An integer storing the employee ID.
     * @param role        A string containing the role.
     * @return An Employee object storing info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Employee employeeSetRole(int employeeID2, String role) throws SQLException, RemoteException {
        return api.employeeSetRole(getLoggedEmployeeID(), employeeID2, role);
    }

    /**
     * Removes the employee from an event.
     *
     * @param employeeID An integer containing the Employee's ID
     * @param eventID    An integer containing the event ID.
     */
    @Ignore
    @Override
    public void removeEventFromEmployee(int employeeID, int eventID) throws SQLException, RemoteException {
        Employee employeeByID = getEmployeeByID(employeeID);
        ArrayList<Integer> eventIDs = employeeByID.getEvents();

        for (int i = 0; i < eventIDs.size(); i++) {
            if (eventIDs.get(i) == eventID) {
                employeeByID.removeEvent(i);
                return;
            }
        }

    }

    /**
     * Removes the employee from a message room.
     *
     * @param employeeID    An integer containing the Employee's ID
     * @param messageRoomID An integer containing the message room's ID.
     */
    @Ignore
    @Override
    public void removeMessageRoomFromEmployee(int employeeID, int messageRoomID) throws SQLException, RemoteException {
        Employee employeeByID = getEmployeeByID(employeeID);
        ArrayList<Integer> messageRoomIDs = employeeByID.getMessageRooms();

        for (int i = 0; i < messageRoomIDs.size(); i++) {
            if (messageRoomIDs.get(i) == messageRoomID) {
                employeeByID.removeMessageRoom(i);
                return;
            }
        }

    }

    /**
     * Add a room into the list.
     *
     * @param roomNumber      A string storing the physical number of a room.
     * @param buildingAddress A string storing address of room.
     * @param numberOfSeats   An integer storing the number of seats in the room.
     * @param floor           An integer storing which floor does room locate.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public void addRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException {
        Room room = api.createRoom(getLoggedEmployeeID(), roomNumber, buildingAddress, numberOfSeats, floor);
        roomList.addRoom(room);
    }

    @Ignore
    @Override
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment) throws SQLException, RemoteException {
        Room room = api.createRoom(getLoggedEmployeeID(), roomCode, buildingAddress, numberOfSeats, floor);
        roomList.addRoom(room);
        api.roomEquipmentSet(getLoggedEmployeeID(), room.getRoomID(), equipment.toArray(new String[0]));
    }


    /**
     * Remove the room from the list by its ID.
     *
     * @param roomID An integer storing the ID of the room to be removed.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public void removeRoom(int roomID) throws RemoteException {
        roomList.removeRoom(roomID);
        api.roomDeleteByID(getLoggedEmployeeID(), roomID);
    }

    @Ignore
    @Override
    public void removeRoom(Room room) throws RemoteException {
        int roomID = room.getRoomID();
        roomList.removeRoom(roomID);
        api.roomDeleteByID(getLoggedEmployeeID(), roomID);
    }

    /**
     * Load the rooms from local client side, if the room not in the list then add.
     *
     * @param room A Room object containing all the info.
     */
    @Override
    public void updateLocalRoom(Room room) {
        ArrayList<Room> rooms = roomList.getRooms();
        for (Room roomItem : rooms) {
            if (roomItem.getRoomID() == room.getRoomID()) {
                roomItem = room;
                break;
            }
        }
        roomList.addRoom(room);
    }

    /**
     * Remove a room from local client side.
     *
     * @param roomID An integer storing the ID.
     */
    @Override
    public void removeLocalRoom(int roomID) {
        roomList.removeRoom(roomID);
    }

    /**
     * Modify a room by searching its ID.
     *
     * @param roomID          An integer storing room ID.
     * @param roomCode        A string storing the physical number of a room.
     * @param buildingAddress A string storing address of room.
     * @param numberOfSeats   An integer storing the number of seats in the room.
     * @param floor           An integer storing which floor does room locate.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public void modifyRoom(int roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException {
        roomList.modifyRoom(roomID, roomCode, buildingAddress, numberOfSeats, floor);
        api.roomSetFloor(getLoggedEmployeeID(), roomID, floor);
        api.roomSetNumberOfSeats(getLoggedEmployeeID(), roomID, numberOfSeats);
        api.roomSetRoomNumber(getLoggedEmployeeID(), roomID, roomCode);
        api.roomSetBuildingAddress(getLoggedEmployeeID(), roomID, buildingAddress);
    }

    @Ignore
    @Override
    public boolean roomEquipmentAdd(int roomID, String equipment) throws RemoteException, SQLException {
        getRoomByID(roomID).addEquipment(equipment);
        return api.roomEquipmentAdd(getLoggedEmployeeID(), roomID, equipment);
    }

    @Ignore
    @Override
    public boolean roomEquipmentRemove(int roomID, String equipment) throws RemoteException, SQLException {
        getRoomByID(roomID).removeEquipment(equipment);
        return api.roomEquipmentRemove(getLoggedEmployeeID(), roomID, equipment);
    }

    /**
     * Set facilities in the room.
     *
     * @param roomID    An integer storing room ID.
     * @param equipment An array containing the equipment need to be add.
     * @return A boolean for whether the equipment has been added. True - added; False - failed.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public boolean roomEquipmentSet(int roomID, String[] equipment) throws SQLException, RemoteException {
        return api.roomEquipmentSet(getLoggedEmployeeID(), roomID, equipment);
    }

    /**
     * Set facilities in the room at local.
     *
     * @param roomID    An integer storing room ID.
     * @param equipment An array containing the equipment need to be add.
     * @return A boolean for whether the equipment has been added. True - added; False - failed.
     */
    @Override
    public boolean roomEquipmentLocalSet(int roomID, String[] equipment) {
        for (Room roomItem : roomList.getRooms()) {
            if (roomItem.getRoomID() == roomID) {
                roomItem.setEquipment(new ArrayList<String>(
                        Arrays.asList(equipment)));
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the equipment of a room.
     *
     * @param roomID
     * @return Array of Strings, each representing one equipment in the room.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public String[] roomEquipmentGet(int roomID) throws RemoteException {
        return api.roomEquipmentGet(roomID);
    }

    @Ignore
    @Override
    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor) {
        roomList.modifyRoom(room, roomCode, buildingAddress, numberOfSeats, floor);
    }

    @Ignore
    @Override
    public int getRoomsCreated() {
        return RoomList.getRoomsCreated();
    }


    /**
     * Gets all the rooms in the list.
     *
     * @return An arraylist storing all the rooms.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Room> getRooms() throws RemoteException {
        roomList.addAllRooms(api.getAllRooms());
        return roomList.getRooms();
    }

    /**
     * Gets rooms by any key words.
     *
     * @param keyword A string storing the key words.
     * @return An arraylist containing the matched rooms.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Room> getRoomsByAnything(String keyword) throws RemoteException {
        getRooms();
        return roomList.getRoomsByAnything(keyword);
    }

    /**
     * Gets a room by its ID.
     *
     * @param id An integer storing the ID of room to be searched.
     * @return A Room object containing all the info of matched room. Null - no matched found.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Room getRoomByID(int id) throws SQLException, RemoteException {
        Room room = roomList.getRoomByID(id);
        if (room != null) {
            return room;
        }
        Room newRoom = api.getRoomByID(id);
        roomList.addRoom(newRoom);
        return newRoom;
    }


    //EVENT

    /**
     * Gets events by ID.
     *
     * @param id An integer storing the event ID.
     * @return An Event object containing all the info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventGetByID(int id) throws SQLException, RemoteException {
        Event event = eventList.getByID(id);
        if (event != null) {
            return event;
        }
        Event newEvent = api.eventGetByID(id);
        eventList.add(newEvent);
        return newEvent;
    }

    /**
     * Gets all the events.
     *
     * @return An arraylist containing all the added events.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Event> getEvents() throws RemoteException {
        ArrayList<Event> events = api.eventGetAll();
        eventList.addAll(events);
        return events;

    }


    /**
     * Create an event.
     *
     * @param roomID      An integer storing room ID.
     * @param timeStart   A long integer storing the UNIX timestamp of start time.
     * @param timeEnd     A long integer storing the UNIX timestamp of end time.
     * @param title       A string storing title of the event.
     * @param description A string containing the description of the event.
     * @param platform    A string containing the online platform of the event.
     * @param onlineLink  A string containing the online platform's link of the event.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventCreate(int roomID, long timeStart, long timeEnd,
                             String title, String description, String platform, String onlineLink) throws SQLException, RemoteException {
        return api.eventCreate(getLoggedEmployeeID(), roomID, timeStart, timeEnd, title, description, platform, onlineLink);
    }

    /**
     * Check the target physical room is available. True - the room is available; False - the room has been occupied.
     *
     * @param roomID    An integer storing the physical room ID.
     * @param startTime A long integer storing the UNIX timestamp of start time.
     * @param endTime   A long integer storing the UNIX timestamp of end time.
     * @return The result of checking.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public boolean isRoomAvailable(int roomID, long startTime, long endTime) throws RemoteException {
        getEvents();
        return eventList.isRoomAvailable(roomID, startTime, endTime);
    }


    /**
     * Remove an event by its ID.
     *
     * @param id An integer containing the event ID.
     * @return A boolean object for whether the event is removed and True is removed.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public boolean eventRemoveByID(int id) throws RemoteException, SQLException {
        eventList.removeByID(eventGetByID(id));
        return api.eventDeleteByID(getLoggedEmployeeID(), id);
    }

    /**
     * Sets title of the event.
     *
     * @param eventID An integer storing the ID.
     * @param title   A string storing the title.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetTitle(int eventID, String title) throws SQLException, RemoteException {
        return api.eventSetTitle(getLoggedEmployeeID(), eventID, title);
    }

    /**
     * Sets description of the event.
     *
     * @param eventID     An integer storing the ID.
     * @param description A string storing the description.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetDescription(int eventID, String description) throws SQLException, RemoteException {
        return api.eventSetDescription(getLoggedEmployeeID(), eventID, description);
    }

    /**
     * Sets online link of the event.
     *
     * @param eventID An integer storing the ID.
     * @param url     A string storing the link.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetOnlineURL(int eventID, String url) throws SQLException, RemoteException {
        return api.eventSetOnlineURL(getLoggedEmployeeID(), eventID, url);
    }

    /**
     * Sets the online platform.
     *
     * @param eventID  An integer storing the ID.
     * @param platform A string storing the platform.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetPlatform(int eventID, String platform) throws SQLException, RemoteException {
        return api.eventSetPlatform(getLoggedEmployeeID(), eventID, platform);
    }

    /**
     * Sets both start and end time for an event.
     *
     * @param eventID   An integer storing the ID.
     * @param startTime A long integer storing the UNIX timestamp of start time.
     * @param endTime   A long integer storing the UNIX timestamp of end time.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetTime(int eventID, long startTime, long endTime) throws SQLException, RemoteException {
        return api.eventSetTime(getLoggedEmployeeID(), eventID, startTime, endTime);
    }

    /**
     * Sets the physical room ID.
     *
     * @param eventID An integer storing the ID.
     * @param roomID  An integer storing physical room ID.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetRoom(int eventID, int roomID) throws SQLException, RemoteException {
        return api.eventSetRoom(getLoggedEmployeeID(), eventID, roomID);
    }

    /**
     * Sets all the participants in the event.
     *
     * @param eventID      An integer storing the ID.
     * @param participants An arraylist containing all the participants.
     * @return An event object containing all info.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public Event eventSetParticipants(int eventID, int[] participants) throws SQLException, RemoteException {
        return api.eventSetParticipants(getLoggedEmployeeID(), eventID, participants);
    }

    /**
     * Join into an event.
     *
     * @param employeeID1 An integer storing the employee ID.
     * @param eventID     An integer storing the ID.
     * @return A boolean object for whether the user joined the event and True is joined.
     * @throws SQLException    Throw exceptions when connecting to database.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public boolean eventJoin(int employeeID1, int eventID) throws SQLException, RemoteException {
        return api.eventJoin(employeeID1, eventID);
    }

    @Ignore
    @Override
    public boolean eventLeave(int employeeID1, int eventID) throws SQLException, RemoteException {
        //the set method is used
        return api.eventLeave(employeeID1, eventID);
    }

    /**
     * Load all the events on local client side and if the event not in the list then add it.
     *
     * @param event An Event object to be added.
     */
    @Override
    public void eventLocalSet(Event event) {
        ArrayList<Event> events = eventList.getAll();
        for (Event eventItem : events) {
            if (eventItem.getID() == event.getID()) {
                eventItem = event;
                break;
            }
        }
        eventList.add(event);
    }

    /**
     * Remove an event from local client side.
     *
     * @param messageRoomID An integer storing message room ID.
     */
    @Override
    public void eventLocalRemove(int messageRoomID) {
        eventList.removeByID(messageRoomID);
    }

    /**
     * Gets events by the date.
     *
     * @param date A string storing dates.
     * @return An arraylist containing all the matched events.
     */
    @Override
    public ArrayList<Event> eventGetByDate(String date) {
        return eventList.getByDate(date);
    }

    /**
     * Gets events by the text.
     *
     * @param text A string storing text.
     * @return An arraylist containing all the matched events.
     */
    @Override
    public ArrayList<Event> eventGetByText(String text) {
        return eventList.getByText(text);
    }

    @Ignore
    @Override
    public ArrayList<Integer> getParticipants() {
        return event.getParticipants();
    }

    @Ignore
    @Override
    public void setParticipants(ArrayList<Integer> employees) {
        event.setParticipants(employees);
    }

    /**
     * Gets events by the physical room.
     *
     * @param roomID An integer storing the roomID.
     * @return An arraylist containing all the matched events.
     * @throws RemoteException Throw exceptions when connecting to server.
     */
    @Override
    public ArrayList<Event> getEventsByRoom(int roomID) throws RemoteException {
        getEvents();
        return eventList.getEventsByRoom(roomID);
    }

}
