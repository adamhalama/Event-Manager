package server.DatabaseModel.models;

import Shared.Messages.Message;
import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageModel extends Model
{

  public MessageModel(Connection connection) {
    super(connection, "message");
  }

  private ArrayList<Message> getMessagesFromResponse(DBResponse dbResponse) {
    ArrayList<ResponseRow> rows = dbResponse.getRows();
    ArrayList<Message> messages = new ArrayList<>();
    for(ResponseRow row : rows) {
      int userID = Integer.parseInt(row.getField("user_id"));
      long timestamp = Long.parseLong(row.getField("timestamp"));
      String message = row.getField("message");
      messages.add(new Message(userID, timestamp, message));
    }
    return messages;
  }

  public ArrayList<Message> getByRoomID(int messageRoomID, int limit, int offset)
  {
    return this.getAll("timestamp DESC", "message_room_id = " + messageRoomID, limit, offset);
  }
  public ArrayList<Message> getByUserID(int userID, int limit, int offset)
  {
    return this.getAll("timestamp DESC", "user_id = " + userID, limit, offset);
  }
  public ArrayList<Message> getAll(String order, String where, int limit, int offset)
  {
    ArrayList<Message> messages = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(order, where, limit, offset);
      messages = getMessagesFromResponse(dbResponse);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return messages;
  }

  public Message create(int messageRoomID, int userID, String message)
      throws SQLException
  {
    DBResponse dbResponse = super.modelInsert(
        new String[] {
            "message_room_id", "user_id", "message", "timestamp"
        },
        new String[] {
            String.valueOf(messageRoomID), String.valueOf(userID), "'"+message+"'",
            String.valueOf(System.currentTimeMillis())
        }
    );
    return getMessagesFromResponse(dbResponse).get(0);
  }
}
