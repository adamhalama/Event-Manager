package server.DatabaseModel;

import server.DatabaseModel.models.EmployeeModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseHandler
{
    private Connection connection;
    public EmployeeModel employee;

    public DatabaseHandler()
    {
        try {
            Class.forName("org.postgresql.Driver");
            //this.connection = DriverManager.getConnection("jdbc:postgresql:"+DatabaseCredentials.HOST+":"+DatabaseCredentials.PORT+"/"+DatabaseCredentials.NAME, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD);
            this.connection = DriverManager.getConnection("jdbc:postgresql:"+DatabaseCredentials.NAME, DatabaseCredentials.USER, DatabaseCredentials.PASSWORD);
            //this.connection.setAutoCommit(false);
            this.employee = new EmployeeModel(this.connection);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void test() {
        try {
            // Define sql statement
            String sql = "CREATE SCHEMA IF NOT EXISTS CdList; CREATE TABLE IF NOT EXISTS CdList.cd(ID SERIAL PRIMARY KEY, Artist CHARACTER VARYING(50) NOT NULL, Title  CHARACTER VARYING(50) NOT NULL );";
            // create statement
            Statement statement = this.connection.createStatement();
            /*statement.setString(1, "No one");
            statement.setString(2, "Best test CD");*/
            // execute update
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("SQL execute resultSet: " + resultSet);
            resultSet.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
