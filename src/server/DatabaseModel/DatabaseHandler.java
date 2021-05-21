package server.DatabaseModel;

import server.DatabaseModel.models.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler
{
    private Connection connection;
    public EmployeeModel employee;
    public EmployeePermissionModel employeePermission;
    public RoomModel room;
    public RoomEquipmentModel roomEquipment;
    public MessageModel message;
    public MessageRoomModel messageRoom;
    public MessageRoomParticipantModel messageRoomParticipant;
    public EventModel event;
    public EventParticipantModel eventParticipant;

    public DatabaseHandler()
    {
        try {
            Class.forName("org.postgresql.Driver");
            //this.connection = DriverManager.getConnection("jdbc:postgresql:"+DatabaseCredentials.HOST+":"+DatabaseCredentials.PORT+"/"+DatabaseCredentials.NAME, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD);
            this.connection = DriverManager.getConnection("jdbc:postgresql:"+DatabaseCredentials.NAME, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD);
            //this.connection.setAutoCommit(false);
            this.employee = new EmployeeModel(this.connection);
            this.employeePermission = new EmployeePermissionModel(this.connection);
            this.room = new RoomModel(this.connection);
            this.roomEquipment = new RoomEquipmentModel(this.connection);
            this.message = new MessageModel(this.connection);
            this.messageRoom = new MessageRoomModel(this.connection);
            this.messageRoomParticipant = new MessageRoomParticipantModel(this.connection);
            this.event = new EventModel(this.connection);
            this.eventParticipant = new EventParticipantModel(this.connection);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
