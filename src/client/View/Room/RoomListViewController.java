package client.View.Room;

import client.View.Helpers.StringIntegerConverter;
import client.View.ViewHandler;
import client.ViewModel.RoomListViewModel;
import client.ViewModel.RoomViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class RoomListViewController
{
    @FXML
    private TextField searchBox;
    @FXML
    private TableView<RoomViewModel> roomTable;
    @FXML
    private TableColumn<RoomViewModel, Number> roomIDColumn;
    @FXML
    private TableColumn<RoomViewModel, String> roomNumberColumn;
    @FXML
    private TableColumn<RoomViewModel, Number> floorColumn;
    @FXML
    private TableColumn<RoomViewModel, Number> seatsColumn;
    @FXML
    private TableColumn<RoomViewModel, String> addressColumn;

    @FXML
    private Label errorLabel;


    private ViewHandler viewHandler;
    private RoomListViewModel viewModel;
    private Region root;

    public RoomListViewController()
    {
    }

    public void init(ViewHandler viewHandler, RoomListViewModel viewModel, Region root)
    {
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
        this.root = root;

        searchBox.textProperty().bindBidirectional(viewModel.getSearchBoxProperty());

        roomTable.setItems(viewModel.getRoomList());

        roomIDColumn.setCellValueFactory(
                cellData -> cellData.getValue().getRoomIDProperty());
        roomNumberColumn.setCellValueFactory(
                cellData -> cellData.getValue().getRoomNumberProperty());
        floorColumn.setCellValueFactory(
                cellData -> cellData.getValue().getFloorProperty());
        seatsColumn.setCellValueFactory(
                cellData -> cellData.getValue().getNumberOfSeatsProperty());
        addressColumn.setCellValueFactory(
                cellData -> cellData.getValue().getAddressProperty());

        errorLabel.textProperty().bindBidirectional(viewModel.getErrorLabelProperty());

        errorLabel.setText("");

        viewModel.reset();

    }

    @FXML
    private void searchBoxKeyTyped()
    {

        viewModel.search();
    }

    @FXML
    private void searchButton()
    {
        viewModel.search();
    }

    @FXML
    private void openButton()
    {
        try
        {
            viewHandler.setPickedRoomID(roomTable.getSelectionModel().getSelectedItem().getRoomIDProperty().get());
            viewHandler.openView("Room");
        } catch (Exception e)
        {
            viewModel.setErrorLabel("Select a room to open first");
        }
    }

    @FXML
    private void editButton()
    {
        try
        {
            viewHandler.setPickedRoomID(roomTable.getSelectionModel().getSelectedItem().getRoomIDProperty().get());
            viewHandler.openView("EditRoom");
        } catch (Exception e)
        {
            viewModel.setErrorLabel("Select a room to edit first");
        }
    }

    @FXML
    private void removeButton()
    {
        try
        {
            viewModel.removeRoom(roomTable.getSelectionModel().getSelectedIndex(), roomTable.getSelectionModel().getSelectedItem().getRoomIDProperty().get());
        } catch (Exception e)
        {
            viewModel.setErrorLabel("Select a room to remove first");
        }
    }

    @FXML
    private void backButton() throws SQLException, RemoteException {
        viewHandler.openView("MainMenu");
    }

    @FXML
    private void addButton() throws SQLException, RemoteException {
        viewHandler.openView("CreateRoom");
    }


    public Region getRoot()
    {
        return root;
    }
}
