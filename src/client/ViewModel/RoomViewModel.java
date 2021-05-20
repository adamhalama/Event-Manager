package client.ViewModel;

import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RoomViewModel
{
    private IntegerProperty roomID;
    private StringProperty roomNumber;
    private IntegerProperty floor;
    private StringProperty address;
    private IntegerProperty numberOfSeats;
    private ObservableList<EquipmentViewModel> equipmentList;

    private int currentRoomID;

    private Model model;

    public RoomViewModel(int roomID, String roomNumber, int floor, String address, int numberOfSeats)
    {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.floor = new SimpleIntegerProperty(floor);
        this.address = new SimpleStringProperty(address);
        this.numberOfSeats = new SimpleIntegerProperty(numberOfSeats);
    }

    public RoomViewModel(Model model)
    {
        this.model = model;

        roomID = new SimpleIntegerProperty();
        roomNumber = new SimpleStringProperty();
        floor = new SimpleIntegerProperty();
        address = new SimpleStringProperty();
        numberOfSeats = new SimpleIntegerProperty();

        equipmentList = FXCollections.observableArrayList();
    }

    public void reset()
    {
        roomID.setValue(currentRoomID);
        roomNumber.setValue(model.getRoomByID(currentRoomID).getRoomNumber());
        floor.setValue(model.getRoomByID(currentRoomID).getFloor());
        address.setValue(model.getRoomByID(currentRoomID).getBuildingAddress());
        numberOfSeats.setValue(model.getRoomByID(currentRoomID).getNumberOfSeats());

        equipmentList.clear();
        for (String equip:
                model.getRoomByID(currentRoomID).getEquipment())
        {
            equipmentList.add(new EquipmentViewModel(equip));
        }
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

    public ObservableList<EquipmentViewModel> getEquipmentList()
    {
        return equipmentList;
    }

    public int getCurrentRoomID()
    {
        return currentRoomID;
    }

    public void setCurrentRoomID(int roomID)
    {
        currentRoomID = roomID;
    }
}
