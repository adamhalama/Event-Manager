package server.DatabaseModel.models;

import server.DatabaseModel.DatabaseCredentials;
import Shared.Employee.Employee;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeModel
{
    private Connection connection;

    public EmployeeModel(Connection connection)
    {
        this.connection = connection;
    }

    private String getRoute()
    {
        return null;
        //return DatabaseCredentials.SCHEMA_NAME + ".employee";
    }

    public int create(String username, String password, String name, String surname, String role) throws SQLException
    {
        try
        {
            String sql = "INSERT INTO " + this.getRoute() + " (username, password, name, surname, role) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.setString(4, surname);
            statement.setString(5, role);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            statement.close();
            return id;
        } catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public ArrayList<Employee> getAll()
    {
        ArrayList<Employee> employees = new ArrayList<>();
        try
        {
            String sql = "SELECT * FROM " + this.getRoute() + ";";
            Statement statement = this.connection.createStatement();
            ResultSet response = statement.executeQuery(sql);
            while (response.next())
            {
                int id = response.getInt("id");
                String username = response.getString("username");
                String password = response.getString("password");
                String name = response.getString("name");
                String surname = response.getString("surname");
                String role = response.getString("role");
                employees.add(new Employee(id, username, name, surname, role));
            }
            response.close();
            statement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return employees;
    }
}
