package client.ViewModel;

import Shared.Date;
import Shared.Employee.Employee;
import Shared.Event.Event;
import Shared.Room.Room;
import client.Model.Model;
import client.View.Helpers.ConvertTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EventListViewModel
{
    private Model model;

    private ObservableList<EventViewModel> eventList;

    private StringProperty errorLabel;
    private StringProperty searchField;

    public EventListViewModel(Model model)
    {
        this.model = model;

        this.errorLabel = new SimpleStringProperty();
        this.searchField = new SimpleStringProperty();
        this.eventList = FXCollections.observableArrayList();

        reset();
    }

    public void reset()
    {
        errorLabel.set("");
        searchField.set("");
        try
        {
            loadEventList(model.getEvents());
        } catch (RemoteException e)
        {
            errorLabel.set("Server error.");
            e.printStackTrace();
        }
    }


    public ObservableList<EventViewModel> getEventList()
    {
        return eventList;
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public StringProperty getSearchProperty()
    {
        return searchField;
    }


    public void removeButton(int selectedIndex, int selectedID)
    {
        try
        {
            if (!model.removeByEventID(selectedID))
                errorLabel.set("The event doesnt exist.");

            eventList.remove(selectedIndex);
        } catch (RemoteException e)
        {
            errorLabel.set("A server error has occurred.");
        } catch (SQLException throwables)
        {
            errorLabel.set(throwables.getMessage());
        }
    }


    public void searchButton()
    {
        try
        {
            model.getEvents();

            if (searchField.get() == null || searchField.get().equals(""))
                loadEventList(model.getEvents());
            else
                loadEventList(model.eventGetByText(searchField.get()));
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void searchByDateButton(LocalDate date)
    {
        loadEventList(model.eventGetByDate(Date.timestampToDate(ConvertTime.parseStringToTimestamp(date.toString()))));
    }

    private void loadEventList(ArrayList<Event> events)
    {
        try
        {
            eventList.clear();
            for (Event e :
                    events)
            {
                Employee employee = model.getEmployeeByID(e.getCreatorID());
                String roomNameId = "No room booked";

                Room room = e.getRoomID() == 0 ? null : model.getRoomByID(e.getRoomID()) ;
                if (room != null)
                {
                    roomNameId = room.getRoomNumber() + " - id" + room.getRoomID();
                }

                String platform = e.getPlatform().toString() ;
                eventList.add(new EventViewModel(e.getID(), e.getTitle(), e.getTimeStartEnd(), platform,
                        roomNameId, employee.getFullName(), e.getParticipants().size()));
            }
        } catch (SQLException throwables)
        {
            errorLabel.set(throwables.getMessage());
        } catch (RemoteException e)
        {
            errorLabel.set(e.getMessage());
            e.printStackTrace();
        }
    }


    public void setErrorLabel(String text)
    {
        errorLabel.set(text);
    }
}
