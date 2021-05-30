package client;

import Shared.API;
import Shared.Employee.Employee;
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

    //EMPLOYEES

    /**
     * Log in method for gaining access to the server.
     *
     * @param username A string containing the username.
     * @param password A string containing the password.
     * @return An employee object of the logged in employee, if the login was successful.
     * @throws SQLException
     * @throws GeneralSecurityException
     * @throws IOException
     */
    //TODO finish the javadoc
    public Employee employeeLogin(String username, String password) throws SQLException, GeneralSecurityException, IOException
    {
        return server.employeeLogin(username, password);
    }
//TODO finish the javadoc

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
     * @throws SQLException
     */

    //TODO implement this in the model manager
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

    public Employee employeeSetName(int employeeID1, int employeeID2, String name) throws SQLException, RemoteException
    {
        return server.employeeSetName(employeeID1, employeeID2, name);
    }

    public Employee employeeSetSurname(int employeeID1, int employeeID2, String surname) throws SQLException, RemoteException
    {
        return server.employeeSetSurname(employeeID1, employeeID2, surname);
    }

    public Employee employeeSetRole(int employeeID1, int employeeID2, String role) throws SQLException, RemoteException
    {
        return server.employeeSetRole(employeeID1, employeeID2, role);
    }


    public Employee addPermission(int employeeID1, int employeeID2, String permission) throws SQLException, RemoteException
    {
        return server.employeePermissionAdd(employeeID1, employeeID2, permission);
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
        return  server.roomSetRoomNumber(employeeID1, roomID, roomNumber);
    }

    public Room roomSetBuildingAddress(int employeeID1, int roomID, String buildingAddress) throws SQLException, RemoteException
    {
        return server.roomSetBuildingAddress(employeeID1, roomID, buildingAddress);
    }

    public String[] roomEquipmentGet(int roomID) throws RemoteException
    {
        return server.roomEquipmentGet(roomID);
    }

    public boolean roomEquipmentAdd(int roomID, String equipment) throws RemoteException
    {
        return server.roomEquipmentAdd(roomID, equipment);
    }

    public boolean roomEquipmentRemove(int roomID, String equipment) throws RemoteException
    {
        return server.roomEquipmentRemove(roomID, equipment);
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


}
