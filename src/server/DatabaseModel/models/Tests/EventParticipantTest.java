package server.DatabaseModel.models.Tests;

import server.DatabaseModel.DatabaseHandler;

import java.sql.SQLException;
import java.util.Arrays;

public class EventParticipantTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    /*boolean created = databaseHandler.eventParticipant.create(1, 1); // Add participant
    System.out.println(created);*/

    /*--GET ONE--*/
    /*boolean participantExists = databaseHandler.eventParticipant.exists(1, 1); // Check if participant exists
    System.out.println(participantExists);*/

    /*--GET ALL--*/
    /*int[] eventParticipants = databaseHandler.eventParticipant.getParticipants(1); // Get room participants
    System.out.println("[ " + String.join(", ", Arrays.stream(eventParticipants).mapToObj(String::valueOf).toArray(String[]::new)) + " ]");
    int[] userEvents = databaseHandler.eventParticipant.getEvents(1); // Get user rooms
    System.out.println("[ " + String.join(", ", Arrays.stream(userEvents).mapToObj(String::valueOf).toArray(String[]::new)) + " ]");*/
  }
}
