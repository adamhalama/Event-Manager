package server.DatabaseModel.models.Tests;

import Shared.Room.Room;
import server.DatabaseModel.DatabaseHandler;
import server.DatabaseModel.models.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class RoomTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    Room room = databaseHandler.room.create("B2", "Horsens", 3, 1);
    System.out.println(room);

    /*--GET ONE--*/
    //Room room = databaseHandler.room.getByID(1); // Get by id
    //Room room = databaseHandler.room.getOne("floor = 1"); // Get one where
    //Room room = databaseHandler.room.getOne("floor = 1", "id DESC"); // Get one where and sorted
    //System.out.println(room);

    /*--GET MANY--*/
    //ArrayList<Room> rooms = databaseHandler.room.getAll(); // Get everything
    //ArrayList<Room> rooms = databaseHandler.room.getAll("id DESC"); // Get ordered by descending id
    //ArrayList<Room> rooms = databaseHandler.room.getAll("id DESC", 5, 0); // Get max 5 rows ordered by descending id
    //ArrayList<Room> rooms = databaseHandler.room.getAllWhere("floor = 1"); // Get where
    //ArrayList<Room> rooms = databaseHandler.room.getAllWhere("floor = 1", 3, 0); // Get max 3 where
    //ArrayList<Room> rooms = databaseHandler.room.getAll("id DESC", "floor = 1", 3, 0); // Get max 3 where and sorted in descending order

    /*--EDIT--*/
    /*ArrayList<Room> rooms = databaseHandler.room.edit(
      new String[] {"building_address"},
      Model.formatStringValues(new String[] {"New address"}),
      "id = 1"
    );

    for (Room room : rooms) {
      System.out.println(room);
    }*/

    /*--DELETE--*/
    /*boolean deleted = databaseHandler.room.deleteByID(1); // Delete room by ID
    System.out.println(deleted);*/
  }
}
