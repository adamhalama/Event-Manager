package client.ViewModel;

import Shared.Room.Room;
import client.Model.Model;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

        Room[] rooms = model.getRooms().toArray(new Room[0]);

        for (Room r : rooms)
        {
            roomList.add(new RoomViewModel(r.getRoomID(), r.getRoomNumber(), r.getFloor(), r.getBuildingAddress(), r.getNumberOfSeats()));
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
        if (searchBox.get().equals(""))
        {
            reset();
            return;
        }
        else
        roomList.clear();

        Room[] rooms = model.getRoomsByAnything(searchBox.get()).toArray(new Room[0]);

        for (Room r : rooms)
        {
            roomList.add(new RoomViewModel(r.getRoomID(), r.getRoomNumber(), r.getFloor(), r.getBuildingAddress(), r.getNumberOfSeats()));
        }
    }

    public void removeRoom(int selectedIndex, int roomID)
    {
        roomList.remove(selectedIndex);
        model.removeRoom(roomID);
    }
}
