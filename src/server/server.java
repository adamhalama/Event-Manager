package server;

import server.DatabaseModel.DatabaseHandler;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

class Server
{
    public static void main(String[] args)
        throws IOException
    {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        startRegistry();
        RmiServer server = new RmiServer();
        server.start(databaseHandler);
        System.out.println("Server started...");
        //test(server);
    }

    public static void startRegistry() throws RemoteException
    {
        try
        {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Registry started...");
        } catch (java.rmi.server.ExportException e)
        {
            System.out.println("Registry already started? " + e.getMessage());
        }
    }

    /*private static void test(RmiServer server)
    {
        try
        {
            server.employeeSetSurname(1, 1, "NewName");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}