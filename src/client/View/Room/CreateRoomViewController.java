package client.View.Room;


import client.View.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.CreateRoomViewModel;
import client.ViewModel.EquipmentViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;


public class CreateRoomViewController
{
    @FXML
    private Label topLabel;
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

    @FXML
    private Button addButton, removeButton, confirmEditButton;

    private ViewHandler viewHandler;
    private CreateRoomViewModel viewModel;
    private Region root;

    private boolean editing;
    private boolean viewing;

    public CreateRoomViewController()
    {
    }

    public void init(ViewHandler viewHandler, CreateRoomViewModel viewModel, Region root)
    {
        StringIntegerConverter converter = new StringIntegerConverter(0);

        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;


        equipmentList.setItems(viewModel.getEquipmentList());

        equipmentColumn.setCellValueFactory(
                cellDate -> cellDate.getValue().getEquipmentProperty());

        roomNumber.textProperty().bindBidirectional(viewModel.getRoomNumberProperty());
        address.textProperty().bindBidirectional(viewModel.getAddressProperty());
        equipmentToAdd.textProperty().bindBidirectional(viewModel.getEquipmentToAddProperty());

        topLabel.textProperty().bindBidirectional(viewModel.getTopLabelProperty());

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

    private void enableEditing()
    {
        roomNumber.setDisable(false);
        address.setDisable(false);
        equipmentToAdd.setDisable(false);
        floor.setDisable(false);
        numberOfSeats.setDisable(false);

        addButton.setDisable(false);
        removeButton.setDisable(false);
        confirmEditButton.setText("Confirm");
    }

    private void disableEditing()
    {
        roomNumber.setDisable(true);
        address.setDisable(true);
        equipmentToAdd.setDisable(true);
        floor.setDisable(true);
        numberOfSeats.setDisable(true);

        addButton.setDisable(true);
        removeButton.setDisable(true);
        confirmEditButton.setText("Edit");
    }

    public boolean isEditing()
    {
        return editing;
    }

    public void setEditing(boolean editing)
    {
        this.editing = editing;
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
        if (!viewing)
        {
            viewModel.confirm(editing, viewHandler.getPickedRoomID());
            viewHandler.openView("RoomList");
        }
        else
            viewHandler.openView("EditRoom");
    }

    @FXML
    private void backButton()
    {
        viewHandler.openView("RoomList");
    }

    public Region getRoot()
    {
        return root;
    }

    public void setViewing(boolean b)
    {
        this.viewing = b;

        if (viewing)
            disableEditing();
        else
            enableEditing();
    }
}
