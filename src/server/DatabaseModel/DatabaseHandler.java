package server.DatabaseModel;

import Shared.Employee.Employee;
import server.APIMethods.Utils.Crypt;
import server.DatabaseModel.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.*;

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
            this.checkSchema();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    private void checkSchema() {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet response = statement.executeQuery("SELECT schema_name FROM information_schema.schemata WHERE schema_name = '" + DatabaseCredentials.SCHEMA_NAME + "';");
            response.next();
            String schemaName = response.getString("schema_name");
            if(schemaName.equals(DatabaseCredentials.SCHEMA_NAME)) {
                System.out.println("Database Schema found.");
                return;
            }
            System.out.println("\n\n--------------------------\nWARNING! No database schema found. Initializing database!");
            this.writeDB();
            this.createAdminAccount();
            System.out.println("Schema has been initiated.\n--------------------------\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void writeDB() {
        try {
            System.out.println("Creating schema...");
            String sqlDBInit = Files.readString(Paths.get(System.getProperty("user.dir")+"\\src\\server\\SEP2-Database.sql"));
            PreparedStatement statement = this.connection.prepareStatement(sqlDBInit);
            statement.executeUpdate();
            System.out.println("Schema has been created.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void createAdminAccount() {
        try {
            System.out.println("Creating admin account...");
            String encryptedPassword = Crypt.encryptPassword("admin");
            Employee employee = this.employee.create("Admin", encryptedPassword, "Admin", "Admin", "Admin");
            String[] permissions = new String[] {"event_join", "event_create", "event_edit", "room_create_edit", "employees_create_edit", "chat_rooms_create_edit"};
            for(String permission : permissions) {
                this.employeePermission.create(permission, employee.getId());
            }
            System.out.println("Admin account has been created.");
        } catch (GeneralSecurityException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
