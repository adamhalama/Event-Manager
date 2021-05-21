package server.DatabaseModel.models.Tests;

import Shared.Event.Event;
import server.DatabaseModel.DatabaseHandler;
import server.DatabaseModel.models.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class EventTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    // TODO: 5/21/2021 Fill correct fields and values after event class is refactored
    /*Event event = databaseHandler.event.create("JackHamer", "123qweasd", "Jack", "Hamer", "Admin");
    System.out.println(event);*/

    /*--GET ONE--*/
    //Event event = databaseHandler.event.getByID(1); // Get by id
    //Event event = databaseHandler.event.getOne("id = 1"); // Get one where
    //Event event = databaseHandler.event.getOne("id = 1", "id DESC"); // Get one where and sorted
    //System.out.println(event);

    /*--GET MANY--*/
    //ArrayList<Event> events = databaseHandler.event.getAll(); // Get everything
    //ArrayList<Event> events = databaseHandler.event.getAll("id DESC"); // Get ordered by descending id
    //ArrayList<Event> events = databaseHandler.event.getAll("id DESC", 5, 0); // Get max 5 rows ordered by descending id
    //ArrayList<Event> events = databaseHandler.event.getAllWhere("creator = 1"); // Get where
    //ArrayList<Event> events = databaseHandler.event.getAllWhere("creator = 1", 3, 0); // Get max 3 where
    //ArrayList<Event> events = databaseHandler.event.getAll("id DESC", "creator = 1", 3, 0); // Get max 3 where and sorted in descending order

    /*--EDIT--*/
    // TODO: 5/21/2021 Fill correct fields and values after event class is refactored
    /*ArrayList<Event> events = databaseHandler.event.edit(
      new String[] {"name", "surname"},
      Model.formatStringValues(new String[] {"Jason","Statham"}),
      "id = 1"
    );*/

    /*for (Event event : events) {
      System.out.println(event);
    }*/
  }
}
