package server.DatabaseModel.models.Tests;

import server.DatabaseModel.DatabaseHandler;

public class EmployeePermissionTest
{
  public static void main(String[] args)
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    /*boolean created = databaseHandler.employeePermission.create("room_create", 1); // Add employee permission
    System.out.println(created);*/

    /*--GET ONE--*/
    /*boolean permissionExists = databaseHandler.employeePermission.exists("room_create", 1); // Check if permission exists
    System.out.println(permissionExists);*/

    /*--GET ALL--*/
    String[] employeePermissions = databaseHandler.employeePermission.getAllByID(1); // Get room equipment
    System.out.println("[ " + String.join(", ", employeePermissions) + " ]");
  }
}
