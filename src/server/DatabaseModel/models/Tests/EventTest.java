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
    /*Event event = databaseHandler.event.create("Title", "Description", 2, 1, 1, 1622548899, 1622558899); // Create offline event
    //Event event = databaseHandler.event.create("Title", "Description", "DISCORD", "123qweasd", 1, 1, 1622548899, 1622558899); // Create online event
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
    /*ArrayList<Event> events = databaseHandler.event.edit(
      new String[] {"title", "description"},
      Model.formatStringValues(new String[] {"New title","New Description"}),
      "id = 1"
    );*/

    /*for (Event event : events) {
      System.out.println(event);
    }*/
  }
}
