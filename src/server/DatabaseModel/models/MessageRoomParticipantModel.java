package server.DatabaseModel.models;

import server.DatabaseModel.Utils.DBResponse;

import java.sql.Connection;
import java.sql.SQLException;

public class MessageRoomParticipantModel extends Model
{

  public MessageRoomParticipantModel(Connection connection) {
    super(connection, "message_room_participant");
  }

  public boolean exists(int messageRoomID, int userID)
  {
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "message_room_id = " + messageRoomID + " AND employee_id = " + userID , 1, 0);
      return dbResponse.getRawRows().size() >= 1;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public boolean create(int messageRoomID, int userID)
  {
    try
    {
      DBResponse dbResponse = super.modelInsert(new String[] {"message_room_id", "employee_id"},
          new String[] {String.valueOf(messageRoomID), String.valueOf(userID)});
      return dbResponse.getRawRows().size() >= 1;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
