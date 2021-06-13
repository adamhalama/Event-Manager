package client.ViewModel;

import Shared.Employee.Employee;
import Shared.Event.Event;
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
    private StringProperty topLabelProperty;
    private StringProperty titleProperty;
    private StringProperty descriptionProperty;

    private StringProperty linkField;
    private StringProperty newParticipantField;

    private ObservableList<Integer> roomPickerList;
    private ObservableList<EmployeeViewModel> employeeList;

    private StringProperty errorLabel;

    private Model model;

    private boolean isEditing;
    private int currentEventID;

    public CreateEventViewModel(Model model)
    {
        this.model = model;

        this.topLabelProperty = new SimpleStringProperty();
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
        if (currentEventID == 0) //creating
        {
            topLabelProperty.set("Create an event");
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
        else //editing
        {
            Event e;
            try
            {
                e = model.eventGetByID(currentEventID);
            } catch (SQLException throwables)
            {
                errorLabel.set(throwables.getMessage());
                throwables.printStackTrace();
                return;
            } catch (RemoteException ex)
            {
                errorLabel.set("Server error");
                ex.printStackTrace();
                return;
            }

            topLabelProperty.set("Edit an event");
            titleProperty.set(e.getTitle());
            descriptionProperty.set(e.getDescription());

            linkField.set(e.getOnlineLink());
            newParticipantField.set("");

            employeeList.clear();
            try
            {
                for (Integer participantID :
                        e.getParticipants())
                {
                    Employee emp = model.getEmployeeByID(participantID);
                    employeeList.add(new EmployeeViewModel(emp.getId(), emp.getName(), emp.getSurname(), emp.getRole()));
                }
            } catch (RemoteException | SQLException ex)
            {
                ex.printStackTrace();
            }

            roomPickerList.clear();
            try
            {
                for (Room r :
                        model.getRooms())
                {
                    roomPickerList.add(r.getRoomID());
                }
            } catch (RemoteException ex)
            {
                ex.printStackTrace();
            }
        }
    }



    public void removeParticipant(int index)
    {
        employeeList.remove(index);
    }

    public boolean createButton(String platform, long startTimestamp, long endTimestamp, int room)
    {
        //universal for create and edit
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
        if (startTimestamp < System.currentTimeMillis())
        {
            errorLabel.set("You cant pick past time.");
            return false;
        }


        if (currentEventID == 0) //create
        {
            try
            {
                Event event = model.eventCreate(room, startTimestamp, endTimestamp, titleProperty.get(), descriptionProperty.get(), platform, linkField.get());
                model.eventJoin(model.getLoggedEmployeeID(), event.getID());
                if (!employeeList.isEmpty())
                {
                    for (EmployeeViewModel e :
                            employeeList)
                    {
                        model.eventJoin(e.getUserIDProperty().get(), event.getID());
                    }
                }

            } catch (SQLException throwables)
            {
                errorLabel.set(throwables.getMessage());
                throwables.printStackTrace();
                return false;
            } catch (RemoteException e)
            {
                errorLabel.set("Server error");
                e.printStackTrace();
                return false;
            }
        }
        else //editing
        {
            try
            {
                model.eventSetTitle(currentEventID, titleProperty.get());
                model.eventSetDescription(currentEventID, descriptionProperty.get());
                model.eventSetTime(currentEventID, startTimestamp, endTimestamp);
                model.eventSetPlatform(currentEventID, platform);
                model.eventSetOnlineURL(currentEventID, linkField.get());
                model.eventSetRoom(currentEventID, room);

                ArrayList<Integer> employees = new ArrayList<>();
                for (EmployeeViewModel e:
                     employeeList)
                {
                    employees.add(e.getUserIDProperty().get());
                }
                int[] primitive = employees.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();

                model.eventSetParticipants(currentEventID, primitive);


            } catch (SQLException throwables)
            {
                errorLabel.set(throwables.getMessage());
                throwables.printStackTrace();
                return false;
            } catch (RemoteException e)
            {
                errorLabel.set("Server error");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public void addParticipant()
    {
        String employeeID = newParticipantField.getValue().contains("(") ? newParticipantField.getValue().split("[()]")[1] : "no id";
        String id = employeeID.equals("no id") ? employeeID : employeeID.split(" ")[1];
        //getting just the id number from the whole string
        // if the format is not correct, the string is filled filled "no id"

        if (id.equals("no id"))
        {
            errorLabel.setValue("Pick an employee from the list");
            return;
        }
        int empID = Integer.parseInt(id); // creating an integer from a string
        Employee employee;
        try
        {
            employee = model.getEmployeeByID(empID);
            //get the employee from the server trough the model
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

    public StringProperty getLinkFieldProperty()
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

    public ObservableList<Integer> getRoomList()
    {
        return roomPickerList;
    }

    public ObservableList<EmployeeViewModel> getEmployeeList()
    {
        return employeeList;
    }

    public void setErrorLabel(String text)
    {
        errorLabel.set(text);
    }

    public StringProperty getTopLabelProperty()
    {
        return topLabelProperty;
    }

    public void setCurrentEventID(int currentEventID)
    {
        this.currentEventID = currentEventID;
    }
}
