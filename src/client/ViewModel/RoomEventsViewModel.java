package client.ViewModel;

import Shared.Event.Event;
import client.Model.Model;
import client.View.Helpers.ConvertTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomEventsViewModel
{
    private final Model model;
    private ObservableList<EventViewModel> eventList;
    private StringProperty errorLabel, topLabel;

    private int currentRoomID;

    public RoomEventsViewModel(Model model)
    {
        this.model = model;

        errorLabel = new SimpleStringProperty();
        topLabel = new SimpleStringProperty();

        eventList = FXCollections.observableArrayList();

        reset();
    }

    public void reset()
    {
        eventList.clear();
        ArrayList<Event> eventsByRoom;
        try
        {
            eventsByRoom = model.getEventsByRoom(currentRoomID);
        } catch (RemoteException e)
        {
            errorLabel.set("Server error");
            e.printStackTrace();
            return;
        }

        for (Event event:
                eventsByRoom)
        {
            if(event.getTimeEnd() > System.currentTimeMillis())
            {
                try
                {
                    eventList.add(new EventViewModel(event.getID(), event.getTitle(),
                            event.getTimeStartEnd(),
                            model.getEmployeeByID(event.getCreatorID()).getFullName(), event.getParticipants().size()));
                } catch (SQLException throwables)
                {
                    errorLabel.setValue(throwables.getMessage());
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                    errorLabel.set("Error while loading the list");
                }
            }
        }
    }

    public ObservableList<EventViewModel> getEventsOfThisRoom()
    {
        return eventList;
    }

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public StringProperty getTopLabelProperty()
    {
        return topLabel;
    }

    public void setCurrentRoomID(int currentRoomID)
    {
        this.currentRoomID = currentRoomID;
    }
}
