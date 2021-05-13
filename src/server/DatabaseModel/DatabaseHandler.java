package server.DatabaseModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHandler
{
    private String url = "jdbc:postgresql://avrstlzujnmrbd:fe4b3eed1db09683f9494477814865cffd6c116cd686a2533bc1021605ebdea8@ec2-54-73-58-75.eu-west-1.compute.amazonaws.com:5432/ddjtq5l8q18roq";
    private String user = "avrstlzujnmrbd";
    private String password = "fe4b3eed1db09683f9494477814865cffd6c116cd686a2533bc1021605ebdea8";
    private int port = 5432;
    private String database = "ddjtq5l8q18roq";
    private Connection connection;

    public DatabaseHandler()
    {
        try
        {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void test()
    {
        try
        {
            // Define sql statement
            String sql = "CREATE SCHEMA IF NOT EXISTS CdList; CREATE TABLE IF NOT EXISTS CdList.cd ( ID     SERIAL PRIMARY KEY, Artist CHARACTER VARYING(50) NOT NULL, Title  CHARACTER VARYING(50) NOT NULL ); CREATE TABLE IF NOT EXISTS CdList.track ( ID     SERIAL PRIMARY KEY, cdID int NOT                   NULL, Artist CHARACTER VARYING(50) NOT NULL, Title  CHARACTER VARYING(50) NOT NULL, Length int NOT                NULL, FOREIGN KEY (cdID) REFERENCES CdList.cd (ID) ); INSERT INTO CdList.cd (Artist, Title) VALUES ('Beatles', 'Best Of'); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (1, 'Beatles', 'Help', 212); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (1, 'Beatles', 'She loves you', 171); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (1, 'Beatles', 'Michelle', 185); INSERT INTO CdList.cd (Artist, Title) VALUES ('Various Artists', 'Mixed'); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (2, 'Kiss', 'A world without Heroes', 133); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (2, 'Indigo Girls', 'Galileo', 230); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (2, 'Elton John', 'Circle of Life', 274); INSERT INTO CdList.cd (Artist, Title) VALUES ('AC/DC', 'Best Of'); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (3, 'AC/DC', 'Thunderstruck', 255); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (3, 'AC/DC', 'Back in Black', 263); INSERT INTO CdList.cd (Artist, Title) VALUES ('Bob', 'My House'); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (4, 'Bob and Wendy', 'See me', 539); INSERT INTO CdList.track(cdID, Artist, Title, Length) VALUES (4, 'Wendy', 'See you', 565);";
            // create statement
            PreparedStatement statement = this.connection.prepareStatement(sql);
            /*statement.setString(1, "No one");
            statement.setString(2, "Best test CD");*/
            // execute update
            ResultSet resultSet = statement.executeQuery();
            System.out.println("SQL execute resultSet: " + resultSet);
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
