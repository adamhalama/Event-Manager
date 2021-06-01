package client.ViewModel;

import Shared.Employee.Employee;
import Shared.Room.Room;
import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateEventViewModel
{
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;

    private StringProperty linkField;
    private StringProperty newParticipantField;

    private ObservableList<Integer> roomPickerList;
    private ObservableList<EmployeeViewModel> employeeList;

    private StringProperty errorLabel;

    private Model model;

    public CreateEventViewModel(Model model)
    {
        this.model = model;

        this.titleProperty = new SimpleStringProperty();
        this.descriptionProperty = new SimpleStringProperty();
        this.linkField = new SimpleStringProperty();

        this.roomPickerList = FXCollections.observableArrayList();

        this.newParticipantField = new SimpleStringProperty();

        this.employeeList = FXCollections.observableArrayList();

        this.errorLabel = new SimpleStringProperty();
    }

    public void reset()
    {
        titleProperty.set("");
        descriptionProperty.set("");

        linkField.set("");
        newParticipantField.set("");

        employeeList.clear();
        roomPickerList.clear();
        try
        {
            for (Room r :
                    model.getRooms())
            {
                roomPickerList.add(r.getRoomID());
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }



    public void removeParticipant(int index)
    {
        employeeList.remove(index);
    }

    public boolean createButton(String platform, long startTimestamp, long endTimestamp, int room)
    {
        if (titleProperty.get() == null || titleProperty.get().equals(""))
        {
            errorLabel.set("Please set a title");
            return false;
        }

        if (startTimestamp >= endTimestamp)
        {
            errorLabel.set("The event cant end before it starts.");
            return false;
        }

        try
        {
            model.eventCreate(room, startTimestamp, endTimestamp, titleProperty.get(), descriptionProperty.get(), platform, linkField.get());
        } catch (SQLException throwables)
        {
            errorLabel.set(throwables.getMessage());
            throwables.printStackTrace();
        } catch (RemoteException e)
        {
            errorLabel.set("Server error");
            e.printStackTrace();
        }
        return true;
    }

    public void addParticipant()
    {
        String employeeID = newParticipantField.getValue().contains("(") ? newParticipantField.getValue().split("[()]")[1] : "no id";
        String id = employeeID.equals("no id") ? employeeID : employeeID.split(" ")[1];
        //getting just the id number from the whole string

        if (id.equals("no id"))
        {
            errorLabel.setValue("Pick an employee from the list");
            return;
        }

        int empID = Integer.parseInt(id);

        Employee employee;
        try
        {
            employee = model.getEmployeeByID(empID);
        } catch (SQLException | RemoteException throwables)
        {
            throwables.printStackTrace();
            errorLabel.setValue("Failed to add an employee");
            return;
        }

        // check if the employee is already in the list
        for (EmployeeViewModel employeeViewModel : employeeList)
        {
            if (employeeViewModel.getUserIDProperty().get() == empID)
            {
                errorLabel.setValue("The employee is already added");
                return;
            }
        }

        employeeList.add(new EmployeeViewModel(empID, employee.getName(), employee.getSurname(), employee.getRole()));
        newParticipantField.set("");
    }


    public ArrayList<String> getAllEmployeesForAutocomplete()
    {
        ArrayList<String> returnStrings = new ArrayList<>();

        ArrayList<Employee> employees;
        try
        {
            employees = model.getEmployees();
        } catch (RemoteException e)
        {
            e.printStackTrace();
            return returnStrings;
        }

        for (Employee employee :
                employees)
        {
            returnStrings.add(employee.getName() + " " + employee.getSurname() + "(id " + employee.getId() + ")");
        }
        return returnStrings;
    }



    public StringProperty getNewParticipantFieldProperty()
    {
        return newParticipantField;
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public StringProperty getLinkFieldproperty()
    {
        return linkField;
    }

    public StringProperty getTitleProperty()
    {
        return titleProperty;
    }

    public StringProperty getDescriptionProperty()
    {
        return descriptionProperty;
    }



    public int getID()
    {
        return model.getEvent_id();
    }


    public ObservableList<Integer> getRoomList()
    {
        return roomPickerList;
    }

    public ObservableList<EmployeeViewModel> getEmployeeList()
    {
        return employeeList;
    }
}
