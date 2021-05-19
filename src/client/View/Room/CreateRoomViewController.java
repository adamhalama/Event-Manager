package client.View.Room;


import client.View.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.CreateRoomViewModel;
import client.ViewModel.EquipmentViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;


public class CreateRoomViewController
{
    @FXML
    private TextField roomNumber;
    @FXML
    private TextField floor;
    @FXML
    private TextField address;
    @FXML
    private TextField numberOfSeats;
    @FXML
    private TextField equipmentToAdd;
    @FXML
    private TableView<EquipmentViewModel> equipmentList;
    @FXML
    private TableColumn<EquipmentViewModel, String> equipmentColumn;

    private ViewHandler viewHandler;
    private CreateRoomViewModel viewModel;
    private Region root;

    private boolean editing;

    public CreateRoomViewController()
    {
    }

    public void init(ViewHandler viewHandler, CreateRoomViewModel viewModel, Region root, boolean editing)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        this.editing = editing;

        equipmentList.setItems(viewModel.getEquipmentList());

        equipmentColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getEquipmentProperty());

        roomNumber.textProperty().bindBidirectional(viewModel.getRoomNumberProperty());
        address.textProperty().bindBidirectional(viewModel.getAddressProperty());
        equipmentToAdd.textProperty().bindBidirectional(viewModel.getEquipmentToAddProperty());


        Bindings.bindBidirectional(
                floor.textProperty(),
                viewModel.getFloorProperty(),
                converter); // FLOOR
        Bindings.bindBidirectional(
                numberOfSeats.textProperty(),
                viewModel.getSeatsProperty(),
                converter); // NUMBER OF SEATS


    }

    public void reset()
    {
        equipmentList.setItems(viewModel.getEquipmentList());
        roomNumber.setText(null);
        address.setText(null);
        equipmentToAdd.setText(null);
        floor.setText(null);
        numberOfSeats.setText(null);
    }

    public boolean isEditing()
    {
        return editing;
    }

    @FXML
    private void addButton()
    {
        viewModel.addEquipment(editing);
    }

    @FXML
    private void removeSelectedButton()
    {
        //todo maybe swap for getFocusedIndex

        viewModel.removeEquipment(equipmentList.getSelectionModel().getSelectedIndex(), editing);
    }

    @FXML
    private void confirmButton()
    {
        //todo save the changes, push them, and call parent view

        viewModel.confirm(editing);
        viewHandler.openView("RoomList");
    }

    public Region getRoot()
    {
        return root;
    }
}
