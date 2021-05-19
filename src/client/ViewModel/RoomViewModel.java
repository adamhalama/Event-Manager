package client.ViewModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RoomViewModel
{
    private IntegerProperty roomID;
    private StringProperty roomNumber;
    private IntegerProperty floor;
    private StringProperty address;
    private IntegerProperty numberOfSeats;
    //probably going to add equipment also, if the viewModel is going to be used with the roomViewController

    public RoomViewModel(int roomID, String roomNumber, int floor, String address, int numberOfSeats)
    {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.floor = new SimpleIntegerProperty(floor);
        this.address = new SimpleStringProperty(address);
        this.numberOfSeats = new SimpleIntegerProperty(numberOfSeats);
    }

    public IntegerProperty getRoomIDProperty()
    {
        return roomID;
    }

    public StringProperty getAddressProperty()
    {
        return address;
    }

    public IntegerProperty getFloorProperty()
    {
        return floor;
    }

    public IntegerProperty getNumberOfSeatsProperty()
    {
        return numberOfSeats;
    }

    public StringProperty getRoomNumberProperty()
    {
        return roomNumber;
    }
}
