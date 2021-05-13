package client.ViewModel;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateRoomViewModel
{
    private StringProperty roomCode;
    private IntegerProperty floor;
    private StringProperty address;
    private IntegerProperty seats;
    private StringProperty equipmentToAdd;
    private ObservableList<String> equipmentList;

    public CreateRoomViewModel(/*Model model*/)
    {
        roomCode = new SimpleStringProperty();
        floor = new SimpleIntegerProperty();
        address = new SimpleStringProperty();
        seats = new SimpleIntegerProperty();
        equipmentToAdd = new SimpleStringProperty();

        equipmentList = FXCollections.observableArrayList();
    }
}
