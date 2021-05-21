package server.DatabaseModel.models.Tests;

import Shared.Messages.MessageRoom;
import server.DatabaseModel.DatabaseHandler;

import java.sql.SQLException;

public class MessageRoomTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    //MessageRoom messageRoom = databaseHandler.messageRoom.create("New message room");

    /*--GET ONE--*/
    //MessageRoom messageRoom = databaseHandler.messageRoom.getByID(1); // Get by message room id

    /*--EDIT--*/
    MessageRoom messageRoom = databaseHandler.messageRoom.editName("New name", 1); // Get by message room id

    System.out.println(messageRoom);
  }
}
