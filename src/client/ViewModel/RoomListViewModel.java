package client.ViewModel;

import Shared.Room.Room;
import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;

public class RoomListViewModel
{
    private StringProperty searchBox;
    private ObservableList<RoomViewModel> roomList;
    private StringProperty errorLabel;

    private Model model;

    public RoomListViewModel(Model model)
    {
        this.model = model;

        searchBox = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();

        roomList = FXCollections.observableArrayList();

        reset();
    }

    public void reset()
    {
        searchBox.setValue(null);
        errorLabel.setValue(null);

        roomList.clear();


        try
        {
            for (Room r : model.getRooms())
            {
                roomList.add(new RoomViewModel(r.getRoomID(), r.getRoomNumber(), r.getFloor(), r.getBuildingAddress(), r.getNumberOfSeats()));
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public ObservableList<RoomViewModel> getRoomList()
    {
        return roomList;
    }

    public StringProperty getSearchBoxProperty()
    {
        return searchBox;
    }


    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public void search()
    {
        if (searchBox == null)
            return;

        if (searchBox.get().equals(""))
        {
            reset();
            return;
        }
        else
        roomList.clear();


        try
        {
            for (Room r : model.getRoomsByAnything(searchBox.get()))
            {
                roomList.add(new RoomViewModel(r.getRoomID(), r.getRoomNumber(), r.getFloor(), r.getBuildingAddress(), r.getNumberOfSeats()));
            }
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    public void removeRoom(int selectedIndex, int roomID)
    {
        try
        {
            model.removeRoom(roomID);
            roomList.remove(selectedIndex);
        } catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }
}
