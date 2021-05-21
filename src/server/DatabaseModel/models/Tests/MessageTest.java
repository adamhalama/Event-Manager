package server.DatabaseModel.models.Tests;

import Shared.Messages.Message;
import server.DatabaseModel.DatabaseHandler;
import server.DatabaseModel.models.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class MessageTest
{
  public static void main(String[] args) throws SQLException
  {
    DatabaseHandler databaseHandler = new DatabaseHandler();

    /*--CREATE--*/
    /*Message message = databaseHandler.message.create(1, 1, "Hello! This is a message!");
    System.out.println(message);*/

    /*--GET MANY--*/
    //ArrayList<Message> messages = databaseHandler.message.getByRoomID(1, 20, 0); // Get by message room id
    //ArrayList<Message> messages = databaseHandler.message.getByUserID(1, 20, 0); // Get by message user id

    /*for (Message message : messages) {
      System.out.println(message);
    }*/
  }
}
