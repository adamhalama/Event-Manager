package client.ViewModel;

import Shared.Room.Room;
import client.Model.Model;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateRoomViewModel
{
    private StringProperty topLabel;
    private StringProperty roomNumber;
    private IntegerProperty floor;
    private StringProperty address;
    private IntegerProperty seats;
    private StringProperty equipmentToAdd;
    private ObservableList<EquipmentViewModel> equipmentList;

    private StringProperty confirmEditButton, errorLabel;

    private int currentRoomID;
    private boolean onlyViewing;

    private Model model;

    public CreateRoomViewModel(Model model)
    {
        this.model = model;

        topLabel = new SimpleStringProperty();
        roomNumber = new SimpleStringProperty();
        floor = new SimpleIntegerProperty();
        address = new SimpleStringProperty();
        seats = new SimpleIntegerProperty();
        equipmentToAdd = new SimpleStringProperty();
        confirmEditButton = new SimpleStringProperty();
        errorLabel = new SimpleStringProperty();

        equipmentList = FXCollections.observableArrayList();
    }

    public void reset(){

        equipmentToAdd.setValue(null);
        errorLabel.setValue(null);
        if (currentRoomID == 0) // creating
        {
            topLabel.setValue("Add a room");
            roomNumber.setValue(null);
            floor.setValue(null);
            address.set(null);
            seats.setValue(null);

            equipmentList.clear();
        } else //Editing or viewing
        {
            Room room;
            try
            {
                room = model.getRoomByID(currentRoomID);
            } catch (SQLException throwables)
            {
                errorLabel.setValue(throwables.getMessage());
                throwables.printStackTrace();
                return;
            } catch (RemoteException e)
            {
                errorLabel.setValue("Error loading the room");
                e.printStackTrace();
                return;
            }

            topLabel.setValue("Editing, roomID: " + currentRoomID);
            roomNumber.setValue(room.getRoomNumber());
            floor.setValue(room.getFloor());
            address.set(room.getBuildingAddress());
            seats.setValue(room.getNumberOfSeats());

            equipmentList.clear();
            for (String equip:
                    room.getEquipment())
            {
                equipmentList.add(new EquipmentViewModel(equip));
            }

            if (onlyViewing)
            {
                topLabel.setValue("RoomID: " + currentRoomID);
            }

        }
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
        if (equipmentList.size() != 0 && equipmentList.size() - 1 >= selectedIndex && selectedIndex >= 0)
            equipmentList.remove(selectedIndex);
    }

    public void confirm(boolean editing, int roomID) throws SQLException, RemoteException {
        // todo
        //  some confirmation
        if (!editing)
        {
            if (equipmentList.isEmpty())
            {
                try
                {
                    model.addRoom(roomNumber.get(), address.get(), seats.get(), floor.get());
                } catch (SQLException throwables)
                {
                    throwables.printStackTrace();
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                } //TODO add different errorLabel statements
            }
            else
            {
                ArrayList<String> equipment = new ArrayList<>();

                for (EquipmentViewModel e :
                        equipmentList)
                {
                    equipment.add(e.getEquipmentProperty().get());
                }

                model.addRoom(roomNumber.get(), address.get(), seats.get(), floor.get(), equipment);
            }
        } else
        {
            ArrayList<String> equipment = new ArrayList<>();

            for (EquipmentViewModel e :
                    equipmentList)
            {
                equipment.add(e.getEquipmentProperty().get());
            }

            model.getRoomByID(roomID).modifyRoom(roomNumber.get(), address.get(), seats.get(), floor.get(), equipment);

        }

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

    public StringProperty getTopLabelProperty(){return topLabel;}

    public StringProperty getErrorLabelProperty()
    {
        return errorLabel;
    }

    public void setCurrentRoomID(int currentRoomID)
    {
        this.currentRoomID = currentRoomID;
    }

    public int getCurrentRoomID()
    {
        return currentRoomID;
    }

    public void setOnlyViewing(boolean onlyViewing)
    {
        this.onlyViewing = onlyViewing;
    }

    public boolean isOnlyViewing()
    {
        return onlyViewing;
    }


}
