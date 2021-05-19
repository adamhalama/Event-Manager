package client.ViewModel;

import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateRoomViewModel
{
    private StringProperty roomNumber;
    private IntegerProperty floor;
    private StringProperty address;
    private IntegerProperty seats;
    private StringProperty equipmentToAdd;
    private ObservableList<EquipmentViewModel> equipmentList;

    private Model model;

    public CreateRoomViewModel(Model model)
    {
        this.model = model;

        roomNumber = new SimpleStringProperty();
        floor = new SimpleIntegerProperty();
        address = new SimpleStringProperty();
        seats = new SimpleIntegerProperty();
        equipmentToAdd = new SimpleStringProperty();

        equipmentList = FXCollections.observableArrayList();
    }

    public void addEquipment(boolean editing)
    {
        if (equipmentToAdd.getValue() != null && !equipmentToAdd.get().equals(""))
        {
                equipmentList.add(new EquipmentViewModel(equipmentToAdd.get()));
                equipmentToAdd.setValue(null);
        }

    }

    public void removeEquipment(int selectedIndex, boolean editing)
    {
        if (equipmentList.size() != 0 && equipmentList.size() -1 >= selectedIndex && selectedIndex >= 0)
            equipmentList.remove(selectedIndex);
    }

    public void confirm(boolean editing)
    {
        // todo
        //  some confirmation
        if (!editing)
            model.addRoom(roomNumber.get(), address.get(), seats.get(), floor.get());
        else
            return;
        // something like room.modify();
    }

    public ObservableList<EquipmentViewModel> getEquipmentList()
    {
        return equipmentList;
    }

    public StringProperty getRoomNumberProperty()
    {
        return roomNumber;
    }

    public IntegerProperty getFloorProperty()
    {
        return floor;
    }

    public StringProperty getAddressProperty()
    {
        return address;
    }

    public IntegerProperty getSeatsProperty()
    {
        return seats;
    }

    public StringProperty getEquipmentToAddProperty()
    {
        return equipmentToAdd;
    }


}
