package server.DatabaseModel.models.Tests;

import server.DatabaseModel.DatabaseHandler;

import java.util.ArrayList;

public class RoomEquipmentTest
{
  public static void main(String[] args)
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    /*boolean created = databaseHandler.roomEquipment.create("broom", 1); // Add room equipment
    System.out.println(created);*/

    /*--GET ONE--*/
    /*boolean permissionExists = databaseHandler.roomEquipment.exists("broom", 1); // Check if room has equipment
    System.out.println(permissionExists);*/

    /*--GET ALL--*/
    String[] roomEquipment = databaseHandler.roomEquipment.getAllByID(1); // Get room equipment
    System.out.println("[ " + String.join(", ", roomEquipment) + " ]");
  }
}
