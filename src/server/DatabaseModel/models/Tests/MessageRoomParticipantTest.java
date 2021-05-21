package server.DatabaseModel.models.Tests;

import server.DatabaseModel.DatabaseHandler;

import java.sql.SQLException;
import java.util.Arrays;

public class MessageRoomParticipantTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    /*boolean created = databaseHandler.messageRoomParticipant.create(1, 1); // Add participant
    System.out.println(created);*/

    /*--GET ONE--*/
    /*boolean participantExists = databaseHandler.messageRoomParticipant.exists(1, 1); // Check if participant exists
    System.out.println(participantExists);*/

    /*--GET ALL--*/
    /*int[] messageRoomParticipants = databaseHandler.messageRoomParticipant.getParticipants(1); // Get room participants
    System.out.println("[ " + String.join(", ", Arrays.stream(messageRoomParticipants).mapToObj(String::valueOf).toArray(String[]::new)) + " ]");
    int[] userRooms = databaseHandler.messageRoomParticipant.getRooms(1); // Get user rooms
    System.out.println("[ " + String.join(", ", Arrays.stream(userRooms).mapToObj(String::valueOf).toArray(String[]::new)) + " ]");*/
  }
}
