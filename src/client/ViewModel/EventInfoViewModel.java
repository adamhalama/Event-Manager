package client.ViewModel;

import Shared.Employee.Employee;
import Shared.Event.Event;
import client.Model.Model;
import client.View.Helpers.ConvertTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class EventInfoViewModel
{
    private StringProperty title;
    private StringProperty description;
    private StringProperty startTime;
    private StringProperty endTime;
    private StringProperty platform;
    private StringProperty link;
    private StringProperty room;
    private StringProperty creator;
    private StringProperty errorLabel;

    private ObservableList<EmployeeViewModel> employeeList;

    private Model model;
    private int currentEventID;


    public EventInfoViewModel(Model model)
    {
        this.model = model;

        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.startTime = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();
        this.platform = new SimpleStringProperty();
        this.link = new SimpleStringProperty();
        this.room = new SimpleStringProperty();
        this.creator = new SimpleStringProperty();

        employeeList = FXCollections.observableArrayList();
    }

    public void reset()
    {
        Event e = model.getEventByID(currentEventID);

        title.set("");
        description.set("");
        startTime.set("");
        endTime.set("");
        platform.set("");
        link.set("");
        room.set("");
        creator.set("");
        
        title.set(e.getTitle());
        description.set(e.getDescription());
        startTime.set(ConvertTime.getFormattedDateTime(e.getTimeStart()));
        endTime.set(ConvertTime.getFormattedTime(e.getTimeEnd()));
        link.set(e.getOnlineLink());

        String platform = e.getPlatform().equals("") ? "This event is only physical" : e.getPlatform();
        this.platform.set(platform);

        int roomID = e.getRoomID();
        try
        {
            String roomName = roomID == 0 ? "No room has been booked" : model.getRoomByID(roomID).getRoomNumber() + " id - " + roomID;
            room.set(roomName);
            creator.set((model.getEmployeeByID(e.getCreatorID())).getFullName());
        } catch (SQLException throwables)
        {
            errorLabel.set(throwables.getMessage());
            throwables.printStackTrace();
        } catch (RemoteException remoteException)
        {
            errorLabel.set("Server error");
            remoteException.printStackTrace();
        }

        employeeList.clear();
        for (Employee emp : model.getEmployeesByEvent(currentEventID))
        {
            employeeList.add(new EmployeeViewModel(emp.getId(), emp.getName(), emp.getSurname(), emp.getRole()));
        }
    }

    public StringProperty getTitleProperty()
    {
        return title;
    }

    public StringProperty getDescriptionProperty()
    {
        return description;
    }

    public StringProperty getStartTimeProperty()
    {
        return startTime;
    }

    public StringProperty getEndTimeProperty()
    {
        return endTime;
    }


    public StringProperty getPlatformProperty()
    {
        return platform;
    }

    public StringProperty getLinkProperty()
    {
        return link;
    }

    public StringProperty getRoomProperty()
    {
        return room;
    }

    public StringProperty getCreatorProperty()
    {
        return creator;
    }

    public String copyButton()
    {
        return link.get();
    }

    public void setCurrentEventID(int currentEventID)
    {
        this.currentEventID = currentEventID;
    }

    public ObservableList<EmployeeViewModel> getEmployeeList()
    {
        return employeeList;
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }
}
