package server;

import Shared.Employee.Employee;
import server.APIMethods.Utils.Crypt;
import server.DatabaseModel.DatabaseHandler;

import java.io.IOException;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

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
        return this.databaseHandler.employee.create(username, encryptedPassword, name, surname, role);
    }

    @Override
    public Employee loginEmployee(String username, String password)
        throws GeneralSecurityException, IOException, SQLException
    {
        String encryptedPassword = Crypt.encryptPassword(password);
        return this.databaseHandler.employee.getByUsernameAndPassword(username, encryptedPassword);
    }
}