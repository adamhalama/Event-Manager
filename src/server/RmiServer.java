package server;

import Shared.API;
import Shared.Employee.Employee;
import Shared.Room.Room;
import server.APIMethods.Utils.Crypt;
import server.APIMethods.Utils.ObjectInfo;
import server.DatabaseModel.DatabaseHandler;
import utility.observer.listener.GeneralListener;

import java.io.IOException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RmiServer implements API
{
    private DatabaseHandler databaseHandler;

    public void start(DatabaseHandler databaseHandler) throws RemoteException, MalformedURLException
    {
        this.databaseHandler = databaseHandler;
        UnicastRemoteObject.exportObject(this, 0);
        Naming.rebind("API", this);
    }

    @Override
    public Employee registerEmployee(String username, String password, String name, String surname, String role)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.create(username, encryptedPassword, name, surname, role), this.databaseHandler);
    }

    @Override
    public Employee loginEmployee(String username, String password)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByUsernameAndPassword(username, encryptedPassword), this.databaseHandler);
    }

    @Override
    public Employee getEmployee(int employeeID) throws SQLException
    {
        return ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID), this.databaseHandler);
    }

    @Override
    public ArrayList<Employee> getEmployees(ArrayList<Integer> employeesIDs) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            for(Integer employeeID : employeesIDs) {
                ObjectInfo.getFullEmployee(this.databaseHandler.employee.getByID(employeeID), this.databaseHandler);
            }
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Room getRoom(int roomID) throws SQLException
    {
        return ObjectInfo.getFullRoom(this.databaseHandler.room.getByID(roomID), this.databaseHandler);
    }

    @Override
    public ArrayList<Room> getRooms(ArrayList<Integer> roomIDs) {
        ArrayList<Room> employees = new ArrayList<>();
        try {
            for(Integer roomID : roomIDs) {
                ObjectInfo.getFullRoom(this.databaseHandler.room.getByID(roomID), this.databaseHandler);
            }
        } catch (Error | SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}