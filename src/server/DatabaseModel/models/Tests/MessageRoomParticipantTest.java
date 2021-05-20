package server.DatabaseModel.models.Tests;

import server.DatabaseModel.DatabaseHandler;

import java.sql.SQLException;

public class MessageRoomParticipantTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    boolean created = databaseHandler.messageRoomParticipant.create(1, 1); // Add participant
    System.out.println(created);

    /*--GET ONE--*/
    boolean participantExists = databaseHandler.messageRoomParticipant.exists(1, 1); // Check if participant exists
    System.out.println(participantExists);
  }
}
