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
    private PropertyChangeSupport property;

    public ModelManager(RmiClient client)
    {
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


    @Override
    public void addListener(String propertyName, PropertyChangeListener listener)
    {
        property.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removeListener(String propertyName, PropertyChangeListener listener)
    {
        property.removePropertyChangeListener(propertyName, listener);
    }

    @Override
    public void login(String username, String password) throws SQLException, GeneralSecurityException, IOException
    {
        loggedEmployee = api.employeeLogin(username, password);

        employeeList.addEmployee(loggedEmployee);
    }

    @Override
    public void logOut()
    {
        loggedEmployee = null;
    }


    @Override
    public int getLoggedEmployeeID()
    {
        if (loggedEmployee == null)
            return 0;
        return loggedEmployee.getId();
    }

    @Override
    public Employee getLoggedEmployee()
    {
        return loggedEmployee;
    }

    @Ignore
    @Override
    public void addMessageRoom(String name)
    {
        messageRoomList.addMessageRoom(name);
    }

    @Override
    public MessageRoom messageRoomCreatePrivate(int employeeID2) throws SQLException, RemoteException
    {
        return api.messageRoomCreatePrivate(getLoggedEmployeeID(), employeeID2);
    }

    @Ignore
    @Override
    public void addMessageRoom(String name, ArrayList<Integer> usersIDs)
    {
        messageRoomList.addMessageRoom(name, usersIDs);
    }

    @Override
    public MessageRoom messageRoomSetName(int messageRoomID, String name) throws SQLException, RemoteException
    {
        return api.messageRoomSetName(getLoggedEmployeeID(), messageRoomID, name);
    }

    @Override
    public void messageRoomLocalSet(MessageRoom messageRoom)
    {
        ArrayList<MessageRoom> messageRooms = messageRoomList.getMessageRooms();
        for(MessageRoom messageRoomItem : messageRooms) {
            if(messageRoomItem.getId() == messageRoom.getId()) {
                messageRoomItem = messageRoom;
                break;
            }
        }
        messageRoomList.addMessageRoom(messageRoom);
    }

    @Override
    public void messageRoomLocalRemove(int messageRoomID)
    {
        messageRoomList.removeMessageRoom(messageRoomID);
    }

    @Override
    public ArrayList<Message> messagesGet(int messageRoomID, int offset) throws RemoteException
    {
        return api.messagesGet(getLoggedEmployeeID(), messageRoomID, offset);
    }

    @Override
    public Message sendMessage(int messageRoomID, String message) throws SQLException, RemoteException
    {
        return api.messagePost(getLoggedEmployeeID(), messageRoomID, message);
    }

    @Override
    public void messageAddLocal(int messageRoomID, Message message)
    {
        System.out.println("test rmi listens");
        for(MessageRoom messageRoomItem : messageRoomList.getMessageRooms()) {
            if(messageRoomItem.getId() == messageRoomID) {
                messageRoomItem.addMessage(message);
                property.firePropertyChange("message", null, message);
                break;
            }
        }
    }

    @Ignore
    @Override
    public void removeMessageRoomFromEmployee(int messageRoomID)
    {
        messageRoomList.removeMessageRoom(messageRoomID);
    }

    @Ignore
    @Override
    public void removeMessageRoomFromEmployee(MessageRoom room)
    {
        messageRoomList.removeMessageRoom(room);
    }

    @Override
    public ArrayList<MessageRoom> getMessageRoomsByEmployeeID(int employeeID)
    {
        return messageRoomList.getMessageRoomsByEmployeeID(employeeID);
    }

    @Override
    public ArrayList<MessageRoom> messageRoomGetPrivate() throws RemoteException
    {
        ArrayList<MessageRoom> messageRooms = api.messageRoomGetPrivate(getLoggedEmployeeID());
        messageRoomList.addRoomList(messageRooms);
        return messageRooms;
    }

    @Override
    public ArrayList<MessageRoom> messageRoomGetAll() throws RemoteException
    {
        ArrayList<MessageRoom> messageRooms = api.messageRoomGetAll(getLoggedEmployeeID());
        messageRoomList.addRoomList(messageRooms);
        return messageRooms;
    }

    @Override
    public ArrayList<MessageRoom> getMessageRoomsByAnything(String keyword) throws RemoteException
    {
        messageRoomGetAll();
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
    public ArrayList<String> getMessageRoomParticipantNames(MessageRoom messageRoom) throws RemoteException
    {
        ArrayList<String> participants = new ArrayList<>();

        getEmployees();

        for (int userID :
                messageRoom.getUsersIDs())
        {
            participants.add(employeeList.getEmployeeByID(userID).getName());
        }

        return participants;
    }

    @Override
    public MessageRoom getMessageRoomByID(int id) throws SQLException, RemoteException
    {
        MessageRoom messageRoom = messageRoomList.getMessageRoomByID(id);
        if(messageRoom != null) {
            return messageRoom;
        }
        MessageRoom newMessageRoom = api.getMessageRoomByID(id);
        messageRoomList.addMessageRoom(newMessageRoom);
        return newMessageRoom;
    }


    @Ignore
    @Override
    public void addEmployee(Employee employee)
    {
        employeeList.addEmployee(employee);
    }

    @Override
    public void addEmployee(String username, String password, String name, String surname, String role) throws SQLException, GeneralSecurityException, IOException
    {
        employeeList.addEmployee(api.employeeRegister(username, password, name, surname, role));
    }

    @Override
    public void addEmployee(String username, String password, String name, String surname,
                            String role, ArrayList<String> permissions) throws SQLException, GeneralSecurityException, IOException
    {
        Employee employee = api.employeeRegister(username, password, name, surname, role);
        employeeList.addEmployee(employee);
        api.setPermissions(getLoggedEmployeeID(), employee.getId(), permissions.toArray(new String[0]));
        employee.setPermissions(permissions);
    }

    @Override
    public Employee removePermission(int employeeID2, String permission) throws SQLException, RemoteException
    {
        return api.removePermission(getLoggedEmployeeID(), employeeID2, permission);
    }

    @Override
    public Employee setPermissions(int employeeID2, String[] permissions) throws SQLException, RemoteException
    {
        return api.setPermissions(getLoggedEmployeeID(), employeeID2, permissions);
    }


    @Override
    public Employee removeEmployee(int employeeID) throws SQLException, RemoteException
    {
        employeeList.removeEmployee(employeeID);
        return api.employeeDelete(getLoggedEmployeeID(), employeeID);
    }

    @Override
    public void updateLocalEmployee(Employee employee)
    {
        ArrayList<Employee> employees = employeeList.getEmployees();
        for(Employee employeeItem : employees) {
            if(employeeItem.getId() == employee.getId()) {
                employeeItem = employee;
                break;
            }
        }
        employeeList.addEmployee(employee);
    }

    @Override
    public void removeLocalEmployee(int employeeID)
    {
        employeeList.removeEmployee(employeeID);
    }

    @Override
    public Employee employeeRestore(int employeeID) throws SQLException, RemoteException
    {
        if (!employeeList.restoreEmployee(employeeID))
        {
            getEmployeeByID(employeeID); // getting the emp from the server, if he is not in the list
            employeeList.restoreEmployee(employeeID);
        }
        return api.employeeRestore(getLoggedEmployeeID(), employeeID);
    }

    @Override
    public ArrayList<Employee> getEmployees() throws RemoteException
    {
        employeeList.addAllEmployees(api.getAllEmployees());
        return employeeList.getEmployees();
    }

    @Override
    public ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) throws RemoteException
    {
        return api.employeeGetByIDs(employeesIDs);
    }

    @Override
    public ArrayList<Employee> getEmployeesByMessageRoom(int messageRoom)
    {
        return employeeList.getEmployeesByMessageRoom(messageRoom);
    }

    @Override
    public ArrayList<Employee> getEmployeesByEvent(int eventID) throws RemoteException
    {
        getEmployees();
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
    public Employee getEmployeeByID(int id) throws SQLException, RemoteException
    {
        Employee employee = employeeList.getEmployeeByID(id);
        if(employee != null) {
            return employee;
        }
        Employee newEmployee = api.getEmployeeByID(id);
        employeeList.addEmployee(newEmployee);
        return newEmployee;
    }

    @Override
    public Employee employeeSetName(int employeeID2, String name) throws SQLException, RemoteException
    {
        return api.employeeSetName(getLoggedEmployeeID(), employeeID2, name);
    }

    @Override
    public Employee employeeSetSurname(int employeeID2, String surname) throws SQLException, RemoteException
    {
        return api.employeeSetSurname(getLoggedEmployeeID(), employeeID2, surname);
    }

    @Override
    public Employee employeeSetPassword(int employeeID2, String password) throws GeneralSecurityException, SQLException, IOException, RemoteException
    {
        return api.employeeSetPassword(getLoggedEmployeeID(), employeeID2, password);
    }

    @Override
    public Employee employeeSetUsername(int employeeID2, String username) throws SQLException, RemoteException
    {
        return api.employeeSetUsername(getLoggedEmployeeID(), employeeID2, username);
    }

    @Override
    public Employee employeeSetRole(int employeeID2, String role) throws SQLException, RemoteException
    {
        return api.employeeSetRole(getLoggedEmployeeID(), employeeID2, role);
    }

    /**
     * Removes the employee from an event.
     *
     * @param employeeID An integer containing the Employee's ID
     * @param eventID    An integer containing the event ID.
     */
    @Override
    public void removeEventFromEmployee(int employeeID, int eventID) throws SQLException, RemoteException
    {
        Employee employeeByID = getEmployeeByID(employeeID);
        ArrayList<Integer> eventIDs = employeeByID.getEvents();

        for (int i = 0; i < eventIDs.size(); i++)
        {
            if (eventIDs.get(i) == eventID)
            {
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
    @Override
    public void removeMessageRoomFromEmployee(int employeeID, int messageRoomID) throws SQLException, RemoteException
    {
        Employee employeeByID = getEmployeeByID(employeeID);
        ArrayList<Integer> messageRoomIDs = employeeByID.getMessageRooms();

        for (int i = 0; i < messageRoomIDs.size(); i++)
        {
            if (messageRoomIDs.get(i) == messageRoomID)
            {
                employeeByID.removeMessageRoom(i);
                return;
            }
        }

    }

    @Override
    public void addRoom(String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException
    {
        Room room = api.createRoom(getLoggedEmployeeID(), roomNumber, buildingAddress, numberOfSeats, floor);
        roomList.addRoom(room);
    }

    @Override
    public void addRoom(String roomCode, String buildingAddress, int numberOfSeats, int floor, ArrayList<String> equipment) throws SQLException, RemoteException
    {
        Room room = api.createRoom(getLoggedEmployeeID(), roomCode, buildingAddress, numberOfSeats, floor);
        roomList.addRoom(room);
        api.roomEquipmentSet(getLoggedEmployeeID(), room.getRoomID(), equipment.toArray(new String[0]));
    }


    @Override
    public void removeRoom(int roomID) throws RemoteException
    {
        roomList.removeRoom(roomID);
        api.roomDeleteByID(getLoggedEmployeeID(), roomID);
    }

    @Override
    public void removeRoom(Room room) throws RemoteException
    {
        int roomID = room.getRoomID();
        roomList.removeRoom(roomID);
        api.roomDeleteByID(getLoggedEmployeeID(), roomID);
    }

    @Override
    public void updateLocalRoom(Room room)
    {
        ArrayList<Room> rooms = roomList.getRooms();
        for(Room roomItem : rooms) {
            if(roomItem.getRoomID() == room.getRoomID()) {
                roomItem = room;
                break;
            }
        }
        roomList.addRoom(room);
    }

    @Override
    public void removeLocalRoom(int roomID)
    {
        roomList.removeRoom(roomID);
    }

    @Override
    public void modifyRoom(int roomID, String roomCode, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException
    {
        roomList.modifyRoom(roomID, roomCode, buildingAddress, numberOfSeats, floor);
        api.roomSetFloor(getLoggedEmployeeID(), roomID, floor);
        api.roomSetNumberOfSeats(getLoggedEmployeeID(), roomID, numberOfSeats);
        api.roomSetRoomNumber(getLoggedEmployeeID(), roomID, roomCode);
        api.roomSetBuildingAddress(getLoggedEmployeeID(), roomID, buildingAddress);
    }

    @Override
    public boolean roomEquipmentAdd(int roomID, String equipment) throws RemoteException, SQLException
    {
        getRoomByID(roomID).addEquipment(equipment);
        return api.roomEquipmentAdd(getLoggedEmployeeID(), roomID, equipment);
    }

    @Override
    public boolean roomEquipmentRemove(int roomID, String equipment) throws RemoteException, SQLException
    {
        getRoomByID(roomID).removeEquipment(equipment);
        return api.roomEquipmentRemove(getLoggedEmployeeID(), roomID, equipment);
    }

    @Override
    public boolean roomEquipmentSet(int roomID, String[] equipment) throws SQLException, RemoteException
    {
        return api.roomEquipmentSet(getLoggedEmployeeID(), roomID, equipment);
    }

    @Override
    public boolean roomEquipmentLocalSet(int roomID, String[] equipment)
    {
        for(Room roomItem : roomList.getRooms()) {
            if(roomItem.getRoomID() == roomID) {
                roomItem.setEquipment(new ArrayList<String>(
                    Arrays.asList(equipment)));
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] roomEquipmentGet(int roomID) throws RemoteException
    {
        return api.roomEquipmentGet(roomID);
    }

    @Override
    public void modifyRoom(Room room, String roomCode, String buildingAddress, int numberOfSeats, int floor)
    {
        roomList.modifyRoom(room, roomCode, buildingAddress, numberOfSeats, floor);
    }

    @Override
    public int getRoomsCreated()
    {
        return RoomList.getRoomsCreated();
    }

    @Override
    public ArrayList<Room> getRooms() throws RemoteException
    {
        roomList.addAllRooms(api.getAllRooms());
        return roomList.getRooms();
    }

    @Override
    public ArrayList<Room> getRoomsByAnything(String keyword) throws RemoteException
    {
        getRooms();
        return roomList.getRoomsByAnything(keyword);
    }

    @Override
    public Room getRoomByID(int id) throws SQLException, RemoteException
    {
        Room room = roomList.getRoomByID(id);
        if(room != null) {
            return room;
        }
        Room newRoom = api.getRoomByID(id);
        roomList.addRoom(newRoom);
        return newRoom;
    }



    //EVENT

    @Override
    public Event eventGetByID(int id) throws SQLException, RemoteException
    {
        Event event = eventList.getByID(id);
        if(event != null) {
            return event;
        }
        Event newEvent = api.eventGetByID(id);
        eventList.add(newEvent);
        return newEvent;
    }

    @Override
    public ArrayList<Event> getEvents() throws RemoteException
    {
        ArrayList<Event> events = api.eventGetAll();
        eventList.addAll(events);
        return events;

    }


    @Override
    public Event eventCreate(int roomID, long timeStart, long timeEnd,
                             String title, String description, String platform, String onlineLink) throws SQLException, RemoteException
    {
        return api.eventCreate(getLoggedEmployeeID(), roomID, timeStart, timeEnd, title, description, platform, onlineLink);
    }

    @Override
    public boolean isRoomAvailable(int roomID, long startTime, long endTime)
    {
        return eventList.isRoomAvailable(roomID, startTime, endTime);
    }
    

    @Override
    public boolean eventRemoveByID(int id) throws RemoteException, SQLException
    {
        eventList.removeByID(eventGetByID(id));
        return api.eventDeleteByID(getLoggedEmployeeID(), id);
    }

    @Override
    public Event eventSetTitle(int eventID, String title) throws SQLException, RemoteException
    {
        return api.eventSetTitle(getLoggedEmployeeID(), eventID, title);
    }

    @Override
    public Event eventSetDescription(int eventID, String description) throws SQLException, RemoteException
    {
        return api.eventSetDescription(getLoggedEmployeeID(), eventID, description);
    }

    @Override
    public Event eventSetOnlineURL(int eventID, String url) throws SQLException, RemoteException
    {
        return api.eventSetOnlineURL(getLoggedEmployeeID(), eventID, url);
    }

    @Override
    public Event eventSetPlatform(int eventID, String platform) throws SQLException, RemoteException
    {
        return api.eventSetPlatform(getLoggedEmployeeID(), eventID, platform);
    }

    @Override
    public Event eventSetOnlineState(int eventID, boolean isOnline) throws SQLException, RemoteException
    {
        return api.eventSetOnlineState(getLoggedEmployeeID(), eventID, isOnline);
    }

    @Override
    public Event eventSetTime(int eventID, long startTime, long endTime) throws SQLException, RemoteException
    {
        return api.eventSetTime(getLoggedEmployeeID(), eventID, startTime, endTime);
    }

    @Override
    public Event eventSetRoom(int eventID, int roomID) throws SQLException, RemoteException
    {
        return api.eventSetRoom(getLoggedEmployeeID(), eventID, roomID);
    }

    @Override
    public Event eventSetParticipants(int eventID, int[] participants) throws SQLException, RemoteException
    {
        return api.eventSetParticipants(getLoggedEmployeeID(), eventID, participants);
    }

    @Override
    public boolean eventJoin(int employeeID1, int eventID) throws SQLException, RemoteException
    {
        return api.eventJoin(employeeID1, eventID);
    }

    @Override
    public boolean eventLeave(int employeeID1, int eventID) throws SQLException, RemoteException
    {
        return api.eventLeave(employeeID1, eventID);
    }

    @Override
    public void eventLocalSet(Event event)
    {
        ArrayList<Event> events = eventList.getAll();
        for(Event eventItem : events) {
            if(eventItem.getID() == event.getID()) {
                eventItem = event;
                break;
            }
        }
        eventList.add(event);
    }

    @Override
    public void eventLocalRemove(int messageRoomID)
    {
        eventList.removeByID(messageRoomID);
    }

    @Override
    public ArrayList<Event> eventGetByDate(String date)
    {
        return eventList.getByDate(date);
    }

    @Override
    public ArrayList<Event> eventGetByText(String text)
    {
        return eventList.getByText(text);
    }

    @Override
    public ArrayList<Integer> getParticipants()
    {
        return event.getParticipants();
    }

    @Override
    public void setParticipants(ArrayList<Integer> employees)
    {
        event.setParticipants(employees);
    }

    @Override
    public ArrayList<Event> getEventsByRoom(int roomID) throws RemoteException
    {
        getEvents();
        return eventList.getEventsByRoom(roomID);
    }

}
