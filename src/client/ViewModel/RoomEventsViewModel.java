package client.ViewModel;

import Shared.Event.Event;
import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;

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
        for (Event event:
             model.getEventsByRoom(currentRoomID))
        {
            if(event.getEndTime() > System.currentTimeMillis())
            {
                try
                {
                    eventList.add(new EventViewModel(event.getEvent_id(), event.getTitle(),
                            model.getFormattedDateTime(event.getStartTime()), model.getFormattedDateTime(event.getEndTime()),
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
