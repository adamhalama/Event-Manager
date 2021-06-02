package server.DatabaseModel.models;

import server.DatabaseModel.Utils.DBResponse;
import server.DatabaseModel.Utils.ResponseRow;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageRoomParticipantModel extends Model
{

  public MessageRoomParticipantModel(Connection connection) {
    super(connection, "message_room_participant");
  }

  private int[] arrayListToInt(ArrayList<Integer> numbersArrList) {
    int[] ret = new int[numbersArrList.size()];
    for (int i=0; i < ret.length; i++)
    {
      ret[i] = numbersArrList.get(i).intValue();
    }
    return ret;
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

  public boolean delete(int messageRoomID, int userID)
  {
    try
    {
      return super.modelDelete("message_room_id = " + messageRoomID + " AND employee_id = " + userID);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public boolean deleteAll(int messageRoomID)
  {
    try
    {
      return super.modelDelete("message_room_id = " + messageRoomID);
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public int[] getParticipants(int messageRoomID)
  {
    ArrayList<Integer> participants = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "message_room_id = " + messageRoomID, 0, 0);
      ArrayList<ResponseRow> responseRows = dbResponse.getRows();
      for(ResponseRow row : responseRows)
      {
        participants.add(Integer.valueOf(row.getField("employee_id")));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return this.arrayListToInt(participants);
  }

  public int[] getRooms(int userID)
  {
    ArrayList<Integer> participants = new ArrayList<>();
    try
    {
      DBResponse dbResponse = super.modelGetAll(null, "employee_id = " + userID, 0, 0);
      ArrayList<ResponseRow> responseRows = dbResponse.getRows();
      for(ResponseRow row : responseRows)
      {
        participants.add(Integer.valueOf(row.getField("message_room_id")));
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return this.arrayListToInt(participants);
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
