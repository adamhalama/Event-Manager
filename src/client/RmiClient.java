package client;

import Shared.API;
import Shared.ClientListener;
import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Messages.Message;
import Shared.Messages.MessageRoom;
import Shared.Room.Room;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RmiClient
{
    private API server;

    public RmiClient()
    {
        try
        {
            server = (API) Naming.lookup("rmi://localhost:1099/API");
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void registerClientListener(ClientListener client)
        throws RemoteException
    {
        server.registerClientListener(client);
    }

    public void removeClientListener(ClientListener client)
        throws RemoteException
    {
        server.removeClientListener(client);
    }

    //EMPLOYEES

    /**
     * Log in method for gaining access to the system on the server.
     *
     * @param username A string containing the username.
     * @param password A string containing the password.
     * @return An employee object of the logged in employee, if the login was successful.
     * @throws SQLException Throws an exception with a message.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Employee employeeLogin(String username, String password) throws SQLException, GeneralSecurityException, IOException
    {
        return server.employeeLogin(username, password);
    }

    /**
     * Used for registering an employee in the system.
     *
     * @param username A string containing the username the new user will have.
     * @param password A string containing the password the new user will have.
     * @param name     A string containing the name the new user will have.
     * @param surname  A string containing the surname the new user will have.
     * @param role     A string containing the role the new user will have.
     * @return An Employee object of the created employee in the system on the server side.
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws SQLException Exeption with a message
     */

    public Employee employeeRegister(String username, String password, String name, String surname, String role)
            throws GeneralSecurityException, IOException, SQLException
    {
        return server.employeeRegister(username, password, name, surname, role);
    }

    public Employee employeeDelete(int employeeID1, int employeeID2) throws SQLException, RemoteException
    {
        return server.employeeDelete(employeeID1, employeeID2);
    }

    public Employee employeeRestore(int employeeID1, int employeeID2) throws SQLException, RemoteException
    {
        return server.employeeRestore(employeeID1, employeeID2);
    }

    public ArrayList<Employee> getAllEmployees() throws RemoteException
    {
        return server.employeeGetAll();
    }


    public Employee getEmployeeByID(int employeeID) throws SQLException, RemoteException
    {
        return server.employeeGetByID(employeeID);
    }

    public ArrayList<Employee> employeeGetByIDs(ArrayList<Integer> employeesIDs) throws RemoteException
    {
        return server.employeeGetByIDs(employeesIDs);
    }

    public Employee employeeSetPassword(int employeeID1, int employeeID2, String password) throws GeneralSecurityException, SQLException, IOException, RemoteException
    {
        return server.employeeSetPassword(employeeID1, employeeID2, password);
    }

    public Employee employeeSetName(int employeeID1, int employeeID2, String name) throws SQLException, RemoteException
    {
        return server.employeeSetName(employeeID1, employeeID2, name);
    }

    public Employee employeeSetSurname(int employeeID1, int employeeID2, String surname) throws SQLException, RemoteException
    {
        return server.employeeSetSurname(employeeID1, employeeID2, surname);
    }

    public Employee employeeSetUsername(int employeeID1, int employeeID2, String username) throws SQLException, RemoteException
    {
        return server.employeeSetUsername(employeeID1, employeeID2, username);
    }


    public Employee employeeSetRole(int employeeID1, int employeeID2, String role) throws SQLException, RemoteException
    {
        return server.employeeSetRole(employeeID1, employeeID2, role);
    }


    public Employee addPermission(int employeeID1, int employeeID2, String permission) throws SQLException, RemoteException
    {
        return server.employeePermissionAdd(employeeID1, employeeID2, permission);
    }

    public Employee removePermission(int employeeID1, int employeeID2, String permission) throws SQLException, RemoteException
    {
        return server.employeePermissionRemove(employeeID1, employeeID2, permission);
    }

    public Employee setPermissions(int employeeID1, int employeeID2, String[] permissions) throws SQLException, RemoteException
    {
        return server.employeePermissionSet(employeeID1, employeeID2, permissions);
    }


    //ROOMS
    public Room createRoom(int employeeID1, String roomNumber, String buildingAddress, int numberOfSeats, int floor) throws SQLException, RemoteException
    {
        return server.roomCreate(employeeID1, roomNumber, buildingAddress, numberOfSeats, floor);
    }

    public ArrayList<Room> getAllRooms() throws RemoteException
    {
        return server.roomGetAll();
    }

    public boolean roomDeleteByID(int employeeID1, int roomID) throws RemoteException
    {
        return server.roomDeleteByID(employeeID1, roomID);
    }

    public Room getRoomByID(int roomID) throws SQLException, RemoteException
    {
        return server.roomGetByID(roomID);
    }

    public ArrayList<Room> getRoomsByIDs(ArrayList<Integer> roomIDs) throws RemoteException
    {
        return server.roomGetByIDs(roomIDs);
    }

    public Room roomSetFloor(int employeeID1, int roomID, int floor) throws SQLException, RemoteException
    {
        return server.roomSetFloor(employeeID1, roomID, floor);
    }

    public Room roomSetNumberOfSeats(int employeeID1, int roomID, int numberOfSeats) throws SQLException, RemoteException
    {
        return server.roomSetNumberOfSeats(employeeID1, roomID, numberOfSeats);
    }

    public Room roomSetRoomNumber(int employeeID1, int roomID, String roomNumber) throws SQLException, RemoteException
    {
        return server.roomSetRoomNumber(employeeID1, roomID, roomNumber);
    }

    public Room roomSetBuildingAddress(int employeeID1, int roomID, String buildingAddress) throws SQLException, RemoteException
    {
        return server.roomSetBuildingAddress(employeeID1, roomID, buildingAddress);
    }

    public String[] roomEquipmentGet(int roomID) throws RemoteException
    {
        return server.roomEquipmentGet(roomID);
    }

    public boolean roomEquipmentSet(int employeeID1, int roomID, String[] equipment) throws SQLException, RemoteException
    {
        return server.roomEquipmentSet(employeeID1, roomID, equipment);
    }

    public boolean roomEquipmentAdd(int employeeID1, int roomID, String equipment) throws RemoteException, SQLException
    {
        return server.roomEquipmentAdd(employeeID1, roomID, equipment);
    }

    public boolean roomEquipmentRemove(int employeeID1, int roomID, String equipment) throws RemoteException, SQLException
    {
        return server.roomEquipmentRemove(employeeID1, roomID, equipment);
    }


    //MESSAGES

    public MessageRoom messageRoomCreatePrivate(int employeeID1, int employeeID2) throws SQLException, RemoteException
    {
        return server.messageRoomCreatePrivate(employeeID1, employeeID2);
    }

    public MessageRoom getMessageRoomByID(int messageRoomID) throws SQLException, RemoteException
    {
        return server.messageRoomGetByID(messageRoomID);
    }

    public ArrayList<MessageRoom> messageRoomGetPrivate(int employeeID1) throws RemoteException
    {
        return server.messageRoomGetPrivate(employeeID1);
    }

    public ArrayList<MessageRoom> messageRoomGetAll(int employeeID1) throws RemoteException
    {
        return server.messageRoomGetAll(employeeID1);
    }

    public MessageRoom messageRoomSetName(int employeeID1, int messageRoomID, String name) throws SQLException, RemoteException
    {
        return server.messageRoomSetName(employeeID1, messageRoomID, name);
    }

    public ArrayList<Message> messagesGet(int employeeID1, int messageRoomID, int offset) throws RemoteException
    {
        return server.messagesGet(employeeID1, messageRoomID, offset);
    }

    public Message messagePost(int employeeID1, int messageRoomID, String message) throws SQLException, RemoteException
    {
        return server.messagePost(employeeID1, messageRoomID, message);
    }

    //EVENT

    public Event eventGetByID(int eventID) throws SQLException, RemoteException
    {
        return server.eventGetByID(eventID);
    }

    public ArrayList<Event> eventGetAll() throws RemoteException
    {
        return server.eventGetAll();
    }

    public Event eventCreate(int employeeID1, int roomID, long timeStart, long timeEnd, String title, String description, String platform, String onlineLink) throws SQLException, RemoteException
    {
        return server.eventCreate(employeeID1, roomID, timeStart, timeEnd, title, description, platform, onlineLink);
    }


    public boolean eventDeleteByID(int employeeID1, int eventID) throws RemoteException, SQLException
    {
        return server.eventDeleteByID(employeeID1, eventID);
    }

    public Event eventSetTitle(int employeeID1, int eventID, String title) throws SQLException, RemoteException
    {
        return server.eventSetTitle(employeeID1, eventID, title);
    }

    public Event eventSetDescription(int employeeID1, int eventID, String description) throws SQLException, RemoteException
    {
        return server.eventSetDescription(employeeID1, eventID, description);
    }

    public Event eventSetOnlineURL(int employeeID1, int eventID, String url) throws SQLException, RemoteException
    {
        return server.eventSetOnlineURL(employeeID1, eventID, url);
    }

    public Event eventSetPlatform(int employeeID1, int eventID, String platform) throws SQLException, RemoteException
    {
        return server.eventSetPlatform(employeeID1, eventID, platform);
    }

    public Event eventSetOnlineState(int employeeID1, int eventID, boolean isOnline) throws SQLException, RemoteException
    {
        return server.eventSetOnlineState(employeeID1, eventID, isOnline);
    }

    public Event eventSetTime(int employeeID1, int eventID, long startTime, long endTime) throws SQLException, RemoteException
    {
        return server.eventSetTime(employeeID1, eventID, startTime, endTime);
    }

    public Event eventSetRoom(int employeeID1, int eventID, int roomID) throws SQLException, RemoteException
    {
        return server.eventSetRoom(employeeID1, eventID, roomID);
    }

    public Event eventSetParticipants(int employeeID1, int eventID, int[] participants) throws SQLException, RemoteException
    {
        return server.eventSetParticipants(employeeID1, eventID, participants);
    }

    public boolean eventJoin(int employeeID1, int eventID) throws SQLException, RemoteException
    {
        return server.eventJoin(employeeID1, eventID);
    }

    public boolean eventLeave(int employeeID1, int eventID) throws SQLException, RemoteException
    {
        return server.eventLeave(employeeID1, eventID);
    }

}
